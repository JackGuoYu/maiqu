package com.maiqu.service;

import com.alibaba.fastjson.JSONObject;
import com.maiqu.domain.model.Role;
import com.maiqu.domain.model.User;
import com.maiqu.domain.model.UserToken;
import com.maiqu.domain.request.Login;
import com.maiqu.domain.request.UserVo;
import com.maiqu.domain.request.dto.PageDto;
import com.maiqu.domain.request.dto.UserDto;
import com.maiqu.domain.response.BaseResponse;
import com.maiqu.domain.response.Page;
import com.maiqu.filter.LoginFilter;
import com.maiqu.mapper.UserMapper;
import com.maiqu.mapper.UserRoleMapper;
import com.maiqu.mapper.UserTokenMapper;
import com.maiqu.util.*;
import io.netty.util.internal.StringUtil;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserTokenMapper userTokenMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    public User getUser(Integer userId){
       return userMapper.getUser(userId);
    }

    @Transactional
    public BaseResponse<String> login(HttpServletResponse response, Login login){
        BaseResponse<String> result = new BaseResponse<>();
        String password = login.getPassword();
        String userName = login.getUserName();

        if(StringUtils.isEmpty(password)){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR, "密码为空");
        }

        if(StringUtils.isEmpty(userName)){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR, "账户名为空");
        }

        User user = userMapper.getUserByName(login.getUserName());
        if(user == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"该账户不存在,请重新注册");
        }

        if(redisUtil.hget(RedisKey.LOGIN_LOCK_KEY,user.getUserName()) != null){
            return BaseResponse.fail(CommonCode.USER_LOCKED, "该账户被锁定,请等待30秒后尝试或联系管理员进行解锁");
        }

        String md5Pwd = MD5.getMD5Password(password);

        if(!md5Pwd.equals(user.getPassWord())){
            Integer number = (Integer)redisUtil.hget(RedisKey.LOGIN_RETYY_NUMBER_KEY, userName);
            if(null == number){
                number = 1;
                redisUtil.hset(RedisKey.LOGIN_RETYY_NUMBER_KEY,user.getUserName(),number);
            }else{
                if(number<10){
                    redisUtil.hset(RedisKey.LOGIN_RETYY_NUMBER_KEY,user.getUserName(),number+1);
                }
            }
            if(number<10) {
                return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR, "密码输入错误，还剩" + (10 - number) + "次请重新输入");
            }else if(number == 10){
                redisUtil.hset(RedisKey.LOGIN_LOCK_KEY, user.getUserName(),1, 30);
                return BaseResponse.fail(CommonCode.USER_LOCKED, "密码输入错误，账号已被锁定，请30秒后再进行尝试");
            }
        }

        //生成token
        String token = MD5.getToken(user.getUserName());
        redisUtil.hset(RedisKey.USER_TOKEN_KEY,user.getUserName(),token,7*24*60*60);    //设置7天有效token
        redisUtil.hset(RedisKey.TOKEN_GET_USER_KEY,token,JSONObject.toJSONString(user));
        UserToken userToken = userTokenMapper.getUserToken(user.getId());
        if(userToken == null){
            userToken = new UserToken();
            userToken.setUserId(user.getId());
            userToken.setToken(token);
            userToken.setCreateTime(LocalDateTime.now());
            userToken.setUpdateTime(LocalDateTime.now());
            userToken.setFlag(CommonCode.ACTIVE);
            userTokenMapper.insertUserToken(userToken);
        }else{
            userToken.setToken(token);
            userToken.setUpdateTime(LocalDateTime.now());
            userTokenMapper.updateUserToken(userToken);
        }


        Cookie cookie=new Cookie("token",token);
        cookie.setMaxAge(2*60*60);

        response.addCookie(cookie);
        result.setData(token);
        result.setMsg("登录成功");
        return result;
    }

    public BaseResponse<Boolean> logout(HttpServletRequest request, HttpServletResponse response){
        BaseResponse<Boolean> result = new BaseResponse<>();
        String token = request.getHeader("token");
        Cookie[] cookies = request.getCookies();
        User user = new User();
        if(cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
                if ("user".equals(cookie.getName())) {
                    user = JSONObject.parseObject(cookie.getValue(), User.class);
                }

            }
        }else{
            Cookie cookie=new Cookie("token",null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }

        if(!StringUtils.isEmpty(token)){
            String userStr = (String)redisUtil.hget(RedisKey.TOKEN_GET_USER_KEY,token);
            if(!StringUtils.isEmpty(userStr)){
               user = JSONObject.parseObject(userStr,User.class);
            }
            redisUtil.hdel(RedisKey.TOKEN_GET_USER_KEY, token);
        }

        if(user != null){
            redisUtil.hdel(RedisKey.USER_TOKEN_KEY, user.getUserName());
            UserToken userToken = new UserToken();
            userToken.setUserId(user.getId());
            userToken.setToken("");
            userToken.setUpdateTime(LocalDateTime.now());
            userTokenMapper.updateUserToken(userToken);
        }

        result.setData(true);
        result.setMsg("退出登录成功");
        return result;
    }

    /**
     * 用户注册
     * @param userVo
     * @return
     */
    public BaseResponse<Boolean> register(UserVo userVo){
        BaseResponse<Boolean> result = new BaseResponse<>();
        result = checkRegisterData(userVo);
        if(result != null){
            return result;
        }
        if(userMapper.getUserByName(userVo.getUserName())!=null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"该账户名已被注册");
        }

        User user = new User();
        user.setUserName(userVo.getUserName());
        user.setPassWord(MD5.getMD5Password(userVo.getPassWord()));
        user.setPhone(userVo.getPhone());
        user.setSex(userVo.getSex());
        LocalDateTime birthDate = DateUtil.parseLocalDateTime(userVo.getBirthDate());
        user.setBirthDate(birthDate);
        user.setRoleId(userVo.getRoleId());
        user.setRoleName(userVo.getRoleName());
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setFlag(CommonCode.ACTIVE);
        userMapper.insertUser(user);
        bandRole(user.getId(),userVo.getRoleId());
        result = new BaseResponse<>();
        result.setData(true);
        result.setMsg("注册成功");
        return result;
    }


    /**
     * 管理员添加用户
     * @param userVo
     * @return
     */
    public BaseResponse<Boolean> addUser(UserVo userVo){
        BaseResponse<Boolean> result = new BaseResponse<>();
        result = checkRegisterData(userVo);
        if(result != null){
            return result;
        }
        if(userMapper.getUserByName(userVo.getUserName())!=null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"该账户名已被注册");
        }
        User user = new User();
        user.setUserName(userVo.getUserName());
        user.setPassWord(MD5.getMD5Password(userVo.getPassWord()));
        user.setPhone(userVo.getPhone());
        user.setSex(userVo.getSex());
        user.setBirthDate(DateUtil.parseLocalDateTime(userVo.getBirthDate()));
        user.setRoleId(userVo.getRoleId());
        user.setRoleName(userVo.getRoleName());
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setFlag(CommonCode.ACTIVE);
        userMapper.insertUser(user);
        bandRole(user.getId(),userVo.getRoleId());
        result = new BaseResponse<>();
        result.setData(true);
        result.setMsg("添加成功");
        return result;
    }

    /**
     * 管理员编辑用户
     * @param userVo
     * @return
     */
    public BaseResponse<Boolean> editUser(UserVo userVo){
        BaseResponse<Boolean> result = new BaseResponse<>();
        result = checkRegisterData(userVo);
        if(result != null){
            return result;
        }
        if(userMapper.getUserByName(userVo.getUserName())!=null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"该账户名已被注册");
        }
        User user = userMapper.getUser(userVo.getUserId());
        BeanUtils.copyProperties(userVo,user);
        user.setId(userVo.getUserId());
        user.setPassWord(MD5.getMD5Password(userVo.getPassWord()));
        user.setBirthDate(DateUtil.parseLocalDateTime(userVo.getBirthDate()));
        user.setUpdateTime(LocalDateTime.now());
        user.setFlag(CommonCode.ACTIVE);
        userMapper.updateUser(user);
        bandRole(user.getId(),userVo.getRoleId());
        result = new BaseResponse<>();
        result.setData(true);
        result.setMsg("编辑成功");
        return result;
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    public BaseResponse<Boolean> deleteUser(Integer userId){
        BaseResponse<Boolean> result = new BaseResponse<>();
        if(userId == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"账户ID为空");
        }
        User dbUser =  userMapper.getUser(userId);
        if(dbUser==null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"不存在该账户名");
        }
        User user = LoginFilter.getUser();
        if(dbUser.getUserName().equals(user.getUserName())){    //自己删自己
            return BaseResponse.success("该管理员删除自己");
        }

        User updateUser =  new User();
        updateUser.setId(userId);
        updateUser.setUpdateTime(LocalDateTime.now());
        updateUser.setFlag(CommonCode.UNACTIVE);
        userMapper.updateUser(updateUser);
        result.setData(true);
        result.setMsg("删除用户成功");
        return result;
    }

    /**
     * 获取用户列表
     * @param userDto
     * @return
     */
    public BaseResponse<Page> userList(UserDto userDto){
        List<User> users = userMapper.findUserList(userDto);
        if(users == null && users.size() == 0){
            return BaseResponse.success(Page.fail());
        }
        Integer total = userMapper.findUserListCount(userDto);
        Integer pageNumber = userDto.getPageNumber(total);
        return BaseResponse.success(Page.success(total,pageNumber,users));
    }

    /**
     * 绑定用户-角色
     * @param userId
     * @param roleId
     * @return
     */
    public BaseResponse<Boolean> bandRole(Integer userId, Integer roleId){
        userRoleMapper.deleteRole(userId);
        userRoleMapper.bandRole(userId,roleId);
        return BaseResponse.success(true);
    }



    public BaseResponse<Boolean> checkRegisterData(UserVo userVo){

        if(StringUtils.isEmpty(userVo.getUserName())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR, "用户名称为空");
        }

        if(StringUtils.isEmpty(userVo.getPassWord())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR, "用户密码为空");
        }

        if(StringUtils.isEmpty(userVo.getPhone())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR, "手机号为空");
        }

        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
        if(userVo.getPhone().length() != 11){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR, "手机号应为11位数");
        }else{
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(userVo.getPhone());
            boolean isMatch = m.matches();
            if(!isMatch){
                return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR, "手机号格式错误");
            }
        }

        if(userVo.getSex() != CommonCode.MAM && userVo.getSex() != CommonCode.WOMAN){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR, "性别输入错误");
        }

        if(StringUtils.isEmpty(userVo.getBirthDate())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR, "出生日期为空");
        }

        if(userVo.getRoleId() == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR, "请选择角色");
        }

        return null;
    }
}

package com.maiqu.controller;

import com.maiqu.domain.model.User;
import com.maiqu.domain.request.Login;
import com.maiqu.domain.request.UserVo;
import com.maiqu.domain.request.dto.PageDto;
import com.maiqu.domain.request.dto.UserDto;
import com.maiqu.domain.response.BaseResponse;
import com.maiqu.domain.response.Page;
import com.maiqu.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api("用户管理接口")
@RestController
@RequestMapping("/admin/user")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value="获取用户", notes="根据userId获取用户")
    @RequestMapping(value = "getUser/{userId}", method = RequestMethod.GET)
    public User GetUser(@PathVariable Integer userId){
        return userService.getUser(userId);
    }

    @ApiOperation(value = "登录密码", notes = "用户输入密码")
    @PostMapping("/login")
    public BaseResponse<String> login(HttpServletResponse response, @RequestBody Login login){
        return userService.login(response,login);
    }

    @ApiOperation(value = "退出登录", notes = "用户退出")
    @GetMapping("/logout")
    public BaseResponse<Boolean> logout(HttpServletRequest request, HttpServletResponse response,String userName){
        return userService.logout(request,response);
    }

    @ApiOperation(value = "用户注册", notes = "用户注册")
    @PostMapping("/register")
    public BaseResponse<Boolean> register(@RequestBody UserVo userVo){
        return userService.register(userVo);
    }

    @ApiOperation(value = "添加用户", notes = "添加用户")
    @PostMapping("/add")
    public BaseResponse<Boolean> addUser(@RequestBody UserVo userVo){
        return userService.addUser(userVo);
    }


    @ApiOperation(value = "编辑用户", notes = "编辑用户")
    @PostMapping("/edit")
    public BaseResponse<Boolean> editUser(@RequestBody UserVo userVo){
        return userService.editUser(userVo);
    }

    @ApiOperation(value = "删除用户", notes = "删除用户")
    @GetMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestParam(value = "userId",required = true) Integer userId){
        return userService.deleteUser(userId);
    }

    @ApiOperation(value = "用户列表", notes = "用户列表")
    @GetMapping("/list")
    public BaseResponse<Page> userList(UserDto userDto){
        return userService.userList(userDto);
    }
}

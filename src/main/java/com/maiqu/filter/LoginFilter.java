package com.maiqu.filter;

import com.alibaba.fastjson.JSONObject;
import com.maiqu.domain.model.User;
import com.maiqu.domain.model.UserToken;
import com.maiqu.mapper.UserTokenMapper;
import com.maiqu.util.RedisKey;
import com.maiqu.util.RedisUtil;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/admin/*")
public class LoginFilter  implements Filter {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserTokenMapper userTokenMapper;

    private static User user;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //检验请求头是否携带token
        HttpServletRequest req = (HttpServletRequest) request;
        String requestURI = req.getRequestURI();
        String[] unFilterUrls = new String[]{"login","register","/role/list"};

        if(!checkPath(requestURI,unFilterUrls)){
            String token = req.getHeader("token") == null? "":req.getHeader("token");
            String userStr = (String) redisUtil.hget(RedisKey.TOKEN_GET_USER_KEY,token);

            if(StringUtils.isEmpty(token) || StringUtils.isEmpty(userStr)){
                 request.getRequestDispatcher("/failed").forward(request,response);
                 return;
            }
            user = JSONObject.parseObject(userStr,User.class);
            String tokenStr = (String)redisUtil.hget(RedisKey.USER_TOKEN_KEY,user.getUserName());
            if(StringUtils.isEmpty(tokenStr)){
                UserToken userToken = userTokenMapper.getUserToken(user.getId());
                if(StringUtils.isEmpty(userToken)){
                    request.getRequestDispatcher("/failed").forward(request,response);
                    return;
                }
                tokenStr = userToken.getToken();
            }

            if(!token.equals(tokenStr)){
                request.getRequestDispatcher("/failed").forward(request,response);
                return;
            }

        }

        chain.doFilter(request,response);
    }

    /**
     * 获取用户信息
     * @return
     */
    public static User getUser(){
       return user;
    }

    public Boolean checkPath(String reqPath, String[] urls){
        for(String url :urls){
            if(reqPath.contains(url)){
                return true;
            }
        }
       return false;
    }
}

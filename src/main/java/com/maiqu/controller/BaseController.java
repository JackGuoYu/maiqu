package com.maiqu.controller;

import com.maiqu.domain.request.Login;
import com.maiqu.domain.response.BaseResponse;
import com.maiqu.util.CommonCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Api("基础接口")
@RestController
public class BaseController {

    @ApiOperation(value = "用户未登录", notes = "用户未登录")
    @GetMapping("/failed")
    public BaseResponse<String> failed(){
        BaseResponse<String> result = new BaseResponse<>();
        result.setMsg("用户未登录");
        result.setCode(CommonCode.USER_UNLOGIN);
        return result;
    }
}

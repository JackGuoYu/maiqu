package com.maiqu.domain.response;

import com.maiqu.util.CommonCode;
import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class BaseResponse<T> {
    private Integer code;
    private T data;
    private String msg;

    public BaseResponse() {
        this.code = CommonCode.SUCCESS;
    }

    public BaseResponse(Integer code, T data, String msg){
       this.code = code;
       this.data = data;
       this.msg = msg;
    }

    public static BaseResponse fail(Integer code, String msg){
        return new BaseResponse(code,null,msg);
    }

    public static BaseResponse success (Object data){
        return new BaseResponse(CommonCode.SUCCESS,data,"");
    }
}

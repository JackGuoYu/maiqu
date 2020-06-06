package com.maiqu.domain.response;

import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class Page<T> {
    private Integer total;
    private Integer pageNumber;
    private T data;

    public Page(Integer total, Integer pageNumber, T data){
        this.total = total;
        this.pageNumber = pageNumber;
        this.data = data;
    }

    public static Page success(Integer total, Integer pageNumber, Object data){
        return new Page(total,pageNumber,data);
    }

    public static Page fail(){
        return new Page(0,0,null);
    }
}

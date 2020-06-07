package com.maiqu.domain.request.dto;

import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class PageDto {
    private Integer pageIndex = 1;
    private Integer pageSize = 10;

    //获取下标
    public Integer getPageIndex(){
        return (this.pageIndex-1)*this.pageSize;
    }

    //获取总页数
    public Integer getPageNumber(Integer total){
        if(total<this.pageSize){
            return this.pageSize/total;
        }
        return (total/this.pageSize) + (total%this.pageSize);
    }
}

package com.maiqu.mapper;

import com.maiqu.domain.model.ClassInfo;
import com.maiqu.domain.model.TechInfo;
import com.maiqu.domain.request.dto.PageDto;
import com.maiqu.domain.response.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechInfoMapper {
    List<TechInfo> findTechInfoList(PageDto pageDto);

    TechInfo getTechInfoById(Integer techId);

    Integer insertTechInfo(TechInfo techInfo);

    Integer updateTechInfo(TechInfo techInfo);

    Integer deleteTechInfo(Integer techId);

    Integer findTechInfoCount();

    TechInfo getTechInfoByName(String name);

}

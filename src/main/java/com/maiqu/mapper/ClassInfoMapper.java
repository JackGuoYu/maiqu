package com.maiqu.mapper;

import com.maiqu.domain.model.ClassInfo;
import com.maiqu.domain.model.Role;
import com.maiqu.domain.request.dto.PageDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassInfoMapper {
    List<ClassInfo> findClassInfoList();

    ClassInfo getClassInfoById(Integer classId);

    Integer insertClassInfo(ClassInfo classInfo);

    Integer updateClassInfo(ClassInfo classInfo);

    Integer deleteClassInfo(Integer classId);

    Integer findClassInfoCount();

    ClassInfo getClassInfoByName(String name);

}

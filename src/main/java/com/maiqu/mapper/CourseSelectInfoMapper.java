package com.maiqu.mapper;

import com.maiqu.domain.model.ClassInfo;
import com.maiqu.domain.model.CourseSelectInfo;
import com.maiqu.domain.request.dto.PageDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseSelectInfoMapper {
    List<CourseSelectInfo> findCourseSelectInfoList(PageDto pageDto);

    CourseSelectInfo getCourseSelectInfoById(Integer courseSelectId);

    Integer insertCourseSelectInfo(CourseSelectInfo courseSelectInfo);

    Integer updateCourseSelectInfo(CourseSelectInfo courseSelectInfo);

    Integer deleteCourseSelectInfo(Integer courseSelectId);

    Integer findCourseSelectInfoCount();

    CourseSelectInfo getCourseSelectInfoByName(String name);
}

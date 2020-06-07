package com.maiqu.service;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.maiqu.domain.model.*;
import com.maiqu.domain.request.CourseSelectVo;
import com.maiqu.domain.request.TechVo;
import com.maiqu.domain.request.dto.PageDto;
import com.maiqu.domain.response.BaseResponse;
import com.maiqu.domain.response.Page;
import com.maiqu.mapper.ClassInfoMapper;
import com.maiqu.mapper.CourseSelectInfoMapper;
import com.maiqu.mapper.TechInfoMapper;
import com.maiqu.util.CommonCode;
import com.maiqu.util.DateUtil;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClassInfoService {
    @Autowired
    private ClassInfoMapper classInfoMapper;

    @Autowired
    private TechInfoMapper techInfoMapper;

    @Autowired
    private CourseSelectInfoMapper courseSelectInfoMapper;

    public List<ClassInfo> classInfoList(){
        return classInfoMapper.findClassInfoList();
    }

    /**
     * 课程添加
     * @param classInfo
     * @return
     */
    public BaseResponse<Boolean> addCourse(ClassInfo classInfo){
        if(classInfo == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"参数不合法");
        }
        if(StringUtils.isEmpty(classInfo.getName())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"课程名称为空");
        }
        if(null != classInfoMapper.getClassInfoByName(classInfo.getName())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"已存在该课程名称");
        }
        classInfoMapper.insertClassInfo(classInfo);
        return BaseResponse.success("课程添加成功");
    }

    /**
     * 课程编辑
     * @param classInfo
     * @return
     */
    public BaseResponse<Boolean> editCourse(ClassInfo classInfo){
        if(classInfo == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"参数不合法");
        }
        if(classInfo.getId()==null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"课程ID为空");
        }
        if(StringUtils.isEmpty(classInfo.getName())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"课程名称为空");
        }
        if(null != classInfoMapper.getClassInfoByName(classInfo.getName())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"已存在该课程名称");
        }
        classInfoMapper.updateClassInfo(classInfo);
        return BaseResponse.success("课程编辑成功");
    }

    /**
     * 删除课程
     * @param classId
     * @return
     */
    public BaseResponse<Boolean> deleteCourse(Integer classId){
        if(classId == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"课程ID为空");
        }
        ClassInfo classInfo = classInfoMapper.getClassInfoById(classId);
        if(classInfo == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"无法删除,不存在该课程");
        }
        classInfoMapper.deleteClassInfo(classId);
        return BaseResponse.success("课程删除成功");
    }

    /**
     * 课程详情
     * @param classId
     * @return
     */
    public BaseResponse<ClassInfo> courseDetail(Integer classId){
        if(classId == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"课程ID为空");
        }
        ClassInfo classInfo = classInfoMapper.getClassInfoById(classId);
        if(classInfo == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"不存在该课程");
        }
        return BaseResponse.success(classInfo);
    }


    /**
     * 教学列表
     * @param pageDto
     * @return
     */
    public BaseResponse<List<TechInfo>> techInfoList(PageDto pageDto){
        List<TechInfo> techInfos = techInfoMapper.findTechInfoList(pageDto);
        if(techInfos == null && techInfos.size() == 0){
            return BaseResponse.success(Page.fail());
        }
        Integer total = techInfoMapper.findTechInfoCount();
        Integer pageNumber = pageDto.getPageNumber(total);
        return BaseResponse.success(Page.success(total,pageNumber,techInfos));
    }

    /**
     * 教学添加
     * @param techVo
     * @return
     */
    public BaseResponse<Boolean> addTechInfo(TechVo techVo){
        if(techVo == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"参数不合法");
        }
        if(StringUtils.isEmpty(techVo.getClassName())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"课程名称为空");
        }
        if(techVo.getClassHour() == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"课时为空");
        }
        if(StringUtils.isEmpty(techVo.getClassObjectives())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"课程目标为空");
        }
        if(StringUtils.isEmpty(techVo.getClassDate())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"授课日期为空");
        }
        if(StringUtils.isEmpty(techVo.getClassTearcher())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"授课讲师为空");
        }
        if(StringUtils.isEmpty(techVo.getClassAssistants())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"授课助教为空");
        }
        TechInfo techInfo = new TechInfo();
        BeanUtils.copyProperties(techVo, techInfo);
        techInfo.setClassDate(DateUtil.parseLocalDateTime(techVo.getClassDate()));
        techInfo.setCreateTime(LocalDateTime.now());
        techInfo.setUpdateTime(LocalDateTime.now());
        techInfo.setFlag(CommonCode.ACTIVE);
        techInfoMapper.insertTechInfo(techInfo);
        return BaseResponse.success("教学添加成功");
    }

    /**
     * 教学添加
     * @param techVo
     * @return
     */
    public BaseResponse<Boolean> editTechInfo(TechVo techVo){
        if(techVo == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"参数不合法");
        }
        if(StringUtils.isEmpty(techVo.getClassName())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"课程名称为空");
        }
        if(techVo.getClassHour() == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"课时为空");
        }
        if(StringUtils.isEmpty(techVo.getClassObjectives())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"课程目标为空");
        }
        if(StringUtils.isEmpty(techVo.getClassDate())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"授课日期为空");
        }
        if(StringUtils.isEmpty(techVo.getClassTearcher())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"授课讲师为空");
        }
        if(StringUtils.isEmpty(techVo.getClassAssistants())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"授课助教为空");
        }
        TechInfo techInfo = new TechInfo();
        BeanUtils.copyProperties(techVo, techInfo);
        techInfo.setClassDate(DateUtil.parseLocalDateTime(techVo.getClassDate()));
        techInfo.setUpdateTime(LocalDateTime.now());
        techInfo.setFlag(CommonCode.ACTIVE);
        techInfoMapper.updateTechInfo(techInfo);
        return BaseResponse.success("教学编辑成功");
    }

    /**
     * 删除教学记录
     * @param techId
     * @return
     */
    public BaseResponse<Boolean> deleteTechInfo(Integer techId){
        BaseResponse<Boolean> result = new BaseResponse<>();
        if(techId == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"教学ID为空");
        }
        TechInfo techInfo = techInfoMapper.getTechInfoById(techId);
        if(techInfo==null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"不存在该学员名称");
        }

        techInfo.setUpdateTime(LocalDateTime.now());
        techInfo.setFlag(CommonCode.UNACTIVE);
        techInfoMapper.updateTechInfo(techInfo);
        result.setData(true);
        result.setMsg("删除用户成功");
        return result;
    }

    /**
     * 教学详情
     * @param techId
     * @return
     */
    public BaseResponse<TechInfo> techInfoDetail(Integer techId){
        if(techId == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"教学ID参数为空");
        }
        TechInfo techInfo = techInfoMapper.getTechInfoById(techId);
        if(techInfo == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"不存在该教学记录");
        }
        return BaseResponse.success(techInfo);
    }

    /**
     * 选课列表
     * @param pageDto
     * @return
     */
    public BaseResponse<List<CourseSelectInfo>> courseSelectInfoList(PageDto pageDto){
        List<CourseSelectInfo> courseSelectInfos = courseSelectInfoMapper.findCourseSelectInfoList(pageDto);
        if(courseSelectInfos == null && courseSelectInfos.size() == 0){
            return BaseResponse.success(Page.fail());
        }
        Integer total = courseSelectInfoMapper.findCourseSelectInfoCount();
        Integer pageNumber = pageDto.getPageNumber(total);
        return BaseResponse.success(Page.success(total,pageNumber,courseSelectInfos));
    }

    /**
     * 选课添加
     * @param courseSelectVo
     * @return
     */
    public BaseResponse<Boolean> addCourseSelectInfo(CourseSelectVo courseSelectVo){
        if(courseSelectVo == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"参数不合法");
        }
        if(StringUtils.isEmpty(courseSelectVo.getClassName())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"课程名称为空");
        }
        if(courseSelectVo.getClassHour() == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"课时为空");
        }
        if(StringUtils.isEmpty(courseSelectVo.getSchool())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"学校名称为空");
        }
        if(courseSelectVo.getGrade() == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"年级为空");
        }
        if(courseSelectVo.getStudentNumber() == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"学生人数为空");
        }
        if(StringUtils.isEmpty(courseSelectVo.getClassDate())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"授课日期为空");
        }
        if(StringUtils.isEmpty(courseSelectVo.getClassTearcher())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"授课讲师为空");
        }
        if(StringUtils.isEmpty(courseSelectVo.getClassAssistants())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"授课助教为空");
        }
        CourseSelectInfo courseSelectInfo = new CourseSelectInfo();
        BeanUtils.copyProperties(courseSelectVo, courseSelectInfo);
        courseSelectInfo.setClassDate(DateUtil.parseLocalDateTime(courseSelectVo.getClassDate()));
        courseSelectInfo.setCreateTime(LocalDateTime.now());
        courseSelectInfo.setUpdateTime(LocalDateTime.now());
        courseSelectInfo.setFlag(CommonCode.ACTIVE);
        courseSelectInfoMapper.insertCourseSelectInfo(courseSelectInfo);
        return BaseResponse.success("教学添加成功");
    }

    /**
     * 选课编辑
     * @param courseSelectVo
     * @return
     */
    public BaseResponse<Boolean> editCourseSelectInfo(CourseSelectVo courseSelectVo){
        if(courseSelectVo == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"参数不合法");
        }
        if(StringUtils.isEmpty(courseSelectVo.getClassName())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"课程名称为空");
        }
        if(courseSelectVo.getClassHour() == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"课时为空");
        }
        if(StringUtils.isEmpty(courseSelectVo.getSchool())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"学校名称为空");
        }
        if(courseSelectVo.getGrade() == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"年级为空");
        }
        if(courseSelectVo.getStudentNumber() == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"学生人数为空");
        }
        if(StringUtils.isEmpty(courseSelectVo.getClassDate())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"授课日期为空");
        }
        if(StringUtils.isEmpty(courseSelectVo.getClassTearcher())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"授课讲师为空");
        }
        if(StringUtils.isEmpty(courseSelectVo.getClassAssistants())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"授课助教为空");
        }
        CourseSelectInfo courseSelectInfo = new CourseSelectInfo();
        BeanUtils.copyProperties(courseSelectVo, courseSelectInfo);
        courseSelectInfo.setId(courseSelectVo.getCourseSelectId());
        courseSelectInfo.setClassDate(DateUtil.parseLocalDateTime(courseSelectVo.getClassDate()));
        courseSelectInfo.setUpdateTime(LocalDateTime.now());
        courseSelectInfo.setFlag(CommonCode.ACTIVE);
        courseSelectInfoMapper.updateCourseSelectInfo(courseSelectInfo);
        return BaseResponse.success("教学编辑成功");
    }

    /**
     * 删除选课记录
     * @param courseSelectId
     * @return
     */
    public BaseResponse<Boolean> deleteCourseSelectInfo(Integer courseSelectId){
        BaseResponse<Boolean> result = new BaseResponse<>();
        if(courseSelectId == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"选课ID为空");
        }
        CourseSelectInfo courseSelectInfo = courseSelectInfoMapper.getCourseSelectInfoById(courseSelectId);
        if(courseSelectInfo == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"不存在该选课记录");
        }
        courseSelectInfo.setUpdateTime(LocalDateTime.now());
        courseSelectInfo.setFlag(CommonCode.UNACTIVE);
        courseSelectInfoMapper.updateCourseSelectInfo(courseSelectInfo);
        result.setMsg("选课删除成功");
        result.setData(true);
        return result;
    }

    /**
     * 选课详情
     * @param courseSelectId
     * @return
     */
    public BaseResponse<CourseSelectInfo> courseSelectInfoDetail(Integer courseSelectId){
        if(courseSelectId == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"选课ID为空");
        }
        CourseSelectInfo courseSelectInfo = courseSelectInfoMapper.getCourseSelectInfoById(courseSelectId);
        if(courseSelectInfo == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"该选课记录不存在");
        }
        return BaseResponse.success(courseSelectInfo);
    }

}

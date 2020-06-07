package com.maiqu.controller;

import com.maiqu.domain.model.*;
import com.maiqu.domain.request.CourseSelectVo;
import com.maiqu.domain.request.TechVo;
import com.maiqu.domain.request.dto.PageDto;
import com.maiqu.domain.response.BaseResponse;
import com.maiqu.domain.response.Page;
import com.maiqu.service.ClassInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("课程管理接口")
@RestController
@RequestMapping("/admin/classinfo")
public class ClassInfoController {

    @Autowired
    private ClassInfoService classInfoService;


    @ApiOperation(value = "课程列表", notes = "课程列表")
    @GetMapping("/course/list")
    public BaseResponse<List<ClassInfo>> courseList(){
        return BaseResponse.success(classInfoService.classInfoList());
    }

    @ApiOperation(value = "增加课程", notes = "增加课程")
    @PostMapping("/course/add")
    public BaseResponse<Boolean> addCourse(@RequestBody ClassInfo classInfo){
        return BaseResponse.success(classInfoService.addCourse(classInfo));
    }

    @ApiOperation(value = "编辑课程", notes = "编辑课程")
    @PostMapping("/course/edit")
    public BaseResponse<Boolean> editCourse(@RequestBody ClassInfo classInfo){
        return BaseResponse.success(classInfoService.editCourse(classInfo));
    }

    @ApiOperation(value = "删除课程", notes = "删除课程")
    @GetMapping("/course/delete")
    public BaseResponse<Boolean> deleteCourse(Integer courseId){
        return BaseResponse.success(classInfoService.deleteCourse(courseId));
    }

    @ApiOperation(value = "课程详情", notes = "课程详情")
    @GetMapping("/course/detail")
    public BaseResponse<ClassInfo> courseDetail(Integer courseId){
        return classInfoService.courseDetail(courseId);
    }

    @ApiOperation(value = "教学列表", notes = "教学列表")
    @PostMapping("/tech/list")
    public BaseResponse<List<TechInfo>> techList(@RequestBody PageDto pageDto){
        return BaseResponse.success(classInfoService.techInfoList(pageDto));
    }

    @ApiOperation(value = "增加教学", notes = "增加教学")
    @PostMapping("/tech/add")
    public BaseResponse<Boolean> addTech(@RequestBody TechVo techVo){
        return BaseResponse.success(classInfoService.addTechInfo(techVo));
    }

    @ApiOperation(value = "编辑教学", notes = "编辑教学")
    @PostMapping("/tech/edit")
    public BaseResponse<Boolean> editTech(@RequestBody TechVo techVo){
        return BaseResponse.success(classInfoService.editTechInfo(techVo));
    }

    @ApiOperation(value = "删除教学", notes = "删除教学")
    @GetMapping("/tech/delete")
    public BaseResponse<Boolean> deleteTech(Integer techId){
        return BaseResponse.success(classInfoService.deleteTechInfo(techId));
    }

    @ApiOperation(value = "教学详情", notes = "教学详情")
    @GetMapping("/tech/detail")
    public BaseResponse<Boolean> techDetail(Integer techId){
        return BaseResponse.success(classInfoService.techInfoDetail(techId));
    }

    @ApiOperation(value = "选课列表", notes = "选课列表")
    @PostMapping("/courseSelect/list")
    public BaseResponse<List<TechInfo>> courseSelectList(@RequestBody PageDto pageDto){
        return BaseResponse.success(classInfoService.courseSelectInfoList(pageDto));
    }

    @ApiOperation(value = "增加选课", notes = "增加选课")
    @PostMapping("/courseSelect/add")
    public BaseResponse<Boolean> addCourseSelect(@RequestBody CourseSelectVo courseSelectVo){
        return BaseResponse.success(classInfoService.addCourseSelectInfo(courseSelectVo));
    }

    @ApiOperation(value = "编辑选课", notes = "编辑选课")
    @PostMapping("/courseSelect/edit")
    public BaseResponse<Boolean> editCourseSelect(@RequestBody CourseSelectVo courseSelectVo){
        return BaseResponse.success(classInfoService.editCourseSelectInfo(courseSelectVo));
    }

    @ApiOperation(value = "删除选课", notes = "删除选课")
    @GetMapping("/courseSelect/delete")
    public BaseResponse<Boolean> deleteCourseSelectInfo(Integer courseSelectId){
        return BaseResponse.success(classInfoService.deleteCourseSelectInfo(courseSelectId));
    }

    @ApiOperation(value = "选课详情", notes = "删除选课")
    @GetMapping("/courseSelect/detail")
    public BaseResponse<CourseSelectInfo> courseSelectInfoDetail(Integer courseSelectId){
        return BaseResponse.success(classInfoService.courseSelectInfoDetail(courseSelectId));
    }
}

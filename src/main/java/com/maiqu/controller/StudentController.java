package com.maiqu.controller;

import com.maiqu.domain.model.Student;
import com.maiqu.domain.request.StudentVo;
import com.maiqu.domain.request.UserVo;
import com.maiqu.domain.request.dto.PageDto;
import com.maiqu.domain.response.BaseResponse;
import com.maiqu.domain.response.Page;
import com.maiqu.mapper.StudentMapper;
import com.maiqu.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("学员管理接口")
@RestController
@RequestMapping("/admin/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @ApiOperation(value = "添加学员", notes = "添加学员")
    @PostMapping("/add")
    public BaseResponse<Boolean> addStudent(@RequestBody StudentVo studentVo){
        return studentService.addStudent(studentVo);
    }


    @ApiOperation(value = "编辑学员", notes = "编辑学员")
    @PostMapping("/edit")
    public BaseResponse<Boolean> editStudent(@RequestBody StudentVo studentVo){
        return studentService.editStudent(studentVo);
    }

    @ApiOperation(value = "删除学员", notes = "删除学员")
    @GetMapping("/delete")
    public BaseResponse<Boolean> deleteStudent(@RequestParam Integer userId){
        return studentService.deleteStudent(userId);
    }

    @ApiOperation(value = "学员列表", notes = "学员列表")
    @GetMapping("/list")
    public BaseResponse<Page> studentList(PageDto pageDto){
        return studentService.studentList(pageDto);
    }

    @ApiOperation(value = "用户详情", notes = "用户详情")
    @GetMapping("/detail")
    public BaseResponse<Student> studentDetail(Integer stuId){
        return studentService.studentDetail(stuId);
    }
}

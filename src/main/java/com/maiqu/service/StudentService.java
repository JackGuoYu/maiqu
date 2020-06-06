package com.maiqu.service;

import com.maiqu.domain.model.Student;
import com.maiqu.domain.model.User;
import com.maiqu.domain.request.StudentVo;
import com.maiqu.domain.request.UserVo;
import com.maiqu.domain.request.dto.PageDto;
import com.maiqu.domain.response.BaseResponse;
import com.maiqu.domain.response.Page;
import com.maiqu.filter.LoginFilter;
import com.maiqu.mapper.StudentMapper;
import com.maiqu.util.CommonCode;
import com.maiqu.util.DateUtil;
import com.maiqu.util.MD5;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class StudentService {
    @Autowired
    private StudentMapper studentMapper;

    /**
     * 获取学员信息
     * @param stuId
     * @return
     */
    public Student getStudent(Integer stuId){
        return studentMapper.getStudent(stuId);
    }

    /**
     * 添加学员
     * @param studentVo
     * @return
     */
    public BaseResponse<Boolean> addStudent(StudentVo studentVo){
        BaseResponse<Boolean> result = new BaseResponse<>();
        result = checkData(studentVo);
        if(result != null){
            return result;
        }
        if(studentMapper.getStudentByName(studentVo.getName())!=null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"该学员名称已被注册");
        }
        Student stu = new Student();
        stu.setName(studentVo.getName());
        stu.setAge(studentVo.getAge());
        stu.setSex(studentVo.getSex());
        stu.setSchool(studentVo.getSchool());
        stu.setGuardian(studentVo.getGuardian());
        stu.setStatus(studentVo.getStatus());
        stu.setTuition(studentVo.getTuition());
        stu.setAddress(studentVo.getAddress());
        stu.setCreateTime(LocalDateTime.now());
        stu.setUpdateTime(LocalDateTime.now());
        stu.setFlag(CommonCode.ACTIVE);
        studentMapper.insertStudent(stu);
        result = new BaseResponse<>();
        result.setData(true);
        result.setMsg("添加成功");
        return result;
    }

    public BaseResponse<Boolean> checkData(StudentVo studentVo){
        if(StringUtils.isEmpty(studentVo.getName())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR, "学员名称为空");
        }

        if(studentVo.getAge() == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR, "年龄为空");
        }

        if(studentVo.getSex() != CommonCode.MAM && studentVo.getSex() != CommonCode.WOMAN){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR, "性别输入错误");
        }

        if(StringUtils.isEmpty(studentVo.getName())){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR, "学员名称为空");
        }

        if(studentVo.getStatus() == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR, "状态为空");
        }
        return null;
    }


    /**
     * 编辑学员
     * @param studentVo
     * @return
     */
    public BaseResponse<Boolean> editStudent(StudentVo studentVo){
        BaseResponse<Boolean> result = new BaseResponse<>();
        result = checkData(studentVo);
        if(result != null){
            return result;
        }
//        if(studentMapper.getStudentByName(studentVo.getName())!=null){
//            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"该学员名称已被注册");
//        }
        Student stu = studentMapper.getStudent(studentVo.getId());
        BeanUtils.copyProperties(studentVo,stu);
        stu.setId(studentVo.getId());
        stu.setUpdateTime(LocalDateTime.now());
        studentMapper.updateStudent(stu);
        result = new BaseResponse<>();
        result.setData(true);
        result.setMsg("编辑成功");
        return result;
    }

    /**
     * 删除学员
     * @param stuId
     * @return
     */
    public BaseResponse<Boolean> deleteStudent(Integer stuId){
        BaseResponse<Boolean> result = new BaseResponse<>();
        if(stuId == null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"账户ID为空");
        }
        Student student =  studentMapper.getStudent(stuId);
        if(student==null){
            return BaseResponse.fail(CommonCode.REQUEST_PARAM_ERROR,"不存在该学员名称");
        }


        Student updateStudent =  new Student();
        updateStudent.setId(stuId);
        updateStudent.setUpdateTime(LocalDateTime.now());
        updateStudent.setFlag(CommonCode.UNACTIVE);
        studentMapper.updateStudent(updateStudent);
        result.setData(true);
        result.setMsg("删除用户成功");
        return result;
    }

    /**
     * 获取学员列表
     * @param pageDto
     * @return
     */
    public BaseResponse<Page> studentList(PageDto pageDto){
        List<Student> students = studentMapper.findStudentList(pageDto);
        if(students == null && students.size() == 0){
            return BaseResponse.success(Page.fail());
        }
        Integer total = studentMapper.findStudentListCount();
        Integer pageNumber = pageDto.getPageNumber(total);
        return BaseResponse.success(Page.success(total,pageNumber,students));
    }
}

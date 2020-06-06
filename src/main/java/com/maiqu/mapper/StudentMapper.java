package com.maiqu.mapper;

import com.maiqu.domain.model.Student;
import com.maiqu.domain.model.User;
import com.maiqu.domain.request.dto.PageDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentMapper {
    Student getStudent(Integer id);

    Student getStudentByName(String name);

    Integer insertStudent(Student stu);

    Integer updateStudent(Student stu);

    List<Student> findStudentList(PageDto pageDto);

    Integer findStudentListCount();
}

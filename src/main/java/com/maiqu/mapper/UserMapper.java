package com.maiqu.mapper;

import com.maiqu.domain.model.User;
import com.maiqu.domain.request.dto.PageDto;
import com.maiqu.domain.request.dto.UserDto;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    User getUser(Integer id);

    User getUserByName(String userName);

    Integer insertUser(User user);

    Integer updateUser(User user);

    List<User> findUserList(UserDto userDto);

    Integer findUserListCount(UserDto userDto);
}

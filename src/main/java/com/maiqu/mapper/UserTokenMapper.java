package com.maiqu.mapper;

import com.maiqu.domain.model.User;
import com.maiqu.domain.model.UserToken;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenMapper {
    UserToken getUserToken(Integer userId);

    Integer insertUserToken(UserToken userToken);

    Integer updateUserToken(UserToken userToken);
}

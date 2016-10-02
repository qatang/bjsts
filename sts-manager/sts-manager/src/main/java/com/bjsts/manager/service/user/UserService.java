package com.bjsts.manager.service.user;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.user.UserEntity;
import com.bjsts.manager.query.user.UserSearchable;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author jinsheng
 * @since 2016-04-27 15:54
 */
public interface UserService extends IService<UserEntity, Long> {

    List<Long> findRoleIdByUserId(Long userId);

    ApiResponse<UserEntity> findAll(UserSearchable userSearchable, Pageable pageable, String identifier);

    void bindRole(Long userId, List<Long> roleIdList);

    UserEntity findByUsername(String username);
}

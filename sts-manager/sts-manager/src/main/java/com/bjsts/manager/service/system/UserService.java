package com.bjsts.manager.service.system;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.system.UserEntity;
import com.bjsts.manager.query.system.UserSearchable;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author jinsheng
 * @since 2016-04-27 15:54
 */
public interface UserService extends IService<UserEntity, Long> {

    List<Long> findRoleIdByUserId(Long userId);

    ApiResponse<UserEntity> findAll(UserSearchable userSearchable, Pageable pageable);

    void bindRole(Long userId, List<Long> roleIdList);

    UserEntity findByUsername(String username);
}

package com.bjsts.manager.repository.user;

import com.bjsts.manager.core.repository.IRepository;
import com.bjsts.manager.entity.user.UserEntity;

/**
 * @author jinsheng
 * @since 2016-04-27 14:53
 */
public interface UserRepository extends IRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);
}

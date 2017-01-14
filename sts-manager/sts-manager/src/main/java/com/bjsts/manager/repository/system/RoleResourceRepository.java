package com.bjsts.manager.repository.system;

import com.bjsts.manager.entity.system.RoleResourceEntity;
import com.bjsts.manager.core.repository.IRepository;

import java.util.List;

/**
 * @author jinsheng
 * @since 2016-04-27 14:53
 */
public interface RoleResourceRepository extends IRepository<RoleResourceEntity, Long> {

    List<RoleResourceEntity> findByRoleIdIn(List<Long> roleIdList);
}

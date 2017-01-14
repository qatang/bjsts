package com.bjsts.manager.repository.system;

import com.bjsts.manager.entity.system.ResourceEntity;
import com.bjsts.manager.core.repository.IRepository;

import java.util.List;

/**
 * @author jinsheng
 * @since 2016-04-27 14:53
 */
public interface ResourceRepository extends IRepository<ResourceEntity, Long> {

    List<ResourceEntity> findByParentId(Long parentId);
}

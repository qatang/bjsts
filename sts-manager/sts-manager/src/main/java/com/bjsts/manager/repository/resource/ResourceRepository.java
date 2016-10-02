package com.bjsts.manager.repository.resource;

import com.bjsts.manager.entity.resource.ResourceEntity;
import com.bjsts.manager.core.repository.IRepository;

import java.util.List;

/**
 * @author jinsheng
 * @since 2016-04-27 14:53
 */
public interface ResourceRepository extends IRepository<ResourceEntity, Long> {

    List<ResourceEntity> findByParentId(Long parentId);
}

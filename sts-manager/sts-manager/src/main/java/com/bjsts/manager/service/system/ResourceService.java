package com.bjsts.manager.service.system;

import com.bjsts.manager.entity.system.ResourceEntity;
import com.bjsts.manager.core.service.IService;

import java.util.List;

/**
 * @author jinsheng
 * @since 2016-04-28 16:08
 */
public interface ResourceService extends IService<ResourceEntity, Long> {

    List<ResourceEntity> findByParentId(Long parentId);
}

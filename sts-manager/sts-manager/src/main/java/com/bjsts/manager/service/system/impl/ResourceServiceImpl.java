package com.bjsts.manager.service.system.impl;

import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.system.ResourceEntity;
import com.bjsts.manager.repository.system.ResourceRepository;
import com.bjsts.manager.service.system.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

/**
 * @author jinsheng
 * @since 2016-04-28 16:09
 */
@Service
@Transactional
public class ResourceServiceImpl extends AbstractService<ResourceEntity, Long> implements ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public List<ResourceEntity> findByParentId(Long parentId) {
        return resourceRepository.findByParentId(parentId);
    }

    @Override
    public void updateValid(Long id, EnableDisableStatus toStatus) {
        ResourceEntity resourceEntity = resourceRepository.findOne(id);
        if (Objects.equals(resourceEntity.getValid(), toStatus)) {
            return;
        }
        resourceEntity.setValid(toStatus);
        resourceRepository.saveAndFlush(resourceEntity);
    }
}

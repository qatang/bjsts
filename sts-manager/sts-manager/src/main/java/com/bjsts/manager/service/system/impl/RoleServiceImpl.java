package com.bjsts.manager.service.system.impl;

import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.manager.entity.system.RoleEntity;
import com.bjsts.manager.repository.system.RoleResourceRepository;
import com.google.common.collect.Lists;
import com.bjsts.manager.entity.system.RoleResourceEntity;
import com.bjsts.manager.repository.system.RoleRepository;
import com.bjsts.manager.service.system.RoleService;
import com.bjsts.manager.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author jinsheng
 * @since 2016-04-28 15:59
 */
@Service
@Transactional
public class RoleServiceImpl extends AbstractService<RoleEntity, Long> implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleResourceRepository roleResourceRepository;

    @Override
    public List<Long> findResourceIdByRoleIdIn(List<Long> roleIdList) {
        return roleResourceRepository.findByRoleIdIn(roleIdList).stream().map(RoleResourceEntity::getResourceId).collect(Collectors.toList());
    }

    @Override
    public void bindResource(Long roleId, List<Long> resourceIdList) {

        List<RoleResourceEntity> roleResourceEntityList = roleResourceRepository.findByRoleIdIn(Lists.newArrayList(roleId));

        if (roleResourceEntityList != null && !roleResourceEntityList.isEmpty()) {
            roleResourceRepository.deleteInBatch(roleResourceEntityList);
        }
        if (resourceIdList != null && !resourceIdList.isEmpty()) {
            List<RoleResourceEntity> saveRoleResourceEntityList = Lists.newArrayList();
            resourceIdList.forEach(resourceId -> {
                RoleResourceEntity roleResourceEntity = new RoleResourceEntity();
                roleResourceEntity.setRoleId(roleId);
                roleResourceEntity.setResourceId(resourceId);
                saveRoleResourceEntityList.add(roleResourceEntity);
            });
            roleResourceRepository.save(saveRoleResourceEntityList);
        }
    }

    @Override
    public void updateValid(Long id, EnableDisableStatus toStatus) {
        RoleEntity roleEntity = roleRepository.findOne(id);
        if (Objects.equals(roleEntity.getValid(), toStatus)) {
            return;
        }
        roleEntity.setValid(toStatus);
        roleRepository.saveAndFlush(roleEntity);
    }
}

package com.bjsts.manager.service.system.impl;

import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.system.UserEntity;
import com.bjsts.manager.entity.system.UserRoleEntity;
import com.bjsts.manager.query.system.UserSearchable;
import com.bjsts.manager.repository.system.UserRepository;
import com.bjsts.manager.repository.system.UserRoleRepository;
import com.bjsts.manager.service.system.UserService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jinsheng
 * @since 2016-04-27 15:57
 */
@Service
@Transactional
public class UserServiceImpl extends AbstractService<UserEntity, Long> implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public List<Long> findRoleIdByUserId(Long userId) {
        return userRoleRepository.findByUserId(userId).stream().map(UserRoleEntity::getRoleId).collect(Collectors.toList());
    }

    @Override
    public ApiResponse<UserEntity> findAll(UserSearchable userSearchable, Pageable pageable) {
        ApiRequest request = ApiRequest.newInstance();

       /* if (StringUtils.isNotEmpty(userSearchable.getId())) {
            String[] ids = StringUtils.split(userSearchable.getId(), ",");
            if (ids != null && ids.length > 0) {
                List<Long> idList = Lists.newArrayList();
                Lists.newArrayList(ids).forEach(id -> idList.add(Long.valueOf(id)));
                request.filterIn(QUserInfo.id, idList);
            }
        }

        if (StringUtils.isNotEmpty(userSearchable.getContent())) {
            request.filterOr(
                    new ApiRequestFilter(OperatorType.LIKE, QUserInfo.username, userSearchable.getContent()),
                    new ApiRequestFilter(OperatorType.LIKE, QUserInfo.name, userSearchable.getContent()),
                    new ApiRequestFilter(OperatorType.LIKE, QUserInfo.email, userSearchable.getContent()),
                    new ApiRequestFilter(OperatorType.LIKE, QUserInfo.mobile, userSearchable.getContent())
            );
        }

        if (userSearchable.getBeginCreatedTime() != null) {
            request.filterGreaterEqual(QUserInfo.createdTime, userSearchable.getBeginCreatedTime());
        }

        if (userSearchable.getEndCreatedTime() != null) {
            request.filterLessEqual(QUserInfo.createdTime, userSearchable.getEndCreatedTime());
        }

        if (userSearchable.getValid() != null && !Objects.equals(EnableDisableStatus.ALL, userSearchable.getValid())) {
            request.filterEqual(QUserInfo.valid, userSearchable.getValid());
        }*/

       request.filterEqual("valid", EnableDisableStatus.ENABLE);

        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        userSearchable.convertPageable(requestPage, pageable);

        Page<UserEntity> userEntityPage = userRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(userEntityPage);
    }

    @Override
    public void bindRole(Long userId, List<Long> roleIdList) {

        List<UserRoleEntity> userRoleEntityList = userRoleRepository.findByUserId(userId);

        if (userRoleEntityList != null && !userRoleEntityList.isEmpty()) {
            userRoleRepository.deleteInBatch(userRoleEntityList);
        }

        if (roleIdList != null && !roleIdList.isEmpty()) {
            List<UserRoleEntity> saveUserRoleEntityList = Lists.newArrayList();
            roleIdList.forEach(roleId -> {
                UserRoleEntity userRoleEntity = new UserRoleEntity();
                userRoleEntity.setUserId(userId);
                userRoleEntity.setRoleId(roleId);
                saveUserRoleEntityList.add(userRoleEntity);
            });
            userRoleRepository.save(saveUserRoleEntityList);
        }
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}

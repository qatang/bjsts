package com.bjsts.manager.service.user.impl;

import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.user.StaffEntity;
import com.bjsts.manager.query.user.StaffSearchable;
import com.bjsts.manager.repository.user.StaffRepository;
import com.bjsts.manager.service.user.StaffService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author wangzhiliang
 */
@Service
@Transactional
public class StaffServiceImpl extends AbstractService<StaffEntity, Long> implements StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Override
    public ApiResponse<StaffEntity> findAll(StaffSearchable staffSearchable, Pageable pageable) {
        ApiRequest request = ApiRequest.newInstance();

        if (StringUtils.isNotEmpty(staffSearchable.getRealName())) {
            request.filterLike("realName", staffSearchable.getRealName());
        }

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

        */
        request.filterEqual("valid", EnableDisableStatus.ENABLE);

        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        staffSearchable.convertPageable(requestPage, pageable);

        Page<StaffEntity> staffEntityPage = staffRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(staffEntityPage);
    }

    @Override
    public ApiResponse<StaffEntity> findAll(ApiRequest request, ApiRequestPage requestPage) {
        Page<StaffEntity> page = staffRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(page);
    }
}

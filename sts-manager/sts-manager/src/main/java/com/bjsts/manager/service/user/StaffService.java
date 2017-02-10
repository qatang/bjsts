package com.bjsts.manager.service.user;

import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.user.StaffEntity;
import com.bjsts.manager.query.user.StaffSearchable;
import org.springframework.data.domain.Pageable;

/**
 * @author wangzhiliang
 */
public interface StaffService extends IService<StaffEntity, Long> {

    ApiResponse<StaffEntity> findAll(StaffSearchable staffSearchable, Pageable pageable);

    ApiResponse<StaffEntity> findAll(ApiRequest request, ApiRequestPage requestPage);
}

package com.bjsts.manager.service.user;

import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.user.DepartmentEntity;

/**
 * @author jinsheng
 * @since 2016-04-28 15:44
 */
public interface DepartmentService extends IService<DepartmentEntity, Long> {
    ApiResponse<DepartmentEntity> findAll(ApiRequest request, ApiRequestPage requestPage);
}

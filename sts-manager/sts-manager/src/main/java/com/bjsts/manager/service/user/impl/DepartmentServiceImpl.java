package com.bjsts.manager.service.user.impl;

import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.user.DepartmentEntity;
import com.bjsts.manager.repository.user.DepartmentRepository;
import com.bjsts.manager.service.user.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author jinsheng
 * @since 2016-04-28 15:59
 */
@Service
@Transactional
public class DepartmentServiceImpl extends AbstractService<DepartmentEntity, Long> implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public ApiResponse<DepartmentEntity> findAll(ApiRequest request, ApiRequestPage requestPage) {
        Page<DepartmentEntity> page = departmentRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(page);
    }
}

package com.bjsts.manager.service.produce.impl;

import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.produce.PlanManageEntity;
import com.bjsts.manager.query.produce.PlanManageSearchable;
import com.bjsts.manager.repository.produce.PlanManageRepository;
import com.bjsts.manager.service.produce.PlanManageService;
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
public class PlanManageServiceImpl extends AbstractService<PlanManageEntity, Long> implements PlanManageService {

    @Autowired
    private PlanManageRepository planManageRepository;

    @Override
    public ApiResponse<PlanManageEntity> findAll(PlanManageSearchable planManageSearchable, Pageable pageable) {
        ApiRequest request = ApiRequest.newInstance();

        request.filterEqual("valid", EnableDisableStatus.ENABLE);

        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        planManageSearchable.convertPageable(requestPage, pageable);

        Page<PlanManageEntity> planManageEntityPage = planManageRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(planManageEntityPage);
    }
}

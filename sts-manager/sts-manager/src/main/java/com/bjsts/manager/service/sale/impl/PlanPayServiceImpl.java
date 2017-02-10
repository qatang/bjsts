package com.bjsts.manager.service.sale.impl;

import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.sale.PlanPayEntity;
import com.bjsts.manager.query.sale.PlanPaySearchable;
import com.bjsts.manager.repository.sale.PlanPayRepository;
import com.bjsts.manager.service.sale.PlanPayService;
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
public class PlanPayServiceImpl extends AbstractService<PlanPayEntity, Long> implements PlanPayService {

    @Autowired
    private PlanPayRepository planPayRepository;

    @Override
    public ApiResponse<PlanPayEntity> findAll(PlanPaySearchable planPaySearchable, Pageable pageable) {
        ApiRequest request = ApiRequest.newInstance();

        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        planPaySearchable.convertPageable(requestPage, pageable);

        Page<PlanPayEntity> planEntityPage = planPayRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(planEntityPage);
    }

    @Override
    public PlanPayEntity findByPlanNo(String planNo) {
        return planPayRepository.findByPlanNo(planNo);
    }
}

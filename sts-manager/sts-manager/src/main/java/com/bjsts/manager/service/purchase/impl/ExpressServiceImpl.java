package com.bjsts.manager.service.purchase.impl;

import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.purchase.ExpressEntity;
import com.bjsts.manager.query.purchase.ExpressSearchable;
import com.bjsts.manager.repository.purchase.ExpressRepository;
import com.bjsts.manager.service.purchase.ExpressService;
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
public class ExpressServiceImpl extends AbstractService<ExpressEntity, Long> implements ExpressService {

    @Autowired
    private ExpressRepository expressRepository;

    @Override
    public ApiResponse<ExpressEntity> findAll(ExpressSearchable expressSearchable, Pageable pageable) {
        ApiRequest request = ApiRequest.newInstance();

        request.filterEqual("valid", EnableDisableStatus.ENABLE);

        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        expressSearchable.convertPageable(requestPage, pageable);

        Page<ExpressEntity> expressEntityPage = expressRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(expressEntityPage);
    }
}

package com.bjsts.manager.service.stock.impl;

import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.stock.ProductOutBoundEntity;
import com.bjsts.manager.query.stock.ProductOutBoundSearchable;
import com.bjsts.manager.repository.stock.ProductOutBoundRepository;
import com.bjsts.manager.service.stock.ProductOutBoundService;
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
public class ProductOutBoundServiceImpl extends AbstractService<ProductOutBoundEntity, Long> implements ProductOutBoundService {

    @Autowired
    private ProductOutBoundRepository productOutBoundRepository;

    @Override
    public ApiResponse<ProductOutBoundEntity> findAll(ProductOutBoundSearchable productOutBoundSearchable, Pageable pageable) {
        ApiRequest request = ApiRequest.newInstance();

        request.filterEqual("valid", EnableDisableStatus.ENABLE);

        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        productOutBoundSearchable.convertPageable(requestPage, pageable);

        Page<ProductOutBoundEntity> productOutBoundEntityPage = productOutBoundRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(productOutBoundEntityPage);
    }
}

package com.bjsts.manager.service.stock.impl;

import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.stock.ProductInBoundEntity;
import com.bjsts.manager.query.stock.ProductInBoundSearchable;
import com.bjsts.manager.repository.stock.ProductInBoundRepository;
import com.bjsts.manager.service.stock.ProductInBoundService;
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
public class ProductInBoundServiceImpl extends AbstractService<ProductInBoundEntity, Long> implements ProductInBoundService {

    @Autowired
    private ProductInBoundRepository productInBoundRepository;

    @Override
    public ApiResponse<ProductInBoundEntity> findAll(ProductInBoundSearchable productInBoundSearchable, Pageable pageable) {
        ApiRequest request = ApiRequest.newInstance();

        request.filterEqual("valid", EnableDisableStatus.ENABLE);

        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        productInBoundSearchable.convertPageable(requestPage, pageable);

        Page<ProductInBoundEntity> productInBoundEntityPage = productInBoundRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(productInBoundEntityPage);
    }
}

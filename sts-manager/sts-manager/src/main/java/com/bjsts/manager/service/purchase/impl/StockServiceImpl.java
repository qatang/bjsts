package com.bjsts.manager.service.purchase.impl;

import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.purchase.StockEntity;
import com.bjsts.manager.query.purchase.StockSearchable;
import com.bjsts.manager.repository.purchase.StockRepository;
import com.bjsts.manager.service.purchase.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author jinsheng
 * @since 2016-04-28 15:59
 */
@Service
@Transactional
public class StockServiceImpl extends AbstractService<StockEntity, Long> implements StockService {

    @Autowired
    private StockRepository stockRepository;

    @Override
    public ApiResponse<StockEntity> findAll(StockSearchable stockSearchable, Pageable pageable) {
        ApiRequest request = ApiRequest.newInstance();

        request.filterEqual("valid", EnableDisableStatus.ENABLE);

        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        stockSearchable.convertPageable(requestPage, pageable);

        Page<StockEntity> stockEntityPage = stockRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(stockEntityPage);
    }

    @Override
    public ApiResponse<StockEntity> findAll(ApiRequest request, ApiRequestPage requestPage) {
        Page<StockEntity> page = stockRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(page);
    }
}

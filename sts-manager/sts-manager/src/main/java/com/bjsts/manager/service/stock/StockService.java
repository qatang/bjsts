package com.bjsts.manager.service.stock;

import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.stock.StockEntity;
import com.bjsts.manager.query.stock.StockSearchable;
import org.springframework.data.domain.Pageable;

/**
 * @author jinsheng
 * @since 2016-04-28 15:44
 */
public interface StockService extends IService<StockEntity, Long> {
    ApiResponse<StockEntity> findAll(StockSearchable stockSearchable, Pageable pageable);

    ApiResponse<StockEntity> findAll(ApiRequest request, ApiRequestPage requestPage);
}

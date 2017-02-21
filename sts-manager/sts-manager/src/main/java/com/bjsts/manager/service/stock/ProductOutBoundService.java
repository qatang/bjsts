package com.bjsts.manager.service.stock;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.stock.ProductOutBoundEntity;
import com.bjsts.manager.query.stock.ProductOutBoundSearchable;
import org.springframework.data.domain.Pageable;

/**
 * @author wangzhiliang
 */
public interface ProductOutBoundService extends IService<ProductOutBoundEntity, Long> {
    ApiResponse<ProductOutBoundEntity> findAll(ProductOutBoundSearchable productOutBoundSearchable, Pageable pageable);
}

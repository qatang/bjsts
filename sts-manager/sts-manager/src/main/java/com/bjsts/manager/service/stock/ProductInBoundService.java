package com.bjsts.manager.service.stock;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.stock.ProductInBoundEntity;
import com.bjsts.manager.query.stock.ProductInBoundSearchable;
import org.springframework.data.domain.Pageable;

/**
 * @author wangzhiliang
 */
public interface ProductInBoundService extends IService<ProductInBoundEntity, Long> {
    ApiResponse<ProductInBoundEntity> findAll(ProductInBoundSearchable productInBoundSearchable, Pageable pageable);
}

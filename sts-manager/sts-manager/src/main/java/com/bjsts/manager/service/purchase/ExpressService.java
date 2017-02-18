package com.bjsts.manager.service.purchase;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.purchase.ExpressEntity;
import com.bjsts.manager.query.purchase.ExpressSearchable;
import org.springframework.data.domain.Pageable;

/**
 * @author wangzhiliang
 */
public interface ExpressService extends IService<ExpressEntity, Long> {
    ApiResponse<ExpressEntity> findAll(ExpressSearchable expressSearchable, Pageable pageable);
}

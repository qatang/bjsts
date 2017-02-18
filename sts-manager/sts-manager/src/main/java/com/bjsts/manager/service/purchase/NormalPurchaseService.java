package com.bjsts.manager.service.purchase;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.purchase.NormalPurchaseEntity;
import com.bjsts.manager.query.purchase.NormalPurchaseSearchable;
import org.springframework.data.domain.Pageable;

/**
 * @author wangzhiliang
 */
public interface NormalPurchaseService extends IService<NormalPurchaseEntity, Long> {
    ApiResponse<NormalPurchaseEntity> findAll(NormalPurchaseSearchable normalPurchaseSearchable, Pageable pageable);
}

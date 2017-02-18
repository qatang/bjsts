package com.bjsts.manager.service.purchase;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.purchase.PurchaseEntity;
import com.bjsts.manager.query.purchase.PurchaseSearchable;
import org.springframework.data.domain.Pageable;

/**
 * @author jinsheng
 * @since 2016-04-28 15:44
 */
public interface PurchaseService extends IService<PurchaseEntity, Long> {
    ApiResponse<PurchaseEntity> findAll(PurchaseSearchable purchaseSearchable, Pageable pageable);

    PurchaseEntity save(PurchaseEntity purchaseEntity, String purchaseFileUrl);

    PurchaseEntity findByPurchaseNo(String purchaseNo);
}

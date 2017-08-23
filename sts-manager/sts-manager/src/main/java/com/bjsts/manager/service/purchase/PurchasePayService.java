
package com.bjsts.manager.service.purchase;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.purchase.PurchasePayEntity;
import com.bjsts.manager.query.purchase.PurchasePaySearchable;
import org.springframework.data.domain.Pageable;

/**
 * @author wangzhiliang
 */
public interface PurchasePayService extends IService<PurchasePayEntity, Long> {
    ApiResponse<PurchasePayEntity> findAll(PurchasePaySearchable purchasePaySearchable, Pageable pageable);
}

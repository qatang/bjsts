
package com.bjsts.manager.service.purchase;

import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.purchase.PurchasePayItemEntity;

import java.util.List;

/**
 * @author wangzhiliang
 */
public interface PurchasePayItemService extends IService<PurchasePayItemEntity, Long> {
    List<PurchasePayItemEntity> findByPurchasePayId(Long purchasePayId);
}

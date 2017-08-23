package com.bjsts.manager.repository.purchase;

import com.bjsts.manager.core.repository.IRepository;
import com.bjsts.manager.entity.purchase.PurchasePayEntity;

/**
 * @author wangzhiliang
 */
public interface PurchasePayRepository extends IRepository<PurchasePayEntity, Long> {
    PurchasePayEntity findByPurchaseNo(String purchaseNo);
}

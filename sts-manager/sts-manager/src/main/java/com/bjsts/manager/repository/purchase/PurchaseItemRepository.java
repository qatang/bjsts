package com.bjsts.manager.repository.purchase;

import com.bjsts.manager.core.repository.IRepository;
import com.bjsts.manager.entity.purchase.PurchaseItemEntity;

import java.util.List;

/**
 * @author wangzhiliang
 */
public interface PurchaseItemRepository extends IRepository<PurchaseItemEntity, Long> {
    List<PurchaseItemEntity> findByPurchaseId(Long purchaseId);
}

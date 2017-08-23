package com.bjsts.manager.repository.purchase;

import com.bjsts.manager.core.repository.IRepository;
import com.bjsts.manager.entity.purchase.PurchasePayItemEntity;

import java.util.List;

/**
 * @author wangzhiliang
 */
public interface PurchasePayItemRepository extends IRepository<PurchasePayItemEntity, Long> {

    List<PurchasePayItemEntity> findByPurchasePayId(Long purchasePayId);
}

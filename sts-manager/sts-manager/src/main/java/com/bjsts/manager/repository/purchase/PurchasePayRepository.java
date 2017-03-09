package com.bjsts.manager.repository.purchase;

import com.bjsts.manager.core.repository.IRepository;
import com.bjsts.manager.entity.purchase.PurchasePayEntity;

import java.util.List;

/**
 * @author wangzhiliang
 */
public interface PurchasePayRepository extends IRepository<PurchasePayEntity, Long> {

    List<PurchasePayEntity> findByPurchaseNo(String purchaseNo);
}

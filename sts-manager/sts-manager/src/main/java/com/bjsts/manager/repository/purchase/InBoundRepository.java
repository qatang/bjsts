package com.bjsts.manager.repository.purchase;

import com.bjsts.manager.core.repository.IRepository;
import com.bjsts.manager.entity.purchase.InBoundEntity;

/**
 * @author wangzhiliang
 */
public interface InBoundRepository extends IRepository<InBoundEntity, Long> {
    InBoundEntity findByPurchaseNo(String purchaseNo);
}

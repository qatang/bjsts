package com.bjsts.manager.repository.stock;

import com.bjsts.manager.core.repository.IRepository;
import com.bjsts.manager.entity.stock.InBoundEntity;

/**
 * @author wangzhiliang
 */
public interface InBoundRepository extends IRepository<InBoundEntity, Long> {
    InBoundEntity findByPurchaseNo(String purchaseNo);
}

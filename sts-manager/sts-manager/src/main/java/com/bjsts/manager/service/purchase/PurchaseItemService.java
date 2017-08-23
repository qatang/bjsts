package com.bjsts.manager.service.purchase;

import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.purchase.PurchaseItemEntity;

import java.util.List;

/**
 * @author wangzhiliang
 */
public interface PurchaseItemService extends IService<PurchaseItemEntity, Long> {
    List<PurchaseItemEntity> findByPurchaseId(Long pruchaseId);
}

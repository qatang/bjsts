
package com.bjsts.manager.service.purchase;

import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.purchase.PurchasePayEntity;

import java.util.List;

/**
 * @author wangzhiliang
 */
public interface PurchasePayService extends IService<PurchasePayEntity, Long> {
    List<PurchasePayEntity> findByPurchaseNo(String purchaseNo);
}

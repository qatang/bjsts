package com.bjsts.manager.service.purchase;

import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.purchase.SupplierItemEntity;

import java.util.List;

/**
 * @author wangzhiliang
 */
public interface SupplierItemService extends IService<SupplierItemEntity, Long> {
    List<SupplierItemEntity> findBySupplierId(Long supplierId);
}

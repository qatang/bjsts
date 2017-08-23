package com.bjsts.manager.repository.purchase;

import com.bjsts.manager.core.repository.IRepository;
import com.bjsts.manager.entity.purchase.SupplierItemEntity;

import java.util.List;

/**
 * @author wangzhiliang
 */
public interface SupplierItemRepository extends IRepository<SupplierItemEntity, Long> {
    List<SupplierItemEntity> findBySupplierId(Long supplierId);
}

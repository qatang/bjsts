package com.bjsts.manager.service.purchase.impl;

import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.purchase.SupplierItemEntity;
import com.bjsts.manager.repository.purchase.SupplierItemRepository;
import com.bjsts.manager.service.purchase.SupplierItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author wangzhiliang
 */
@Service
@Transactional
public class SupplierItemServiceImpl extends AbstractService<SupplierItemEntity, Long> implements SupplierItemService {

    @Autowired
    private SupplierItemRepository supplierItemRepository;

    @Override
    public List<SupplierItemEntity> findBySupplierId(Long supplierId) {
        return supplierItemRepository.findBySupplierId(supplierId);
    }
}

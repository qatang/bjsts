package com.bjsts.manager.service.purchase.impl;

import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.purchase.PurchaseItemEntity;
import com.bjsts.manager.repository.purchase.PurchaseItemRepository;
import com.bjsts.manager.service.purchase.PurchaseItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangzhiliang
 */
@Service
@Transactional
public class PurchaseItemServiceImpl extends AbstractService<PurchaseItemEntity, Long> implements PurchaseItemService {

    @Autowired
    private PurchaseItemRepository purchaseItemRepository;

    @Override
    public List<PurchaseItemEntity> findByPurchaseId(Long purchaseId) {
        List<PurchaseItemEntity> list = purchaseItemRepository.findByPurchaseId(purchaseId);
        return list.stream().filter(purchaseItemEntity -> purchaseItemEntity.getValid() == EnableDisableStatus.ENABLE).collect(Collectors.toList());
    }
}

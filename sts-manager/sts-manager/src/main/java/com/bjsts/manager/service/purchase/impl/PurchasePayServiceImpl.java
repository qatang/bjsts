package com.bjsts.manager.service.purchase.impl;

import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.purchase.PurchasePayEntity;
import com.bjsts.manager.repository.purchase.PurchasePayRepository;
import com.bjsts.manager.service.purchase.PurchasePayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author wangzhiliang
 */
@Service
@Transactional
public class PurchasePayServiceImpl extends AbstractService<PurchasePayEntity, Long> implements PurchasePayService {

    @Autowired
    private PurchasePayRepository planPayRepository;

    @Override
    public List<PurchasePayEntity> findByPurchaseNo(String purchaseNo) {
        return planPayRepository.findByPurchaseNo(purchaseNo);
    }
}

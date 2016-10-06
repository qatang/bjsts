package com.bjsts.manager.service.purchase.impl;

import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.purchase.PurchaseEntity;
import com.bjsts.manager.repository.purchase.PurchaseRepository;
import com.bjsts.manager.service.purchase.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author jinsheng
 * @since 2016-04-28 15:59
 */
@Service
@Transactional
public class PurchaseServiceImpl extends AbstractService<PurchaseEntity, Long> implements PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;


}

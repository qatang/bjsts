package com.bjsts.manager.service.sale.impl;

import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.sale.PlanEntity;
import com.bjsts.manager.repository.sale.ProductOrderRepository;
import com.bjsts.manager.service.sale.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author jinsheng
 * @since 2016-04-28 15:59
 */
@Service
@Transactional
public class ProductOrderServiceImpl extends AbstractService<PlanEntity, Long> implements ProductOrderService {

    @Autowired
    private ProductOrderRepository productOrderRepository;


}

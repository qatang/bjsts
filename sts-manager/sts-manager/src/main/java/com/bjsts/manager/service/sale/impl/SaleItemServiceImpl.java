package com.bjsts.manager.service.sale.impl;

import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.sale.PlanTraceEntity;
import com.bjsts.manager.repository.sale.SaleItemRepository;
import com.bjsts.manager.service.sale.SaleItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author jinsheng
 * @since 2016-04-28 15:59
 */
@Service
@Transactional
public class SaleItemServiceImpl extends AbstractService<PlanTraceEntity, Long> implements SaleItemService {

    @Autowired
    private SaleItemRepository saleItemRepository;

    @Override
    public List<PlanTraceEntity> findByPlanNo(String planNo) {
        return saleItemRepository.findByPlanNo(planNo);
    }
}

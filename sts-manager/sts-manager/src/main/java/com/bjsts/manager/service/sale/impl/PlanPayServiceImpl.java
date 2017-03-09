package com.bjsts.manager.service.sale.impl;

import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.sale.PlanPayEntity;
import com.bjsts.manager.repository.sale.PlanPayRepository;
import com.bjsts.manager.service.sale.PlanPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author wangzhiliang
 */
@Service
@Transactional
public class PlanPayServiceImpl extends AbstractService<PlanPayEntity, Long> implements PlanPayService {

    @Autowired
    private PlanPayRepository planPayRepository;

    @Override
    public List<PlanPayEntity> findByContractNo(String contractNo) {
        return planPayRepository.findByContractNo(contractNo);
    }
}

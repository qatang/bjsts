package com.bjsts.manager.service.sale.impl;

import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.sale.ContractEntity;
import com.bjsts.manager.entity.sale.PlanEntity;
import com.bjsts.manager.entity.sale.PlanTraceEntity;
import com.bjsts.manager.entity.system.UserEntity;
import com.bjsts.manager.enums.sale.ContractStatus;
import com.bjsts.manager.enums.sale.PlanStatus;
import com.bjsts.manager.repository.sale.ContractRepository;
import com.bjsts.manager.repository.sale.ProductOrderRepository;
import com.bjsts.manager.repository.sale.SaleItemRepository;
import com.bjsts.manager.service.idgenerator.IdGeneratorService;
import com.bjsts.manager.service.sale.SaleItemService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author jinsheng
 * @since 2016-04-28 15:59
 */
@Service
public class SaleItemServiceImpl extends AbstractService<PlanTraceEntity, Long> implements SaleItemService {

    @Autowired
    private SaleItemRepository saleItemRepository;

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private IdGeneratorService idGeneratorService;

    @Override
    public List<PlanTraceEntity> findByPlanNo(String planNo) {
        return saleItemRepository.findByPlanNo(planNo);
    }

    @Override
    @Transactional
    public PlanTraceEntity save(PlanTraceEntity planTraceEntity, PlanEntity planEntity) {
        PlanEntity db = productOrderRepository.findOneForUpdate(planEntity.getId());
        db.setPlanStatus(planEntity.getPlanStatus());
        productOrderRepository.save(db);

        if (planEntity.getPlanStatus() == PlanStatus.COMPLETE) {
            ContractEntity contractEntity = new ContractEntity();
            contractEntity.setPlanNo(db.getPlanNo());
            contractEntity.setPlanName(db.getName());
            contractEntity.setContractNo(ContractEntity.SEQ_ID_PREFIX_AUTO + idGeneratorService.generateDateFormatted(ContractEntity.SEQ_ID_GENERATOR));
            contractEntity.setStatus(ContractStatus.EXECUTE_NOT);
            contractEntity.setCompany(db.getCompany());
            contractEntity.setLinkman(db.getLinkman());
            contractRepository.save(contractEntity);
        }

        planTraceEntity.setPlanNo(db.getPlanNo());
        UserEntity userInfo = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        planTraceEntity.setUserId(userInfo.getId());
        planTraceEntity.setRealName(userInfo.getRealName());
        planTraceEntity = saleItemRepository.save(planTraceEntity);
        return planTraceEntity;
    }
}

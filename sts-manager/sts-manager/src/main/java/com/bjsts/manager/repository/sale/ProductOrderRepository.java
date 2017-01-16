package com.bjsts.manager.repository.sale;

import com.bjsts.manager.core.repository.IRepository;
import com.bjsts.manager.entity.sale.PlanEntity;
import com.bjsts.manager.enums.sale.PlanStatus;

import java.util.List;

/**
 * @author jinsheng
 * @since 2016-04-27 14:53
 */
public interface ProductOrderRepository extends IRepository<PlanEntity, Long> {
    List<PlanEntity> findByPlanStatus(PlanStatus planStatus);

    PlanEntity findByPlanNo(String planNo);
}

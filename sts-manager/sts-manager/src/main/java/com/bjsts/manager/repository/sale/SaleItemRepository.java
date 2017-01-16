package com.bjsts.manager.repository.sale;

import com.bjsts.manager.core.repository.IRepository;
import com.bjsts.manager.entity.sale.PlanTraceEntity;

import java.util.List;

/**
 * @author jinsheng
 * @since 2016-04-27 14:53
 */
public interface SaleItemRepository extends IRepository<PlanTraceEntity, Long> {
    List<PlanTraceEntity> findByPlanNo(String planNo);
}

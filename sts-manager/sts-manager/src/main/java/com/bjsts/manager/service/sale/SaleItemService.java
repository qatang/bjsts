package com.bjsts.manager.service.sale;

import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.sale.PlanTraceEntity;

import java.util.List;

/**
 * @author jinsheng
 * @since 2016-04-28 15:44
 */
public interface SaleItemService extends IService<PlanTraceEntity, Long> {
    List<PlanTraceEntity> findByPlanNo(String planNo);
}

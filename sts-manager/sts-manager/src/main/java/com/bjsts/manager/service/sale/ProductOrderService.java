package com.bjsts.manager.service.sale;

import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.sale.PlanEntity;

/**
 * @author jinsheng
 * @since 2016-04-28 15:44
 */
public interface ProductOrderService extends IService<PlanEntity, Long> {
    PlanEntity save(PlanEntity planEntity, String fileUrl);
}

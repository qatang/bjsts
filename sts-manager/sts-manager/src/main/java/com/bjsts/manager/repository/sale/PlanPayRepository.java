package com.bjsts.manager.repository.sale;

import com.bjsts.manager.core.repository.IRepository;
import com.bjsts.manager.entity.sale.PlanPayEntity;

/**
 * @author wangzhiliang
 */
public interface PlanPayRepository extends IRepository<PlanPayEntity, Long> {

    PlanPayEntity findByPlanNo(String planNo);
}

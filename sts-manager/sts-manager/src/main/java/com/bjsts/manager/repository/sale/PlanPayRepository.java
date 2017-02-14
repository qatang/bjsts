package com.bjsts.manager.repository.sale;

import com.bjsts.manager.core.repository.IRepository;
import com.bjsts.manager.entity.sale.PlanPayEntity;

import java.util.List;

/**
 * @author wangzhiliang
 */
public interface PlanPayRepository extends IRepository<PlanPayEntity, Long> {

    List<PlanPayEntity> findByPlanNo(String planNo);
}


package com.bjsts.manager.service.sale;

import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.sale.PlanPayEntity;

import java.util.List;

/**
 * @author wangzhiliang
 */
public interface PlanPayService extends IService<PlanPayEntity, Long> {
    List<PlanPayEntity> findByContractNo(String contractNo);
}

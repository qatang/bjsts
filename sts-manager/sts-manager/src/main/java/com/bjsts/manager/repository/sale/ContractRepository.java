package com.bjsts.manager.repository.sale;

import com.bjsts.manager.core.repository.IRepository;
import com.bjsts.manager.entity.sale.ContractEntity;

/**
 * @author jinsheng
 * @since 2016-04-27 14:53
 */
public interface ContractRepository extends IRepository<ContractEntity, Long> {
    ContractEntity findByPlanNo(String planNo);
}

package com.bjsts.manager.repository.customer;

import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.manager.core.repository.IRepository;
import com.bjsts.manager.entity.customer.CustomerItemEntity;

import java.util.List;

/**
 * @author wangzhiliang
 */
public interface CustomerItemRepository extends IRepository<CustomerItemEntity, Long> {
    List<CustomerItemEntity> findByCustomerIdAndValid(Long customerId, EnableDisableStatus status);
}

package com.bjsts.manager.service.customer;

import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.customer.CustomerItemEntity;

import java.util.List;

/**
 * @author wangzhiliang
 */
public interface CustomerItemService extends IService<CustomerItemEntity, Long> {
    List<CustomerItemEntity> findByCustomerId(Long customerId);
}

package com.bjsts.manager.service.customer.impl;

import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.customer.CustomerItemEntity;
import com.bjsts.manager.repository.customer.CustomerItemRepository;
import com.bjsts.manager.service.customer.CustomerItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author jinsheng
 * @since 2016-04-28 15:59
 */
@Service
@Transactional
public class CustomerItemServiceImpl extends AbstractService<CustomerItemEntity, Long> implements CustomerItemService {

    @Autowired
    private CustomerItemRepository customerItemRepository;

    @Override
    public List<CustomerItemEntity> findByCustomerId(Long customerId) {
        return customerItemRepository.findByCustomerIdAndValid(customerId, EnableDisableStatus.ENABLE);
    }
}

package com.bjsts.manager.service.sale.impl;

import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.sale.CustomerEntity;
import com.bjsts.manager.entity.user.DepartmentEntity;
import com.bjsts.manager.repository.sale.CustomerRepository;
import com.bjsts.manager.repository.user.DepartmentRepository;
import com.bjsts.manager.service.sale.CustomerService;
import com.bjsts.manager.service.user.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author jinsheng
 * @since 2016-04-28 15:59
 */
@Service
@Transactional
public class CustomerServiceImpl extends AbstractService<CustomerEntity, Long> implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


}

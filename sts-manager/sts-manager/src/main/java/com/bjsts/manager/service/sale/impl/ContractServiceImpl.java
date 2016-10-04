package com.bjsts.manager.service.sale.impl;

import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.sale.ContractEntity;
import com.bjsts.manager.entity.sale.CustomerEntity;
import com.bjsts.manager.repository.sale.ContractRepository;
import com.bjsts.manager.repository.sale.CustomerRepository;
import com.bjsts.manager.service.sale.ContractService;
import com.bjsts.manager.service.sale.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author jinsheng
 * @since 2016-04-28 15:59
 */
@Service
@Transactional
public class ContractServiceImpl extends AbstractService<ContractEntity, Long> implements ContractService {

    @Autowired
    private ContractRepository contractRepository;


}

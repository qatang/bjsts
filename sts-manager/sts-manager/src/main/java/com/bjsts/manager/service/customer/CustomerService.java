package com.bjsts.manager.service.customer;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.customer.CustomerEntity;
import com.bjsts.manager.query.customer.CustomerSearchable;
import org.springframework.data.domain.Pageable;

/**
 * @author jinsheng
 * @since 2016-04-28 15:44
 */
public interface CustomerService extends IService<CustomerEntity, Long> {
    ApiResponse<CustomerEntity> findAll(CustomerSearchable customerSearchable, Pageable pageable);
}

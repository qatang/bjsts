package com.bjsts.manager.service.customer.impl;

import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.customer.CustomerEntity;
import com.bjsts.manager.query.customer.CustomerSearchable;
import com.bjsts.manager.repository.customer.CustomerRepository;
import com.bjsts.manager.service.customer.CustomerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    @Override
    public ApiResponse<CustomerEntity> findAll(CustomerSearchable customerSearchable, Pageable pageable) {
        ApiRequest request = ApiRequest.newInstance();

        request.filterEqual("valid", EnableDisableStatus.ENABLE);

        String companyName = customerSearchable.getCompanyName();
        if (!StringUtils.isEmpty(companyName)) {
            request.filterLike("companyName", companyName);
        }

        String tel = customerSearchable.getTel();
        if (!StringUtils.isEmpty(tel)) {
            request.filterLike("tel", tel);
        }

        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        customerSearchable.convertPageable(requestPage, pageable);

        Page<CustomerEntity> customerEntityPage = customerRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(customerEntityPage);
    }
}

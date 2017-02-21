package com.bjsts.manager.service.purchase.impl;

import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.purchase.SupplierEntity;
import com.bjsts.manager.query.purchase.SupplierSearchable;
import com.bjsts.manager.repository.purchase.SupplierRepository;
import com.bjsts.manager.service.purchase.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author wangzhiliang
 */
@Service
@Transactional
public class SupplierServiceImpl extends AbstractService<SupplierEntity, Long> implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public ApiResponse<SupplierEntity> findAll(SupplierSearchable supplierSearchable, Pageable pageable) {
        ApiRequest request = ApiRequest.newInstance();

        request.filterEqual("valid", EnableDisableStatus.ENABLE);

        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        supplierSearchable.convertPageable(requestPage, pageable);

        Page<SupplierEntity> supplierEntityPage = supplierRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(supplierEntityPage);
    }
}

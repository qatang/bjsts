package com.bjsts.manager.service.purchase;

import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.purchase.SupplierEntity;
import com.bjsts.manager.query.purchase.SupplierSearchable;
import org.springframework.data.domain.Pageable;

/**
 * @author wangzhiliang
 */
public interface SupplierService extends IService<SupplierEntity, Long> {
    ApiResponse<SupplierEntity> findAll(SupplierSearchable supplierSearchable, Pageable pageable);

    ApiResponse<SupplierEntity> findAll(ApiRequest request, ApiRequestPage requestPage);
}

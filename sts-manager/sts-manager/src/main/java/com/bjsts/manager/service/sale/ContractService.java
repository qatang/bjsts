package com.bjsts.manager.service.sale;

import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.document.DocumentEntity;
import com.bjsts.manager.entity.sale.ContractEntity;
import com.bjsts.manager.enums.sale.ContractStatus;
import com.bjsts.manager.query.sale.ContractSearchable;
import com.bjsts.manager.query.sale.ContractSum;
import org.springframework.data.domain.Pageable;

/**
 * @author jinsheng
 * @since 2016-04-28 15:44
 */
public interface ContractService extends IService<ContractEntity, Long> {
    ApiResponse<ContractEntity> findAll(ContractSearchable contractSearchable, Pageable pageable);

    ContractEntity save(ContractEntity contractEntity, DocumentEntity documentEntity);

    ContractEntity updateStatus(Long id, ContractStatus toStatus);

    ContractEntity findByPlanNo(String planNo);

    ApiResponse<ContractEntity> findAll(ApiRequest request, ApiRequestPage requestPage);

    ContractSum sumAll(ContractSearchable contractSearchable);
}

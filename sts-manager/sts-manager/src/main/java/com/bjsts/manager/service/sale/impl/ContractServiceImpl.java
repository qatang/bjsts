package com.bjsts.manager.service.sale.impl;

import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.document.DocumentEntity;
import com.bjsts.manager.entity.sale.ContractEntity;
import com.bjsts.manager.query.sale.ContractSearchable;
import com.bjsts.manager.repository.document.DocumentRepository;
import com.bjsts.manager.repository.sale.ContractRepository;
import com.bjsts.manager.service.sale.ContractService;
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
public class ContractServiceImpl extends AbstractService<ContractEntity, Long> implements ContractService {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public ApiResponse<ContractEntity> findAll(ContractSearchable contractSearchable, Pageable pageable) {
        ApiRequest request = ApiRequest.newInstance();

        request.filterEqual("valid", EnableDisableStatus.ENABLE);

        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        contractSearchable.convertPageable(requestPage, pageable);

        Page<ContractEntity> contractEntityPage = contractRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(contractEntityPage);
    }

    @Override
    public ContractEntity save(ContractEntity contractEntity, String quoteFileUrl) {
        ContractEntity db = contractRepository.save(contractEntity);
        DocumentEntity existDocumentEntity = documentRepository.findByGroupKeyAndObjectId(GlobalConstants.CONTRACT_FILE, db.getContractNo());

        if (existDocumentEntity == null) {
            existDocumentEntity = new DocumentEntity();
            existDocumentEntity.setName(contractEntity.getContractNo());
            existDocumentEntity.setGroupKey(GlobalConstants.CONTRACT_FILE);
            existDocumentEntity.setUrl(quoteFileUrl);
            existDocumentEntity.setObjectId(contractEntity.getContractNo());
        } else {
            existDocumentEntity.setUrl(quoteFileUrl);
        }
        DocumentEntity dbDocumentEntity = documentRepository.save(existDocumentEntity);

        db.setContractUrl(dbDocumentEntity.getId());
        return db;
    }

    @Override
    public ContractEntity findByPlanNo(String planNo) {
        return contractRepository.findByPlanNo(planNo);
    }

    @Override
    public ApiResponse<ContractEntity> findAll(ApiRequest request, ApiRequestPage requestPage) {
        Page<ContractEntity> page = contractRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(page);
    }
}

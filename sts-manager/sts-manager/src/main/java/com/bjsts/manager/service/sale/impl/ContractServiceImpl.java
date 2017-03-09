package com.bjsts.manager.service.sale.impl;

import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.document.DocumentEntity;
import com.bjsts.manager.entity.sale.ContractEntity;
import com.bjsts.manager.enums.document.DocumentType;
import com.bjsts.manager.enums.sale.ContractStatus;
import com.bjsts.manager.query.sale.ContractSearchable;
import com.bjsts.manager.query.sale.ContractSum;
import com.bjsts.manager.repository.document.DocumentRepository;
import com.bjsts.manager.repository.sale.ContractRepository;
import com.bjsts.manager.service.sale.ContractService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author jinsheng
 * @since 2016-04-28 15:59
 */
@Service
public class ContractServiceImpl extends AbstractService<ContractEntity, Long> implements ContractService {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public ApiResponse<ContractEntity> findAll(ContractSearchable contractSearchable, Pageable pageable) {
        ApiRequest request = ApiRequest.newInstance();

        if (StringUtils.isNotEmpty(contractSearchable.getCompany())) {
            request.filterLike("company", contractSearchable.getCompany());
        }

        if (contractSearchable.getSignTime() != null) {
            request.filterEqual("signTime", contractSearchable.getSignTime());
        }

        if (StringUtils.isNotEmpty(contractSearchable.getSign())) {
            request.filterLike("sign", contractSearchable.getSign());
        }

        if (contractSearchable.getQualityTime() != null) {
            request.filterEqual("qualityTime", contractSearchable.getQualityTime());
        }

        if (contractSearchable.getContractStatusList() != null && !contractSearchable.getContractStatusList().isEmpty()) {
            request.filterIn("status", contractSearchable.getContractStatusList());
        }

        request.filterEqual("valid", EnableDisableStatus.ENABLE);

        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        contractSearchable.convertPageable(requestPage, pageable);

        Page<ContractEntity> contractEntityPage = contractRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(contractEntityPage);
    }

    @Override
    @Transactional
    public ContractEntity save(ContractEntity contractEntity, DocumentEntity documentEntity) {
        ContractEntity db = contractRepository.save(contractEntity);
        DocumentEntity existDocumentEntity = documentRepository.findByGroupKeyAndObjectId(DocumentType.CONTRACT.getEnglishName(), db.getContractNo());

        if (existDocumentEntity == null) {
            existDocumentEntity = new DocumentEntity();
            existDocumentEntity.setName(documentEntity.getName());
            existDocumentEntity.setGroupKey(DocumentType.CONTRACT.getEnglishName());
            existDocumentEntity.setUrl(documentEntity.getUrl());
            existDocumentEntity.setObjectId(contractEntity.getContractNo());
        } else {
            existDocumentEntity.setName(documentEntity.getName());
            existDocumentEntity.setUrl(documentEntity.getUrl());
        }
        DocumentEntity dbDocumentEntity = documentRepository.save(existDocumentEntity);

        db.setContractUrl(dbDocumentEntity.getId());
        return db;
    }

    @Override
    @Transactional
    public ContractEntity updateStatus(Long id, ContractStatus toStatus) {
        ContractEntity contractEntity = contractRepository.findOneForUpdate(id);
        contractEntity.setStatus(toStatus);
        return contractRepository.save(contractEntity);
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

    @Override
    public ContractSum sumAll(ContractSearchable contractSearchable) {
        ApiRequest request = ApiRequest.newInstance();

        if (StringUtils.isNotEmpty(contractSearchable.getCompany())) {
            request.filterLike("company", contractSearchable.getCompany());
        }


        if (contractSearchable.getSignTime() != null) {
            request.filterEqual("signTime", contractSearchable.getSignTime());
        }

        if (StringUtils.isNotEmpty(contractSearchable.getSign())) {
            request.filterLike("sign", contractSearchable.getSign());
        }

        request.filterEqual("valid", EnableDisableStatus.ENABLE);

        ContractSum contractSum = new ContractSum();

        List<BigDecimal> sumResultList = contractRepository.multiSum(convertSpecification(request),
                "amount");

        if (sumResultList != null && !sumResultList.isEmpty()) {
            contractSum.setAmount(sumResultList.get(0) == null ? 0 : sumResultList.get(0).longValue());
        }
        return contractSum;
    }
}

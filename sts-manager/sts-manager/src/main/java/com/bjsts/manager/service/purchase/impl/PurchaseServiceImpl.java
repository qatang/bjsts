package com.bjsts.manager.service.purchase.impl;

import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.core.enums.YesNoStatus;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.document.DocumentEntity;
import com.bjsts.manager.entity.purchase.PurchaseEntity;
import com.bjsts.manager.enums.document.DocumentType;
import com.bjsts.manager.query.purchase.PurchaseSearchable;
import com.bjsts.manager.repository.document.DocumentRepository;
import com.bjsts.manager.repository.purchase.PurchaseRepository;
import com.bjsts.manager.service.purchase.PurchaseService;
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
public class PurchaseServiceImpl extends AbstractService<PurchaseEntity, Long> implements PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public ApiResponse<PurchaseEntity> findAll(PurchaseSearchable purchaseSearchable, Pageable pageable) {
        ApiRequest request = ApiRequest.newInstance();

        request.filterEqual("valid", EnableDisableStatus.ENABLE);

        YesNoStatus inBound = purchaseSearchable.getInBound();
        if (inBound != null) {
            request.filterEqual("inBound", inBound);
        }

        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        purchaseSearchable.convertPageable(requestPage, pageable);

        Page<PurchaseEntity> purchaseEntityPage = purchaseRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(purchaseEntityPage);
    }

    @Override
    public PurchaseEntity save(PurchaseEntity purchaseEntity, DocumentEntity documentEntity) {
        PurchaseEntity db = purchaseRepository.save(purchaseEntity);
        DocumentEntity existDocumentEntity = documentRepository.findByGroupKeyAndObjectId(DocumentType.PURCHASE.getEnglishName(), db.getPurchaseNo());

        if (existDocumentEntity == null) {
            existDocumentEntity = new DocumentEntity();
            existDocumentEntity.setName(documentEntity.getName());
            existDocumentEntity.setGroupKey(DocumentType.PURCHASE.getEnglishName());
            existDocumentEntity.setUrl(documentEntity.getUrl());
            existDocumentEntity.setObjectId(purchaseEntity.getPurchaseNo());
        } else {
            existDocumentEntity.setName(documentEntity.getName());
            existDocumentEntity.setUrl(documentEntity.getUrl());
        }
        DocumentEntity dbDocumentEntity = documentRepository.save(existDocumentEntity);

        db.setPurchaseContractUrl(dbDocumentEntity.getId());
        return db;
    }

    @Override
    public PurchaseEntity findByPurchaseNo(String purchaseNo) {
        return purchaseRepository.findByPurchaseNo(purchaseNo);
    }

}

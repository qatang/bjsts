package com.bjsts.manager.service.sale.impl;

import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.document.DocumentEntity;
import com.bjsts.manager.entity.sale.PlanEntity;
import com.bjsts.manager.enums.sale.PlanStatus;
import com.bjsts.manager.query.sale.QuoteSearchable;
import com.bjsts.manager.query.sale.SaleItemSearchable;
import com.bjsts.manager.repository.document.DocumentRepository;
import com.bjsts.manager.repository.sale.ProductOrderRepository;
import com.bjsts.manager.service.sale.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author jinsheng
 * @since 2016-04-28 15:59
 */
@Service
@Transactional
public class ProductOrderServiceImpl extends AbstractService<PlanEntity, Long> implements ProductOrderService {

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Autowired
    private DocumentRepository documentRepository;

    public PlanEntity save(PlanEntity planEntity, String fileUrl) {
        PlanEntity db = productOrderRepository.save(planEntity);
        DocumentEntity existDocumentEntity = documentRepository.findByGroupKeyAndObjectId(GlobalConstants.CUSTOMER_FILE, db.getPlanNo());

        if (existDocumentEntity == null) {
            existDocumentEntity = new DocumentEntity();
            existDocumentEntity.setName(planEntity.getPlanNo());
            existDocumentEntity.setGroupKey(GlobalConstants.CUSTOMER_FILE);
            existDocumentEntity.setUrl(fileUrl);
            existDocumentEntity.setObjectId(planEntity.getPlanNo());
        } else {
            existDocumentEntity.setUrl(fileUrl);
        }
        DocumentEntity dbDocumentEntity = documentRepository.save(existDocumentEntity);

        db.setDocumentId(dbDocumentEntity.getId());
        return db;
    }

    public PlanEntity saveQuote(PlanEntity planEntity, String quoteFileUrl) {
        PlanEntity db = productOrderRepository.save(planEntity);
        DocumentEntity existDocumentEntity = documentRepository.findByGroupKeyAndObjectId(GlobalConstants.QUOTE_FILE, db.getPlanNo());

        if (existDocumentEntity == null) {
            existDocumentEntity = new DocumentEntity();
            existDocumentEntity.setName(planEntity.getPlanNo());
            existDocumentEntity.setGroupKey(GlobalConstants.QUOTE_FILE);
            existDocumentEntity.setUrl(quoteFileUrl);
            existDocumentEntity.setObjectId(planEntity.getPlanNo());
        } else {
            existDocumentEntity.setUrl(quoteFileUrl);
        }
        DocumentEntity dbDocumentEntity = documentRepository.save(existDocumentEntity);

        db.setQuoteFileId(dbDocumentEntity.getId());
        return db;
    }

    @Override
    public ApiResponse<PlanEntity> findAll(QuoteSearchable quoteSearchable, Pageable pageable) {
        ApiRequest request = ApiRequest.newInstance();

/*        PlanStatus planStatus = quoteSearchable.getPlanStatus();
        if (planStatus != null && planStatus != PlanStatus.ALL) {
            request.filterEqual("planStatus", planStatus);
        }*/

        List<PlanStatus> planStatusList = quoteSearchable.getPlanStatusList();
        if (planStatusList != null && !planStatusList.isEmpty()) {
            request.filterIn("planStatus", planStatusList);
        }

        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        quoteSearchable.convertPageable(requestPage, pageable);

        Page<PlanEntity> userEntityPage = productOrderRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(userEntityPage);
    }

    @Override
    public ApiResponse<PlanEntity> findAll(SaleItemSearchable saleItemSearchable, Pageable pageable) {
        ApiRequest request = ApiRequest.newInstance();

        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        saleItemSearchable.convertPageable(requestPage, pageable);

        Page<PlanEntity> userEntityPage = productOrderRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(userEntityPage);
    }

    @Override
    public PlanEntity findByPlanNo(String planNo) {
        return productOrderRepository.findByPlanNo(planNo);
    }
}

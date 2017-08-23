package com.bjsts.manager.service.invoice.impl;

import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.document.DocumentEntity;
import com.bjsts.manager.entity.invoice.SaleInvoiceEntity;
import com.bjsts.manager.enums.document.DocumentType;
import com.bjsts.manager.enums.invoice.InvoiceCategory;
import com.bjsts.manager.enums.invoice.InvoiceStatus;
import com.bjsts.manager.query.invoice.SaleInvoiceSearchable;
import com.bjsts.manager.query.invoice.SaleInvoiceSum;
import com.bjsts.manager.repository.document.DocumentRepository;
import com.bjsts.manager.repository.invoice.SaleInvoiceRepository;
import com.bjsts.manager.service.invoice.SaleInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author wangzhiliang
 */
@Service
@Transactional
public class SaleInvoiceServiceImpl extends AbstractService<SaleInvoiceEntity, Long> implements SaleInvoiceService {

    @Autowired
    private SaleInvoiceRepository saleInvoiceRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public ApiResponse<SaleInvoiceEntity> findAll(SaleInvoiceSearchable saleInvoiceSearchable, Pageable pageable) {
        ApiRequest request = generateRequest(saleInvoiceSearchable);

        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        saleInvoiceSearchable.convertPageable(requestPage, pageable);

        Page<SaleInvoiceEntity> saleInvoiceEntityPage = saleInvoiceRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(saleInvoiceEntityPage);
    }


    @Override
    public SaleInvoiceEntity save(SaleInvoiceEntity saleInvoiceEntity, DocumentEntity documentEntity) {
        SaleInvoiceEntity db = saleInvoiceRepository.save(saleInvoiceEntity);
        DocumentEntity existDocumentEntity = documentRepository.findByGroupKeyAndObjectId(DocumentType.SALE_INVOICE.getEnglishName(), db.getInvoiceNo());

        if (existDocumentEntity == null) {
            existDocumentEntity = new DocumentEntity();
            existDocumentEntity.setName(documentEntity.getName());
            existDocumentEntity.setGroupKey(DocumentType.SALE_INVOICE.getEnglishName());
            existDocumentEntity.setUrl(documentEntity.getUrl());
            existDocumentEntity.setObjectId(saleInvoiceEntity.getInvoiceNo());
        } else {
            existDocumentEntity.setName(documentEntity.getName());
            existDocumentEntity.setUrl(documentEntity.getUrl());
        }
        DocumentEntity dbDocumentEntity = documentRepository.save(existDocumentEntity);

        db.setInvoiceUrl(dbDocumentEntity.getId());
        return db;
    }

    @Override
    public SaleInvoiceSum sumAll(SaleInvoiceSearchable saleInvoiceSearchable) {
        ApiRequest request = generateRequest(saleInvoiceSearchable);

        SaleInvoiceSum saleInvoiceSum = new SaleInvoiceSum();

        List<BigDecimal> sumResultList = saleInvoiceRepository.multiSum(convertSpecification(request),
                "amount");

        if (sumResultList != null && !sumResultList.isEmpty()) {
            saleInvoiceSum.setAmount(sumResultList.get(0) == null ? 0 : sumResultList.get(0).longValue());
        }
        return saleInvoiceSum;
    }


    private ApiRequest generateRequest(SaleInvoiceSearchable saleInvoiceSearchable) {
        ApiRequest request = ApiRequest.newInstance();

        request.filterEqual("valid", EnableDisableStatus.ENABLE);

        InvoiceCategory invoiceCategory = saleInvoiceSearchable.getInvoiceCategory();
        if (invoiceCategory != null && invoiceCategory != InvoiceCategory.ALL) {
            request.filterEqual("invoiceCategory", saleInvoiceSearchable.getInvoiceCategory());
        }

        InvoiceStatus invoiceStatus = saleInvoiceSearchable.getInvoiceStatus();
        if (invoiceStatus != null && invoiceStatus != InvoiceStatus.ALL) {
            request.filterEqual("invoiceStatus", saleInvoiceSearchable.getInvoiceStatus());
        }

        Date beginDate = saleInvoiceSearchable.getBeginCreatedTime();
        if (beginDate != null) {
            request.filterGreaterEqual("invoiceDate", beginDate);
        }

        Date endDate = saleInvoiceSearchable.getEndCreatedTime();
        if (endDate != null) {
            request.filterLessEqual("invoiceDate", endDate);
        }

        String planContent = saleInvoiceSearchable.getPlanContent();
        if (!StringUtils.isEmpty(planContent)) {
            request.filterLike("planContent", planContent);
        }

        String customer = saleInvoiceSearchable.getCustomer();
        if (!StringUtils.isEmpty(customer)) {
            request.filterLike("customer", customer);
        }

        return request;
    }
}

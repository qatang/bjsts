package com.bjsts.manager.service.invoice.impl;

import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.document.DocumentEntity;
import com.bjsts.manager.entity.invoice.InvoiceEntity;
import com.bjsts.manager.enums.document.DocumentType;
import com.bjsts.manager.enums.invoice.InvoiceCategory;
import com.bjsts.manager.query.invoice.InvoiceSearchable;
import com.bjsts.manager.query.invoice.InvoiceSum;
import com.bjsts.manager.repository.document.DocumentRepository;
import com.bjsts.manager.repository.invoice.InvoiceRepository;
import com.bjsts.manager.service.invoice.InvoiceService;
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
public class InvoiceServiceImpl extends AbstractService<InvoiceEntity, Long> implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public ApiResponse<InvoiceEntity> findAll(InvoiceSearchable invoiceSearchable, Pageable pageable) {
        ApiRequest request = generateRequest(invoiceSearchable);

        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        invoiceSearchable.convertPageable(requestPage, pageable);

        Page<InvoiceEntity> invoiceEntityPage = invoiceRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(invoiceEntityPage);
    }


    @Override
    public InvoiceEntity save(InvoiceEntity invoiceEntity, DocumentEntity documentEntity) {
        InvoiceEntity db = invoiceRepository.save(invoiceEntity);
        DocumentEntity existDocumentEntity = documentRepository.findByGroupKeyAndObjectId(DocumentType.INVOICE.getEnglishName(), db.getInvoiceNo());

        if (existDocumentEntity == null) {
            existDocumentEntity = new DocumentEntity();
            existDocumentEntity.setName(documentEntity.getName());
            existDocumentEntity.setGroupKey(DocumentType.INVOICE.getEnglishName());
            existDocumentEntity.setUrl(documentEntity.getUrl());
            existDocumentEntity.setObjectId(invoiceEntity.getInvoiceNo());
        } else {
            existDocumentEntity.setName(documentEntity.getName());
            existDocumentEntity.setUrl(documentEntity.getUrl());
        }
        DocumentEntity dbDocumentEntity = documentRepository.save(existDocumentEntity);

        db.setInvoiceUrl(dbDocumentEntity.getId());
        return db;
    }

    @Override
    public InvoiceSum sumAll(InvoiceSearchable invoiceSearchable) {
        ApiRequest request = generateRequest(invoiceSearchable);

        InvoiceSum invoiceSum = new InvoiceSum();

        List<BigDecimal> sumResultList = invoiceRepository.multiSum(convertSpecification(request),
                "amount");

        if (sumResultList != null && !sumResultList.isEmpty()) {
            invoiceSum.setAmount(sumResultList.get(0) == null ? 0 : sumResultList.get(0).longValue());
        }
        return invoiceSum;
    }

    private ApiRequest generateRequest(InvoiceSearchable invoiceSearchable) {
        ApiRequest request = ApiRequest.newInstance();

        request.filterEqual("valid", EnableDisableStatus.ENABLE);

        InvoiceCategory invoiceCategory = invoiceSearchable.getInvoiceCategory();
        if (invoiceCategory != null && invoiceCategory != InvoiceCategory.ALL) {
            request.filterEqual("invoiceCategory", invoiceSearchable.getInvoiceCategory());
        }

        Date beginDate = invoiceSearchable.getBeginCreatedTime();
        if (beginDate != null) {
            request.filterGreaterEqual("invoiceDate", beginDate);
        }

        Date endDate = invoiceSearchable.getEndCreatedTime();
        if (endDate != null) {
            request.filterLessEqual("invoiceDate", endDate);
        }

        String planContent = invoiceSearchable.getPlanContent();
        if (!StringUtils.isEmpty(planContent)) {
            request.filterLike("planContent", planContent);
        }

        String customer = invoiceSearchable.getCustomer();
        if (!StringUtils.isEmpty(customer)) {
            request.filterLike("customer", customer);
        }

        return request;
    }
}

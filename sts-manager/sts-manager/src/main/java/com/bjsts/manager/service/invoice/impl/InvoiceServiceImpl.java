package com.bjsts.manager.service.invoice.impl;

import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.document.DocumentEntity;
import com.bjsts.manager.entity.invoice.InvoiceEntity;
import com.bjsts.manager.query.invoice.InvoiceSearchable;
import com.bjsts.manager.repository.document.DocumentRepository;
import com.bjsts.manager.repository.invoice.InvoiceRepository;
import com.bjsts.manager.service.invoice.InvoiceService;
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
public class InvoiceServiceImpl extends AbstractService<InvoiceEntity, Long> implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public ApiResponse<InvoiceEntity> findAll(InvoiceSearchable invoiceSearchable, Pageable pageable) {
        ApiRequest request = ApiRequest.newInstance();

        request.filterEqual("valid", EnableDisableStatus.ENABLE);

        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        invoiceSearchable.convertPageable(requestPage, pageable);

        Page<InvoiceEntity> invoiceEntityPage = invoiceRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(invoiceEntityPage);
    }


    @Override
    public InvoiceEntity save(InvoiceEntity invoiceEntity, String invoiceFileUrl) {
        InvoiceEntity db = invoiceRepository.save(invoiceEntity);
        DocumentEntity existDocumentEntity = documentRepository.findByGroupKeyAndObjectId(GlobalConstants.INVOICE_FILE, db.getInvoiceNo());

        if (existDocumentEntity == null) {
            existDocumentEntity = new DocumentEntity();
            existDocumentEntity.setName(invoiceEntity.getInvoiceNo());
            existDocumentEntity.setGroupKey(GlobalConstants.INVOICE_FILE);
            existDocumentEntity.setUrl(invoiceFileUrl);
            existDocumentEntity.setObjectId(invoiceEntity.getInvoiceNo());
        } else {
            existDocumentEntity.setUrl(invoiceFileUrl);
        }
        DocumentEntity dbDocumentEntity = documentRepository.save(existDocumentEntity);

        db.setInvoiceUrl(dbDocumentEntity.getId());
        return db;
    }
}

package com.bjsts.manager.service.invoice;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.document.DocumentEntity;
import com.bjsts.manager.entity.invoice.InvoiceEntity;
import com.bjsts.manager.query.invoice.InvoiceSearchable;
import com.bjsts.manager.query.invoice.InvoiceSum;
import org.springframework.data.domain.Pageable;

/**
 * @author wangzhiliang
 */
public interface InvoiceService extends IService<InvoiceEntity, Long> {
    ApiResponse<InvoiceEntity> findAll(InvoiceSearchable invoiceSearchable, Pageable pageable);

    InvoiceEntity save(InvoiceEntity invoiceEntity, DocumentEntity documentEntity);

    InvoiceSum sumAll(InvoiceSearchable invoiceSearchable);
}

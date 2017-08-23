package com.bjsts.manager.service.invoice;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.document.DocumentEntity;
import com.bjsts.manager.entity.invoice.SaleInvoiceEntity;
import com.bjsts.manager.query.invoice.SaleInvoiceSearchable;
import com.bjsts.manager.query.invoice.SaleInvoiceSum;
import org.springframework.data.domain.Pageable;

/**
 * @author wangzhiliang
 */
public interface SaleInvoiceService extends IService<SaleInvoiceEntity, Long> {
    ApiResponse<SaleInvoiceEntity> findAll(SaleInvoiceSearchable saleInvoiceSearchable, Pageable pageable);

    SaleInvoiceEntity save(SaleInvoiceEntity saleInvoiceEntity, DocumentEntity documentEntity);

    SaleInvoiceSum sumAll(SaleInvoiceSearchable saleInvoiceSearchable);
}

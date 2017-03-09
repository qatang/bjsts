package com.bjsts.manager.service.sale;

import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.document.DocumentEntity;
import com.bjsts.manager.entity.sale.PlanEntity;
import com.bjsts.manager.query.sale.ProductOrderSearchable;
import com.bjsts.manager.query.sale.QuoteSearchable;
import com.bjsts.manager.query.sale.SaleItemSearchable;
import com.bjsts.manager.query.sale.SaleHistorySearchable;
import org.springframework.data.domain.Pageable;

/**
 * @author jinsheng
 * @since 2016-04-28 15:44
 */
public interface ProductOrderService extends IService<PlanEntity, Long> {
    PlanEntity save(PlanEntity planEntity, DocumentEntity documentEntity);

    PlanEntity saveQuote(PlanEntity planEntity, DocumentEntity documentEntity);

    ApiResponse<PlanEntity> findAll(ProductOrderSearchable productOrderSearchable, Pageable pageable);

    ApiResponse<PlanEntity> findAll(QuoteSearchable quoteSearchable, Pageable pageable);

    ApiResponse<PlanEntity> findAll(SaleItemSearchable saleItemSearchable, Pageable pageable);

    ApiResponse<PlanEntity> findAll(SaleHistorySearchable saleHistorySearchable, Pageable pageable);

    PlanEntity findByPlanNo(String planNo);

    ApiResponse<PlanEntity> findAll(ApiRequest request, ApiRequestPage requestPage);
}

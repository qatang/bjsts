package com.bjsts.manager.service.sale;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.sale.PlanEntity;
import com.bjsts.manager.query.sale.QuoteSearchable;
import org.springframework.data.domain.Pageable;

/**
 * @author jinsheng
 * @since 2016-04-28 15:44
 */
public interface ProductOrderService extends IService<PlanEntity, Long> {
    PlanEntity save(PlanEntity planEntity, String fileUrl);

    PlanEntity saveQuote(PlanEntity planEntity, String quoteFileUrl);

    ApiResponse<PlanEntity> findAll(QuoteSearchable quoteSearchable, Pageable pageable);
}

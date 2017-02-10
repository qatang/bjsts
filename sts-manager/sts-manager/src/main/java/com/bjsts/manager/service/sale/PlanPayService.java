
package com.bjsts.manager.service.sale;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.sale.PlanPayEntity;
import com.bjsts.manager.query.sale.PlanPaySearchable;
import org.springframework.data.domain.Pageable;

/**
 * @author wangzhiliang
 */
public interface PlanPayService extends IService<PlanPayEntity, Long> {
    ApiResponse<PlanPayEntity> findAll(PlanPaySearchable planPaySearchable, Pageable pageable);

    PlanPayEntity findByPlanNo(String planNo);
}

package com.bjsts.manager.service.produce;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.produce.PlanManageEntity;
import com.bjsts.manager.query.produce.PlanManageSearchable;
import org.springframework.data.domain.Pageable;

/**
 * @author wangzhiliang
 */
public interface PlanManageService extends IService<PlanManageEntity, Long> {
    ApiResponse<PlanManageEntity> findAll(PlanManageSearchable planManageSearchable, Pageable pageable);
}

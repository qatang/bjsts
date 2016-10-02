package com.bjsts.manager.core.service;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.query.CommonSearchable;
import org.springframework.data.domain.Pageable;

/**
 * @author jinsheng
 * @since 2016-06-12 10:47
 */
public interface CommonService<T> {

    ApiResponse<T> findAll(CommonSearchable commonSearchable, Pageable pageable);
}

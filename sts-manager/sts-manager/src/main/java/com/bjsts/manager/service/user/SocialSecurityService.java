package com.bjsts.manager.service.user;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.user.SocialSecurityEntity;
import com.bjsts.manager.query.user.SocialSecuritySearchable;
import org.springframework.data.domain.Pageable;

/**
 * @author jinsheng
 * @since 2016-04-27 15:54
 */
public interface SocialSecurityService extends IService<SocialSecurityEntity, Long> {
    ApiResponse<SocialSecurityEntity> findAll(SocialSecuritySearchable socialSecuritySearchable, Pageable pageable);
}

package com.bjsts.manager.service.user.impl;

import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.user.SocialSecurityEntity;
import com.bjsts.manager.query.user.SocialSecuritySearchable;
import com.bjsts.manager.repository.user.SocialSecurityRepository;
import com.bjsts.manager.service.user.SocialSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author jinsheng
 * @since 2016-04-27 15:57
 */
@Service
@Transactional
public class SocialSecurityServiceImpl extends AbstractService<SocialSecurityEntity, Long> implements SocialSecurityService {

    @Autowired
    private SocialSecurityRepository socialSecurityRepository;

    @Override
    public ApiResponse<SocialSecurityEntity> findAll(SocialSecuritySearchable socialSecuritySearchable, Pageable pageable) {
        ApiRequest request = ApiRequest.newInstance();

       /* if (StringUtils.isNotEmpty(userSearchable.getId())) {
            String[] ids = StringUtils.split(userSearchable.getId(), ",");
            if (ids != null && ids.length > 0) {
                List<Long> idList = Lists.newArrayList();
                Lists.newArrayList(ids).forEach(id -> idList.add(Long.valueOf(id)));
                request.filterIn(QUserInfo.id, idList);
            }
        }

        if (StringUtils.isNotEmpty(userSearchable.getContent())) {
            request.filterOr(
                    new ApiRequestFilter(OperatorType.LIKE, QUserInfo.username, userSearchable.getContent()),
                    new ApiRequestFilter(OperatorType.LIKE, QUserInfo.name, userSearchable.getContent()),
                    new ApiRequestFilter(OperatorType.LIKE, QUserInfo.email, userSearchable.getContent()),
                    new ApiRequestFilter(OperatorType.LIKE, QUserInfo.mobile, userSearchable.getContent())
            );
        }

        if (userSearchable.getBeginCreatedTime() != null) {
            request.filterGreaterEqual(QUserInfo.createdTime, userSearchable.getBeginCreatedTime());
        }

        if (userSearchable.getEndCreatedTime() != null) {
            request.filterLessEqual(QUserInfo.createdTime, userSearchable.getEndCreatedTime());
        }

        if (userSearchable.getValid() != null && !Objects.equals(EnableDisableStatus.ALL, userSearchable.getValid())) {
            request.filterEqual(QUserInfo.valid, userSearchable.getValid());
        }*/

        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        socialSecuritySearchable.convertPageable(requestPage, pageable);

        Page<SocialSecurityEntity> socialSecurityEntityPage = socialSecurityRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(socialSecurityEntityPage);
    }
}

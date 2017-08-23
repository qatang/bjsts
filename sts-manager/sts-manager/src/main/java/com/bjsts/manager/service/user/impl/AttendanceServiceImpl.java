package com.bjsts.manager.service.user.impl;

import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.user.AttendanceEntity;
import com.bjsts.manager.entity.user.DepartmentEntity;
import com.bjsts.manager.query.user.AttendanceSearchable;
import com.bjsts.manager.repository.user.AttendanceRepository;
import com.bjsts.manager.service.user.AttendanceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author jinsheng
 * @since 2016-04-27 15:57
 */
@Service
@Transactional
public class AttendanceServiceImpl extends AbstractService<AttendanceEntity, Long> implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public List<AttendanceEntity> findByStaffId(Long staffId) {
        return attendanceRepository.findByStaffId(staffId);
    }

    @Override
    public ApiResponse<AttendanceEntity> findAll(AttendanceSearchable attendanceSearchable, Pageable pageable) {
        ApiRequest request = ApiRequest.newInstance();

        if (StringUtils.isNotEmpty(attendanceSearchable.getRealName())) {
            request.filterLike("realName", attendanceSearchable.getRealName());
        }

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
        attendanceSearchable.convertPageable(requestPage, pageable);

        Page<AttendanceEntity> attendanceEntityPage = attendanceRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(attendanceEntityPage);
    }

    @Override
    public ApiResponse<AttendanceEntity> findAll(ApiRequest request, ApiRequestPage requestPage) {
        Page<AttendanceEntity> page = attendanceRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(page);
    }
}

package com.bjsts.manager.service.user;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.user.AttendanceEntity;
import com.bjsts.manager.query.user.AttendanceSearchable;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author jinsheng
 * @since 2016-04-27 15:54
 */
public interface AttendanceService extends IService<AttendanceEntity, Long> {

    List<AttendanceEntity> findByStaffId(Long staffId);

    ApiResponse<AttendanceEntity> findAll(AttendanceSearchable attendanceSearchable, Pageable pageable);
}

package com.bjsts.manager.enums.user;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangzhiliang
 */
public enum AttendanceType {
    ALL(-1, "全部"),
    NORMAL(1, "正常"),
    LEAVE(2, "请假"),
    ;

    private static Logger logger = LoggerFactory.getLogger(AttendanceType.class);

    private static final Object _LOCK = new Object();

    private static Map<Integer, AttendanceType> _MAP;
    private static List<AttendanceType> _LIST;
    private static List<AttendanceType> _ALL_LIST;

    static {
        synchronized (_LOCK) {
            Map<Integer, AttendanceType> map = new HashMap<>();
            List<AttendanceType> list = new ArrayList<>();
            List<AttendanceType> listAll = new ArrayList<>();
            for (AttendanceType attendanceType : AttendanceType.values()) {
                map.put(attendanceType.getValue(), attendanceType);
                listAll.add(attendanceType);
                if (!attendanceType.equals(ALL)) {
                    list.add(attendanceType);
                }
            }

            _MAP = ImmutableMap.copyOf(map);
            _LIST = ImmutableList.copyOf(list);
            _ALL_LIST = ImmutableList.copyOf(listAll);
        }
    }

    private int value;
    private String name;

    AttendanceType(int value, String name){
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue(){
        return value;
    }

    public static AttendanceType get(int value){
        try{
            return _MAP.get(value);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<AttendanceType> list() {
        return _LIST;
    }

    public static List<AttendanceType> listAll() {
        return _ALL_LIST;
    }
}

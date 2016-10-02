package com.bjsts.core.enums;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jinsheng on 15/7/16.
 */
public enum OperationSystemType {

    ALL(-1, "全部"),

    DEFAULT(0, "默认"),

    ANDROID(1, "安卓操作系统"),
    IOS(2, "IOS操作系统");

    private static Logger logger = LoggerFactory.getLogger(OperationSystemType.class);

    private static final Object _LOCK = new Object();

    private static Map<Integer, OperationSystemType> _MAP;
    private static List<OperationSystemType> _LIST;
    private static List<OperationSystemType> _ALL_LIST;

    static {
        synchronized (_LOCK) {
            Map<Integer, OperationSystemType> map = new HashMap<>();
            List<OperationSystemType> list = new ArrayList<>();
            List<OperationSystemType> listAll = new ArrayList<>();
            for (OperationSystemType operationSystemType : OperationSystemType.values()) {
                map.put(operationSystemType.getValue(), operationSystemType);
                listAll.add(operationSystemType);
                if (!operationSystemType.equals(ALL)) {
                    list.add(operationSystemType);
                }
            }

            _MAP = ImmutableMap.copyOf(map);
            _LIST = ImmutableList.copyOf(list);
            _ALL_LIST = ImmutableList.copyOf(listAll);
        }
    }

    private int value;
    private String name;

    OperationSystemType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue(){
        return value;
    }

    public static OperationSystemType get(int value) {
        try {
            return _MAP.get(value);
        } catch(Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<OperationSystemType> list() {
        return _LIST;
    }

    public static List<OperationSystemType> listAll() {
        return _ALL_LIST;
    }
}

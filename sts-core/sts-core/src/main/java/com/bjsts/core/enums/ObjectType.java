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
 * 对象类型
 *
 * @author sunshow
 */
public enum ObjectType {
    ALL(-1, "全部"),

    DEFAULT(0, "默认"),

    // 以下为实际业务对象
    ;

    private static Logger logger = LoggerFactory.getLogger(ObjectType.class);

    private static final Object _LOCK = new Object();

    private static Map<Integer, ObjectType> _MAP;
    private static List<ObjectType> _LIST;
    private static List<ObjectType> _ALL_LIST;

    static {
        synchronized (_LOCK) {
            Map<Integer, ObjectType> map = new HashMap<>();
            List<ObjectType> list = new ArrayList<>();
            List<ObjectType> listAll = new ArrayList<>();
            for (ObjectType type : ObjectType.values()) {
                map.put(type.getValue(), type);
                listAll.add(type);
                if (!type.equals(ALL)) {
                    list.add(type);
                }
            }

            _MAP = ImmutableMap.copyOf(map);
            _LIST = ImmutableList.copyOf(list);
            _ALL_LIST = ImmutableList.copyOf(listAll);
        }
    }

    private int value;
    private String name;

    ObjectType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public static ObjectType get(int value) {
        try {
            return _MAP.get(value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<ObjectType> list() {
        return _LIST;
    }

    public static List<ObjectType> listAll() {
        return _ALL_LIST;
    }
}

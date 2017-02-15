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
 * 离职状态
 * @author wangzhiliang
 */
public enum OnJobStatus {
    ALL(-1, "全部"),
    //    DEFAULT(0, "默认"),
    YES(1, "在职"),
    NO(2, "离职");

    private static Logger logger = LoggerFactory.getLogger(OnJobStatus.class);

    private static final Object _LOCK = new Object();

    private static Map<Integer, OnJobStatus> _MAP;
    private static List<OnJobStatus> _LIST;
    private static List<OnJobStatus> _ALL_LIST;

    static {
        synchronized (_LOCK) {
            Map<Integer, OnJobStatus> map = new HashMap<>();
            List<OnJobStatus> list = new ArrayList<>();
            List<OnJobStatus> listAll = new ArrayList<>();
            for (OnJobStatus onJobStatus : OnJobStatus.values()) {
                map.put(onJobStatus.getValue(), onJobStatus);
                listAll.add(onJobStatus);
                if (!onJobStatus.equals(ALL)) {
                    list.add(onJobStatus);
                }
            }

            _MAP = ImmutableMap.copyOf(map);
            _LIST = ImmutableList.copyOf(list);
            _ALL_LIST = ImmutableList.copyOf(listAll);
        }
    }

    private int value;
    private String name;

    OnJobStatus(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public static OnJobStatus get(int value) {
        try {
            return _MAP.get(value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<OnJobStatus> list() {
        return _LIST;
    }

    public static List<OnJobStatus> listAll() {
        return _ALL_LIST;
    }
}

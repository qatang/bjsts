package com.bjsts.core.enums;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.bjsts.core.exception.StatusFlowException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 等待处理/处理成功/处理失败状态
 *
 * @author sunshow
 */
public enum PendingStatus {
    ALL(-1, "全部"),
    PENDING(0, "待处理"),
    SUCCESSFUL(1, "处理成功"),
    FAILED(2, "处理失败");

    private static Logger logger = LoggerFactory.getLogger(PendingStatus.class);

    private static final Object _LOCK = new Object();

    private static Map<Integer, PendingStatus> _MAP;
    private static List<PendingStatus> _LIST;
    private static List<PendingStatus> _ALL_LIST;

    private static Map<PendingStatus, Set<PendingStatus>> statusFlow;

    static {
        synchronized (_LOCK) {
            Map<Integer, PendingStatus> map = new HashMap<>();
            List<PendingStatus> list = new ArrayList<>();
            List<PendingStatus> listAll = new ArrayList<>();
            for (PendingStatus status : PendingStatus.values()) {
                map.put(status.getValue(), status);
                listAll.add(status);
                if (!status.equals(ALL)) {
                    list.add(status);
                }
            }

            _MAP = ImmutableMap.copyOf(map);
            _LIST = ImmutableList.copyOf(list);
            _ALL_LIST = ImmutableList.copyOf(listAll);
        }

        statusFlow = ImmutableMap.of(
                PENDING, ImmutableSet.of(SUCCESSFUL, FAILED)
        );
    }

    private int value;
    private String name;

    PendingStatus(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public static PendingStatus get(int value) {
        try {
            return _MAP.get(value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<PendingStatus> list() {
        return _LIST;
    }

    public static List<PendingStatus> listAll() {
        return _ALL_LIST;
    }

    public static void checkStatusFlow(PendingStatus from, PendingStatus to) throws StatusFlowException {
        try {
            if (!statusFlow.get(from).contains(to)) {
                throw new StatusFlowException(String.format("不允许的状态变更, 原始状态: %s, 目标状态: %s", from, to));
            }
        } catch (StatusFlowException e) {
            logger.error("不允许的状态变更, 原始状态: {}, 目标状态: {}", from, to);
            throw e;
        } catch (Exception e) {
            logger.error("不允许的状态变更, 原始状态: {}, 目标状态: {}", from, to);
            throw new StatusFlowException(e);
        }
    }
}

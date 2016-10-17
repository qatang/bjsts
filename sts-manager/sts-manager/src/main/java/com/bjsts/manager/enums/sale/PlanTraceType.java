package com.bjsts.manager.enums.sale;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 方案进度类型
 * @author jinsheng
 * @since 2016-04-27 15:04
 */
public enum PlanTraceType {
    ALL(-1, "全部"),
    NORMAL(1, "普通进度记录"),
    ASK_PRICE(2, "报价单");

    private static Logger logger = LoggerFactory.getLogger(PlanTraceType.class);

    private static final Object _LOCK = new Object();

    private static Map<Integer, PlanTraceType> _MAP;
    private static List<PlanTraceType> _LIST;
    private static List<PlanTraceType> _ALL_LIST;

    static {
        synchronized (_LOCK) {
            Map<Integer, PlanTraceType> map = new HashMap<>();
            List<PlanTraceType> list = new ArrayList<>();
            List<PlanTraceType> listAll = new ArrayList<>();
            for (PlanTraceType planType : PlanTraceType.values()) {
                map.put(planType.getValue(), planType);
                listAll.add(planType);
                if (!planType.equals(ALL)) {
                    list.add(planType);
                }
            }

            _MAP = ImmutableMap.copyOf(map);
            _LIST = ImmutableList.copyOf(list);
            _ALL_LIST = ImmutableList.copyOf(listAll);
        }
    }

    private int value;
    private String name;

    PlanTraceType(int value, String name){
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue(){
        return value;
    }

    public static PlanTraceType get(int value){
        try{
            return _MAP.get(value);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<PlanTraceType> list() {
        return _LIST;
    }

    public static List<PlanTraceType> listAll() {
        return _ALL_LIST;
    }
}

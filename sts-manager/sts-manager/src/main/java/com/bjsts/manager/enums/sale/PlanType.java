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
 * 方案类型
 * @author jinsheng
 * @since 2016-04-27 15:04
 */
public enum PlanType {
    ALL(-1, "全部"),
    ASK_PRICE(1, "投标询价"),
    DIRECT_BUG(2, "直接采购");

    private static Logger logger = LoggerFactory.getLogger(PlanType.class);

    private static final Object _LOCK = new Object();

    private static Map<Integer, PlanType> _MAP;
    private static List<PlanType> _LIST;
    private static List<PlanType> _ALL_LIST;

    static {
        synchronized (_LOCK) {
            Map<Integer, PlanType> map = new HashMap<>();
            List<PlanType> list = new ArrayList<>();
            List<PlanType> listAll = new ArrayList<>();
            for (PlanType planType : PlanType.values()) {
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

    PlanType(int value, String name){
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue(){
        return value;
    }

    public static PlanType get(int value){
        try{
            return _MAP.get(value);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<PlanType> list() {
        return _LIST;
    }

    public static List<PlanType> listAll() {
        return _ALL_LIST;
    }
}

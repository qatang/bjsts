package com.bjsts.manager.enums.sale;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目方案状态
 * @author jinsheng
 * @since 2016-04-27 15:04
 */
public enum PlanStatus {
    ALL(-1, "全部"),
    ASK_PRICE(1, "询价"),
    ABANDON(2, "弃单"),
    CONTINUE(3, "持续跟进"),
    QUOTE_FOR_SALE(4, "提交报价单至销售"),
    QUOTE_FOR_CUSTOMER(5, "提交报价单至甲方"),
    UN_COMPLETE(6, "无法完成"),
    COMPLETE(7, "签订合同"),
    TWO_ASK_PRICE(8, "二次报价"),
    OTHERS(9, "其他"),
    ;

    private static Logger logger = LoggerFactory.getLogger(PlanStatus.class);

    private static final Object _LOCK = new Object();

    private static Map<Integer, PlanStatus> _MAP;
    private static List<PlanStatus> _LIST;
    private static List<PlanStatus> _ALL_LIST;

    static {
        synchronized (_LOCK) {
            Map<Integer, PlanStatus> map = new HashMap<>();
            List<PlanStatus> list = new ArrayList<>();
            List<PlanStatus> listAll = new ArrayList<>();
            for (PlanStatus planStatus : PlanStatus.values()) {
                map.put(planStatus.getValue(), planStatus);
                listAll.add(planStatus);
                if (!planStatus.equals(ALL)) {
                    list.add(planStatus);
                }
            }

            _MAP = ImmutableMap.copyOf(map);
            _LIST = ImmutableList.copyOf(list);
            _ALL_LIST = ImmutableList.copyOf(listAll);
        }
    }

    private int value;
    private String name;

    PlanStatus(int value, String name){
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue(){
        return value;
    }

    public static PlanStatus get(int value){
        try{
            return _MAP.get(value);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<PlanStatus> list() {
        return _LIST;
    }

    public static List<PlanStatus> listAll() {
        return _ALL_LIST;
    }

    //可追踪状态
    public static List<PlanStatus> listTraceStatus() {
        return Lists.newArrayList(
                ASK_PRICE,
                CONTINUE,
                QUOTE_FOR_SALE,
                QUOTE_FOR_CUSTOMER,
                TWO_ASK_PRICE,
                OTHERS);
    }

    //归档状态
    public static List<PlanStatus> listHistoryStatus() {
        return Lists.newArrayList(
                ABANDON,
                UN_COMPLETE);
    }
}

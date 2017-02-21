package com.bjsts.manager.enums.produce;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目执行情况
 * @author wangzhiliang
 */
public enum PlanExecuteStatus {
    ALL(-1, "全部"),
    JSJD(1, "技术交底阶段"),
    WLCG(1, "物料采购阶段"),
    CPZZ(1, "产品组装阶段"),
    TS(1, "调试阶段"),
    BZ(2, "包装阶段");

    private static Logger logger = LoggerFactory.getLogger(PlanExecuteStatus.class);

    private static final Object _LOCK = new Object();

    private static Map<Integer, PlanExecuteStatus> _MAP;
    private static List<PlanExecuteStatus> _LIST;
    private static List<PlanExecuteStatus> _ALL_LIST;

    static {
        synchronized (_LOCK) {
            Map<Integer, PlanExecuteStatus> map = new HashMap<>();
            List<PlanExecuteStatus> list = new ArrayList<>();
            List<PlanExecuteStatus> listAll = new ArrayList<>();
            for (PlanExecuteStatus planExecuteStatus : PlanExecuteStatus.values()) {
                map.put(planExecuteStatus.getValue(), planExecuteStatus);
                listAll.add(planExecuteStatus);
                if (!planExecuteStatus.equals(ALL)) {
                    list.add(planExecuteStatus);
                }
            }

            _MAP = ImmutableMap.copyOf(map);
            _LIST = ImmutableList.copyOf(list);
            _ALL_LIST = ImmutableList.copyOf(listAll);
        }
    }

    private int value;
    private String name;

    PlanExecuteStatus(int value, String name){
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue(){
        return value;
    }

    public static PlanExecuteStatus get(int value){
        try{
            return _MAP.get(value);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<PlanExecuteStatus> list() {
        return _LIST;
    }

    public static List<PlanExecuteStatus> listAll() {
        return _ALL_LIST;
    }
}

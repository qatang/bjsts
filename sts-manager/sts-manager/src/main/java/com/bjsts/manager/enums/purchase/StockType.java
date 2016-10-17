package com.bjsts.manager.enums.purchase;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 库存类型
 * @author jinsheng
 * @since 2016-04-27 15:04
 */
public enum StockType {
    ALL(-1, "全部"),
    IN(1, "入库"),
    OUT(2, "出库");

    private static Logger logger = LoggerFactory.getLogger(StockType.class);

    private static final Object _LOCK = new Object();

    private static Map<Integer, StockType> _MAP;
    private static List<StockType> _LIST;
    private static List<StockType> _ALL_LIST;

    static {
        synchronized (_LOCK) {
            Map<Integer, StockType> map = new HashMap<>();
            List<StockType> list = new ArrayList<>();
            List<StockType> listAll = new ArrayList<>();
            for (StockType planType : StockType.values()) {
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

    StockType(int value, String name){
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue(){
        return value;
    }

    public static StockType get(int value){
        try{
            return _MAP.get(value);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<StockType> list() {
        return _LIST;
    }

    public static List<StockType> listAll() {
        return _ALL_LIST;
    }
}

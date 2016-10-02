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
 * 货币类型
 * @author sunshow
 */
public enum CurrencyType {
    ALL(-1, "全部"),
    //    DEFAULT(0, "默认"),
    CNY(1, "人民币"),
    USD(2, "美元"),
    GBP(3, "英镑"),
    HKD(4, "港币"),
    SGD(5, "新加坡"),
    JPY(6, "日元"),
    CAD(7, "加拿大"),
    AUD(8, "澳元"),
    EUR(9, "欧元"),
    CHF(10, "瑞士法郎"),
    ;

    private static Logger logger = LoggerFactory.getLogger(CurrencyType.class);

    private static final Object _LOCK = new Object();

    private static Map<Integer, CurrencyType> _MAP;
    private static List<CurrencyType> _LIST;
    private static List<CurrencyType> _ALL_LIST;

    static {
        synchronized (_LOCK) {
            Map<Integer, CurrencyType> map = new HashMap<>();
            List<CurrencyType> list = new ArrayList<>();
            List<CurrencyType> listAll = new ArrayList<>();
            for (CurrencyType type : CurrencyType.values()) {
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

    CurrencyType(int value, String name){
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue(){
        return value;
    }

    public static CurrencyType get(int value){
        try{
            return _MAP.get(value);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<CurrencyType> list() {
        return _LIST;
    }

    public static List<CurrencyType> listAll() {
        return _ALL_LIST;
    }
}

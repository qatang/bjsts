package com.bjsts.manager.enums.customer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户类型
 * @author wangzhiliang
 */
public enum CustomerType {
    ALL(-1, "全部"),
    NORMAL(1, "一般客户"),
    VIP(2, "重点客户"),
    LATENT(3, "潜在客户"),
    INVALID(4, "失效客户"),
    ;

    private static Logger logger = LoggerFactory.getLogger(CustomerType.class);

    private static final Object _LOCK = new Object();

    private static Map<Integer, CustomerType> _MAP;
    private static List<CustomerType> _LIST;
    private static List<CustomerType> _ALL_LIST;

    static {
        synchronized (_LOCK) {
            Map<Integer, CustomerType> map = new HashMap<>();
            List<CustomerType> list = new ArrayList<>();
            List<CustomerType> listAll = new ArrayList<>();
            for (CustomerType planType : CustomerType.values()) {
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

    CustomerType(int value, String name){
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue(){
        return value;
    }

    public static CustomerType get(int value){
        try{
            return _MAP.get(value);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<CustomerType> list() {
        return _LIST;
    }

    public static List<CustomerType> listAll() {
        return _ALL_LIST;
    }
}

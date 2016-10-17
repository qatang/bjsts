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
 * 合同状态
 * @author jinsheng
 * @since 2016-04-27 15:04
 */
public enum ContractStatus {
    ALL(-1, "全部"),
    EXECUTE_NOT(1, "未执行"),
    EXECUTING(2, "执行中"),
    EXECUTED(3, "已完成"),
    OBSOLETED(4, "已作废"),
    ;

    private static Logger logger = LoggerFactory.getLogger(ContractStatus.class);

    private static final Object _LOCK = new Object();

    private static Map<Integer, ContractStatus> _MAP;
    private static List<ContractStatus> _LIST;
    private static List<ContractStatus> _ALL_LIST;

    static {
        synchronized (_LOCK) {
            Map<Integer, ContractStatus> map = new HashMap<>();
            List<ContractStatus> list = new ArrayList<>();
            List<ContractStatus> listAll = new ArrayList<>();
            for (ContractStatus contractStatus : ContractStatus.values()) {
                map.put(contractStatus.getValue(), contractStatus);
                listAll.add(contractStatus);
                if (!contractStatus.equals(ALL)) {
                    list.add(contractStatus);
                }
            }

            _MAP = ImmutableMap.copyOf(map);
            _LIST = ImmutableList.copyOf(list);
            _ALL_LIST = ImmutableList.copyOf(listAll);
        }
    }

    private int value;
    private String name;

    ContractStatus(int value, String name){
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue(){
        return value;
    }

    public static ContractStatus get(int value){
        try{
            return _MAP.get(value);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<ContractStatus> list() {
        return _LIST;
    }

    public static List<ContractStatus> listAll() {
        return _ALL_LIST;
    }
}

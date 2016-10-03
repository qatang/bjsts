package com.bjsts.manager.enums;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jinsheng
 * @since 2016-04-27 15:04
 */
public enum PolityType {
    ALL(-1, "全部"),
    NONE(1, "无"),
    TUAN(2, "团员"),
    DANG(3, "党员"),
    OTHER(4, "其他"),
    ;

    private static Logger logger = LoggerFactory.getLogger(PolityType.class);

    private static final Object _LOCK = new Object();

    private static Map<Integer, PolityType> _MAP;
    private static List<PolityType> _LIST;
    private static List<PolityType> _ALL_LIST;

    static {
        synchronized (_LOCK) {
            Map<Integer, PolityType> map = new HashMap<>();
            List<PolityType> list = new ArrayList<>();
            List<PolityType> listAll = new ArrayList<>();
            for (PolityType polityType : PolityType.values()) {
                map.put(polityType.getValue(), polityType);
                listAll.add(polityType);
                if (!polityType.equals(ALL)) {
                    list.add(polityType);
                }
            }

            _MAP = ImmutableMap.copyOf(map);
            _LIST = ImmutableList.copyOf(list);
            _ALL_LIST = ImmutableList.copyOf(listAll);
        }
    }

    private int value;
    private String name;

    PolityType(int value, String name){
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue(){
        return value;
    }

    public static PolityType get(int value){
        try{
            return _MAP.get(value);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<PolityType> list() {
        return _LIST;
    }

    public static List<PolityType> listAll() {
        return _ALL_LIST;
    }
}

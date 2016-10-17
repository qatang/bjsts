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
 * 信息来源
 * @author jinsheng
 * @since 2016-04-27 15:04
 */
public enum SourceType {
    ALL(-1, "全部"),
    ONLINE(1, "线上"),
    OFFLINE(2, "线下");

    private static Logger logger = LoggerFactory.getLogger(SourceType.class);

    private static final Object _LOCK = new Object();

    private static Map<Integer, SourceType> _MAP;
    private static List<SourceType> _LIST;
    private static List<SourceType> _ALL_LIST;

    static {
        synchronized (_LOCK) {
            Map<Integer, SourceType> map = new HashMap<>();
            List<SourceType> list = new ArrayList<>();
            List<SourceType> listAll = new ArrayList<>();
            for (SourceType sourceType : SourceType.values()) {
                map.put(sourceType.getValue(), sourceType);
                listAll.add(sourceType);
                if (!sourceType.equals(ALL)) {
                    list.add(sourceType);
                }
            }

            _MAP = ImmutableMap.copyOf(map);
            _LIST = ImmutableList.copyOf(list);
            _ALL_LIST = ImmutableList.copyOf(listAll);
        }
    }

    private int value;
    private String name;

    SourceType(int value, String name){
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue(){
        return value;
    }

    public static SourceType get(int value){
        try{
            return _MAP.get(value);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<SourceType> list() {
        return _LIST;
    }

    public static List<SourceType> listAll() {
        return _ALL_LIST;
    }
}

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
public enum EducationType {
    ALL(-1, "全部"),
    JUNIOR(1, "小学"),
    MIDDLE(2, "初中"),
    HIGH(3, "高中"),
    POLYTECHNIC(4, "中专"),
    COLLEGE(5, "大专"),
    REGULAR(6, "本科"),
    GRADUATE(7, "研究生"),
    ;

    private static Logger logger = LoggerFactory.getLogger(EducationType.class);

    private static final Object _LOCK = new Object();

    private static Map<Integer, EducationType> _MAP;
    private static List<EducationType> _LIST;
    private static List<EducationType> _ALL_LIST;

    static {
        synchronized (_LOCK) {
            Map<Integer, EducationType> map = new HashMap<>();
            List<EducationType> list = new ArrayList<>();
            List<EducationType> listAll = new ArrayList<>();
            for (EducationType educationType : EducationType.values()) {
                map.put(educationType.getValue(), educationType);
                listAll.add(educationType);
                if (!educationType.equals(ALL)) {
                    list.add(educationType);
                }
            }

            _MAP = ImmutableMap.copyOf(map);
            _LIST = ImmutableList.copyOf(list);
            _ALL_LIST = ImmutableList.copyOf(listAll);
        }
    }

    private int value;
    private String name;

    EducationType(int value, String name){
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue(){
        return value;
    }

    public static EducationType get(int value){
        try{
            return _MAP.get(value);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<EducationType> list() {
        return _LIST;
    }

    public static List<EducationType> listAll() {
        return _ALL_LIST;
    }
}

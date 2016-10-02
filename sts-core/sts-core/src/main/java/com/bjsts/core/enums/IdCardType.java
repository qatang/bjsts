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
 * 身份类型
 * @author sunshow
 */
public enum IdCardType {
    ALL(-1, "全部"),
    //    DEFAULT(0, "默认"),
    IC(1, "身份证"),
    TIC(2, "临时身份证"),
    PP(3, "护照"),
    SC(4, "士兵证"),
    AOC(5, "军官证"),
    ACC(6, "军人文职干部证"),
    POC(7, "警官证"),
    APC(8, "武警证"),
    HMP(9, "港澳居民来往内地通行证"),
    RB(10, "户口簿"),
    TWP(11, "台湾居民来往大陆通行证/台胞证"),
    TWR(12, "台湾回乡证"),
    FPP(13, "外国护照"),
    FR(14, "外国人永久居留证"),
    ;

    private static Logger logger = LoggerFactory.getLogger(IdCardType.class);

    private static final Object _LOCK = new Object();

    private static Map<Integer, IdCardType> _MAP;
    private static List<IdCardType> _LIST;
    private static List<IdCardType> _ALL_LIST;

    static {
        synchronized (_LOCK) {
            Map<Integer, IdCardType> map = new HashMap<>();
            List<IdCardType> list = new ArrayList<>();
            List<IdCardType> listAll = new ArrayList<>();
            for (IdCardType type : IdCardType.values()) {
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

    IdCardType(int value, String name){
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue(){
        return value;
    }

    public static IdCardType get(int value){
        try{
            return _MAP.get(value);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<IdCardType> list() {
        return _LIST;
    }

    public static List<IdCardType> listAll() {
        return _ALL_LIST;
    }
}

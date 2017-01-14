package com.bjsts.manager.enums.user;

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
public enum MaleType {
    ALL(-1, "全部"),
    MALE(1, "男"),
    FEMALE(2, "女");

    private static Logger logger = LoggerFactory.getLogger(MaleType.class);

    private static final Object _LOCK = new Object();

    private static Map<Integer, MaleType> _MAP;
    private static List<MaleType> _LIST;
    private static List<MaleType> _ALL_LIST;

    static {
        synchronized (_LOCK) {
            Map<Integer, MaleType> map = new HashMap<>();
            List<MaleType> list = new ArrayList<>();
            List<MaleType> listAll = new ArrayList<>();
            for (MaleType maleType : MaleType.values()) {
                map.put(maleType.getValue(), maleType);
                listAll.add(maleType);
                if (!maleType.equals(ALL)) {
                    list.add(maleType);
                }
            }

            _MAP = ImmutableMap.copyOf(map);
            _LIST = ImmutableList.copyOf(list);
            _ALL_LIST = ImmutableList.copyOf(listAll);
        }
    }

    private int value;
    private String name;

    MaleType(int value, String name){
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue(){
        return value;
    }

    public static MaleType get(int value){
        try{
            return _MAP.get(value);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<MaleType> list() {
        return _LIST;
    }

    public static List<MaleType> listAll() {
        return _ALL_LIST;
    }
}

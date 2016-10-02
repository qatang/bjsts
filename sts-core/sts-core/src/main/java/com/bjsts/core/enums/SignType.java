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
 * 签名类型
 * @author qatang
 * @since 2015-05-12 14:30
 */
public enum SignType {
    ALL(-1, "全部"),
    //    DEFAULT(0, "默认"),
    MD5(1, "MD5"),
    SHA(2, "SHA"),
    DSA(3, "DSA"),
    RSA(4, "RSA");

    private static Logger logger = LoggerFactory.getLogger(SignType.class);

    private static final Object _LOCK = new Object();

    private static Map<Integer, SignType> _MAP;
    private static List<SignType> _LIST;
    private static List<SignType> _ALL_LIST;

    static {
        synchronized (_LOCK) {
            Map<Integer, SignType> map = new HashMap<>();
            List<SignType> list = new ArrayList<>();
            List<SignType> listAll = new ArrayList<>();
            for (SignType signType : SignType.values()) {
                map.put(signType.getValue(), signType);
                listAll.add(signType);
                if (!signType.equals(ALL)) {
                    list.add(signType);
                }
            }

            _MAP = ImmutableMap.copyOf(map);
            _LIST = ImmutableList.copyOf(list);
            _ALL_LIST = ImmutableList.copyOf(listAll);
        }
    }

    private int value;
    private String name;

    SignType(int value, String name){
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue(){
        return value;
    }

    public static SignType get(int value){
        try{
            return _MAP.get(value);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<SignType> list() {
        return _LIST;
    }

    public static List<SignType> listAll() {
        return _ALL_LIST;
    }
}

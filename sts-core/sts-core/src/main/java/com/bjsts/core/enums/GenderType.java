package com.bjsts.core.enums;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Ints;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 性别
 * Created by janus on 15/6/11.
 */
public enum GenderType {
    All(-1, "全部"),
    Unknown(0, "未知"),
    Male(1, "男"),
    Female(2, "女");

    private static Logger logger = LoggerFactory.getLogger(GenderType.class);

    private static final Object _LOCK = new Object();

    private static Map<Integer, GenderType> _MAP;
    private static List<GenderType> _LIST;
    private static List<GenderType> _ALL_LIST;

    static {
        synchronized (_LOCK) {
            Map<Integer, GenderType> map = new HashMap<>();
            List<GenderType> list = new ArrayList<>();
            List<GenderType> listAll = new ArrayList<>();
            for (GenderType genderType : GenderType.values()) {
                map.put(genderType.getValue(), genderType);
                listAll.add(genderType);
                if (!genderType.equals(All)) {
                    list.add(genderType);
                }
            }

            _MAP = ImmutableMap.copyOf(map);
            _LIST = ImmutableList.copyOf(list);
            _ALL_LIST = ImmutableList.copyOf(listAll);
        }
    }

    private int value;
    private String name;

    GenderType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public static GenderType get(int value) {
        try {
            return _MAP.get(value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<GenderType> list() {
        return _LIST;
    }

    public static List<GenderType> listAll() {
        return _ALL_LIST;
    }

    /**
     * 由身份证号得到性别
     * @param idCard
     * @return
     */
    public static GenderType fromIdCard(String idCard) {
        if (StringUtils.isNotBlank(idCard)) {
            int len = idCard.length();
            String genderChar = null;
            if (len == 18) {
                // 18位身份证取最后第二位
                genderChar = idCard.substring(16, 17);
            }
            else if (len == 15) {
                // 15位身份证取最后一位
                genderChar = idCard.substring(14);
            }

            if (StringUtils.isNotBlank(genderChar)) {
                Integer num = Ints.tryParse(genderChar);
                if (num != null) {
                    if (num % 2 == 0) {
                        return GenderType.Female;
                    }
                    return GenderType.Male;
                }
            }
        }

        return GenderType.Unknown;
    }
}

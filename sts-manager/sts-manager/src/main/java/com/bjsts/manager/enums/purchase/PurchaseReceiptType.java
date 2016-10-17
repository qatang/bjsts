package com.bjsts.manager.enums.purchase;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 采购发票类型
 * @author jinsheng
 * @since 2016-04-27 15:04
 */
public enum PurchaseReceiptType {
    ALL(-1, "全部"),
    NONE(1, "无发票"),
    UN_PRINTED_ZHUAN_YONG(2, "增值税专用发票未开"),
    PRINTED_ZHUAN_YONG(3, "增值税专用发票已开"),
    UN_PRINTED_PU_TONG(4, "增值税普通发票未开"),
    PRINTED_PU_TONG(5, "增值税普通发票已开"),
    ;

    private static Logger logger = LoggerFactory.getLogger(PurchaseReceiptType.class);

    private static final Object _LOCK = new Object();

    private static Map<Integer, PurchaseReceiptType> _MAP;
    private static List<PurchaseReceiptType> _LIST;
    private static List<PurchaseReceiptType> _ALL_LIST;

    static {
        synchronized (_LOCK) {
            Map<Integer, PurchaseReceiptType> map = new HashMap<>();
            List<PurchaseReceiptType> list = new ArrayList<>();
            List<PurchaseReceiptType> listAll = new ArrayList<>();
            for (PurchaseReceiptType planType : PurchaseReceiptType.values()) {
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

    PurchaseReceiptType(int value, String name){
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue(){
        return value;
    }

    public static PurchaseReceiptType get(int value){
        try{
            return _MAP.get(value);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<PurchaseReceiptType> list() {
        return _LIST;
    }

    public static List<PurchaseReceiptType> listAll() {
        return _ALL_LIST;
    }
}

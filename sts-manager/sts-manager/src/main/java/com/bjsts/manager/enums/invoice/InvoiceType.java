package com.bjsts.manager.enums.invoice;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发票分类
 * @author wangzhiliang
 */
public enum InvoiceType {
    ALL(-1, "全部"),
    PURCHASE(1, "采购发票"),
    SALE(2, "销售发票"),
    ;

    private static Logger logger = LoggerFactory.getLogger(InvoiceType.class);

    private static final Object _LOCK = new Object();

    private static Map<Integer, InvoiceType> _MAP;
    private static List<InvoiceType> _LIST;
    private static List<InvoiceType> _ALL_LIST;

    static {
        synchronized (_LOCK) {
            Map<Integer, InvoiceType> map = new HashMap<>();
            List<InvoiceType> list = new ArrayList<>();
            List<InvoiceType> listAll = new ArrayList<>();
            for (InvoiceType invoiceType : InvoiceType.values()) {
                map.put(invoiceType.getValue(), invoiceType);
                listAll.add(invoiceType);
                if (!invoiceType.equals(ALL)) {
                    list.add(invoiceType);
                }
            }

            _MAP = ImmutableMap.copyOf(map);
            _LIST = ImmutableList.copyOf(list);
            _ALL_LIST = ImmutableList.copyOf(listAll);
        }
    }

    private int value;
    private String name;

    InvoiceType(int value, String name){
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue(){
        return value;
    }

    public static InvoiceType get(int value){
        try{
            return _MAP.get(value);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<InvoiceType> list() {
        return _LIST;
    }

    public static List<InvoiceType> listAll() {
        return _ALL_LIST;
    }
}

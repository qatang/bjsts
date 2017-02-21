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
 * 发票类型
 * @author wangzhiliang
 */
public enum InvoiceCategory {
    ALL(-1, "全部"),
    BT(1, "增值税普通发票"),
    ZY(2, "增值税专用发票"),
    ;

    private static Logger logger = LoggerFactory.getLogger(InvoiceCategory.class);

    private static final Object _LOCK = new Object();

    private static Map<Integer, InvoiceCategory> _MAP;
    private static List<InvoiceCategory> _LIST;
    private static List<InvoiceCategory> _ALL_LIST;

    static {
        synchronized (_LOCK) {
            Map<Integer, InvoiceCategory> map = new HashMap<>();
            List<InvoiceCategory> list = new ArrayList<>();
            List<InvoiceCategory> listAll = new ArrayList<>();
            for (InvoiceCategory invoiceCategory : InvoiceCategory.values()) {
                map.put(invoiceCategory.getValue(), invoiceCategory);
                listAll.add(invoiceCategory);
                if (!invoiceCategory.equals(ALL)) {
                    list.add(invoiceCategory);
                }
            }

            _MAP = ImmutableMap.copyOf(map);
            _LIST = ImmutableList.copyOf(list);
            _ALL_LIST = ImmutableList.copyOf(listAll);
        }
    }

    private int value;
    private String name;

    InvoiceCategory(int value, String name){
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue(){
        return value;
    }

    public static InvoiceCategory get(int value){
        try{
            return _MAP.get(value);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<InvoiceCategory> list() {
        return _LIST;
    }

    public static List<InvoiceCategory> listAll() {
        return _ALL_LIST;
    }
}

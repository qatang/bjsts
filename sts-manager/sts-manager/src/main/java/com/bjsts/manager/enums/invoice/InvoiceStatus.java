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
 * 发票状态
 * @author wangzhiliang
 */
public enum InvoiceStatus {
    ALL(-1, "全部"),
    ZC(1, "正常"),
    ZF(2, "作废"),
    HC(2, "红冲"),
    YDK(2, "已抵扣"),
    WDK(2, "未抵扣"),
    ;

    private static Logger logger = LoggerFactory.getLogger(InvoiceStatus.class);

    private static final Object _LOCK = new Object();

    private static Map<Integer, InvoiceStatus> _MAP;
    private static List<InvoiceStatus> _LIST;
    private static List<InvoiceStatus> _ALL_LIST;

    static {
        synchronized (_LOCK) {
            Map<Integer, InvoiceStatus> map = new HashMap<>();
            List<InvoiceStatus> list = new ArrayList<>();
            List<InvoiceStatus> listAll = new ArrayList<>();
            for (InvoiceStatus invoiceStatus : InvoiceStatus.values()) {
                map.put(invoiceStatus.getValue(), invoiceStatus);
                listAll.add(invoiceStatus);
                if (!invoiceStatus.equals(ALL)) {
                    list.add(invoiceStatus);
                }
            }

            _MAP = ImmutableMap.copyOf(map);
            _LIST = ImmutableList.copyOf(list);
            _ALL_LIST = ImmutableList.copyOf(listAll);
        }
    }

    private int value;
    private String name;

    InvoiceStatus(int value, String name){
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue(){
        return value;
    }

    public static InvoiceStatus get(int value){
        try{
            return _MAP.get(value);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<InvoiceStatus> list() {
        return _LIST;
    }

    public static List<InvoiceStatus> listAll() {
        return _ALL_LIST;
    }
}

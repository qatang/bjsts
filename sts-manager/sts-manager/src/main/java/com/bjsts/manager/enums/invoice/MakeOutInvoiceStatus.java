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
 * 开具发票状态
 * @author wangzhiliang
 */
public enum MakeOutInvoiceStatus {
    ALL(-1, "全部"),
    SPECIAL_INVOICE(1, "已开具增值税专用发票"),
    NO_SPECIAL_INVOICE(2, "未开具增值税专用发票"),
    PLAIN_INVOICE(3, "已开具增值税普通发票"),
    NO_PLAIN_INVOICE(4, "未开具增值税普通发票"),
    NO_INVOICE(5, "无发票"),
    ;

    private static Logger logger = LoggerFactory.getLogger(MakeOutInvoiceStatus.class);

    private static final Object _LOCK = new Object();

    private static Map<Integer, MakeOutInvoiceStatus> _MAP;
    private static List<MakeOutInvoiceStatus> _LIST;
    private static List<MakeOutInvoiceStatus> _ALL_LIST;

    static {
        synchronized (_LOCK) {
            Map<Integer, MakeOutInvoiceStatus> map = new HashMap<>();
            List<MakeOutInvoiceStatus> list = new ArrayList<>();
            List<MakeOutInvoiceStatus> listAll = new ArrayList<>();
            for (MakeOutInvoiceStatus makeOutInvoiceStatus : MakeOutInvoiceStatus.values()) {
                map.put(makeOutInvoiceStatus.getValue(), makeOutInvoiceStatus);
                listAll.add(makeOutInvoiceStatus);
                if (!makeOutInvoiceStatus.equals(ALL)) {
                    list.add(makeOutInvoiceStatus);
                }
            }

            _MAP = ImmutableMap.copyOf(map);
            _LIST = ImmutableList.copyOf(list);
            _ALL_LIST = ImmutableList.copyOf(listAll);
        }
    }

    private int value;
    private String name;

    MakeOutInvoiceStatus(int value, String name){
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue(){
        return value;
    }

    public static MakeOutInvoiceStatus get(int value){
        try{
            return _MAP.get(value);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<MakeOutInvoiceStatus> list() {
        return _LIST;
    }

    public static List<MakeOutInvoiceStatus> listAll() {
        return _ALL_LIST;
    }
}

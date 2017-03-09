package com.bjsts.manager.enums.document;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件分类
 * @author wangzhiliang
 */
public enum DocumentType {
    ALL("all", "全部"),
    DEFAULT("default", "默认"),
    CUSTOMER("customer", "客户"),
    QUOTE("quote", "报价"),
    CONTRACT("contract", "合同"),
    PURCHASE("purchase", "采购合同"),
    INVOICE("invoice", "发票"),
    ;

    private static Logger logger = LoggerFactory.getLogger(DocumentType.class);

    private static final Object _LOCK = new Object();

    private static Map<String, DocumentType> _MAP;
    private static List<DocumentType> _LIST;
    private static List<DocumentType> _ALL_LIST;

    static {
        synchronized (_LOCK) {
            Map<String, DocumentType> map = new HashMap<>();
            List<DocumentType> list = new ArrayList<>();
            List<DocumentType> listAll = new ArrayList<>();
            for (DocumentType documentType : DocumentType.values()) {
                map.put(documentType.getEnglishName(), documentType);
                listAll.add(documentType);
                if (!documentType.equals(ALL)) {
                    list.add(documentType);
                }
            }

            _MAP = ImmutableMap.copyOf(map);
            _LIST = ImmutableList.copyOf(list);
            _ALL_LIST = ImmutableList.copyOf(listAll);
        }
    }

    private String englishName;
    private String name;

    DocumentType(String englishName, String name){
        this.englishName = englishName;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getEnglishName() {
        return englishName;
    }

    public static DocumentType get(String englishName){
        try{
            return _MAP.get(englishName);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<DocumentType> list() {
        return _LIST;
    }

    public static List<DocumentType> listAll() {
        return _ALL_LIST;
    }
}

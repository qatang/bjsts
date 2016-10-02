package com.bjsts.core.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author jinsheng
 * @since 2016-06-06 09:17
 */
public class ExcelUtils {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);
    private static final String METHOD_GET_NAME = "getName";
    private static final String EXCEL_SUFFIX = ".xls";

    public static byte[] generateBytes(List<String> header, List<String> fieldNameList, List objectList) {
        Workbook workbook = generate(header, fieldNameList, objectList);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            workbook.write(os);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return os.toByteArray();
    }

    public static Workbook generate(List<String> header, List<String> fieldNameList, List objectList) {

        Assert.notNull(header);
        Assert.notEmpty(header);

        Assert.notNull(fieldNameList);
        Assert.notEmpty(fieldNameList);

        Assert.notNull(objectList);
        Assert.notEmpty(objectList);

        Assert.isTrue(Objects.equals(header.size(), fieldNameList.size()));

        return generateExcel(header, generateBody(fieldNameList, objectList));
    }

    private static List<List<String>> generateBody(List<String> fieldList, List objectList) {
        List<List<String>> body = Lists.newArrayList();
        if (objectList != null && !objectList.isEmpty()) {
            objectList.forEach(o -> body.add(generateLine(fieldList, o)));
        }
        return body;
    }

    private static List<String> generateLine(List<String> fieldNameList, Object object) {
        List<String> line = Lists.newArrayList();
        Class clazz = object.getClass();
        fieldNameList.forEach(fieldName -> {
            try {
                Method method = clazz.getMethod(String.format("get%s%s", StringUtils.substring(fieldName, 0, 1).toUpperCase(), StringUtils.substring(fieldName, 1)));
                Object value = method.invoke(object);

                if (Objects.equals(value.getClass(), Date.class)) {
                    line.add(CoreDateUtils.formatDate((Date) value, CoreDateUtils.DATETIME));
                } else if (value.getClass().isEnum()) {
                    line.add(String.valueOf(value.getClass().getMethod(METHOD_GET_NAME).invoke(value)));
                } else {
                    line.add(String.valueOf(value));
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                line.add(null);
            }
        });
        return line;
    }

    private static Workbook generateExcel(List<String> header, List<List<String>> body) {
        Workbook workbook = new HSSFWorkbook();
        Sheet worksheet = workbook.createSheet();
        worksheet.setDefaultColumnWidth(body.size() + 1);
        int sheetNum = 0;
        workbook.setSheetName(sheetNum, "sheet" + (sheetNum + 1));

        Row row0 = worksheet.createRow(0);

        int i = 0;
        for (String head : header) {
            row0.createCell(i, Cell.CELL_TYPE_STRING).setCellValue(head);
            i ++;
        }
        int lineNumber = 1;
        for (List<String> line : body) {
            Row row = worksheet.createRow(lineNumber);
            int j = 0;
            for (String value : line) {
                row.createCell(j, Cell.CELL_TYPE_STRING).setCellValue(value);
                j ++;
            }
            lineNumber ++;
        }
        return workbook;
    }

    public static String generateFileName() {
        Date now = new Date();
        String year = CoreDateUtils.formatDate(now, "yyyy");
        String month = CoreDateUtils.formatDate(now, "MM");
        String day = CoreDateUtils.formatDate(now, "dd");
        return String.format("%s%s%s%s%s%s%s%s", year, File.separator, month, File.separator, day, File.separator, System.currentTimeMillis() + new Random().nextInt(999999), EXCEL_SUFFIX);
    }
}
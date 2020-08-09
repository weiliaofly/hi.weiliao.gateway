package com.hi.weiliao.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CommonUtils {

    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    public static String getSixVerifyCode() {
        int max = 999999, min = 1;
        int ran2 = (int) (Math.random() * (max - min) + min);
        return new DecimalFormat("000000").format(ran2);
    }

    public static boolean isBigger(BigDecimal num1, BigDecimal num2) {
        if (num1 == null || num2 == null) {
            return false;
        }
        return num1.compareTo(num2) > 0 ? true : false;
    }

    public static boolean isBiggerAndSame(BigDecimal num1, BigDecimal num2) {
        if (num1 == null || num2 == null) {
            return false;
        }
        return num1.compareTo(num2) >= 0 ? true : false;
    }

    public static <T> T resolveEnumByName(String name, Class<T> enumSource) {
        try {
            Field field = enumSource.getField("name");
            field.setAccessible(true);
            for (T t : enumSource.getEnumConstants()) {
                String sourceName = (String) field.get(t);
                if (sourceName.equalsIgnoreCase(name)) {
                    return t;
                }
            }
        } catch (Exception e) {
            logger.error("解析枚举失败", e);
        }
        return null;
    }
}

package com.hi.weiliao.base.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CommonUtils {

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
}

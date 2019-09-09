package com.hi.weiliao.base.utils;

import java.text.DecimalFormat;

public class CommonUtils {

    public static String getSixVerifyCode() {
        int max = 999999, min = 1;
        int ran2 = (int) (Math.random() * (max - min) + min);
        return new DecimalFormat("000000").format(ran2);
    }
}

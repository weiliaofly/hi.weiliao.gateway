package com.hi.weiliao.base.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
    public static String getCurrentYYYYMMDD() {
        SimpleDateFormat sf = new SimpleDateFormat("YYYY-MM-dd");
        String currentDate = sf.format(new Date());
        return currentDate;
    }
}

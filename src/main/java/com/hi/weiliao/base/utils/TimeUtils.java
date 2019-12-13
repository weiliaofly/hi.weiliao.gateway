package com.hi.weiliao.base.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    public static final String REG_YYYY_MM_DD = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";

    public static String getCurrentYYYYMMDD() {
        SimpleDateFormat sf = new SimpleDateFormat("YYYY-MM-dd");
        String currentDate = sf.format(new Date());
        return currentDate;
    }

    public static String getCurrentYYYYMMDDHHMMSS() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = sf.format(new Date());
        return currentDate;
    }
}

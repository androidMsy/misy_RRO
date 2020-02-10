package com.msy.rrodemo.utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2019/12/27/027.
 */

public class DateUtils {


    /**
     * 年-月-日 时:分:秒 显示格式
     * 备注:如果使用大写HH标识使用24小时显示格式,如果使用小写hh就表示使用12小时制格式。
     */
    public static String DATE_TO_STRING_DETAIAL_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * 年-月-日
     */
    public static String YEAR_MONTH_DAY_PATTERN = "yyyy-MM-dd";
    /**
     * 月-日 时-分
     */
    public static String MONTH_AND_HOUR_PATTERN = "MM-dd HH:mm";
    /**
     * 时-分
     */
    public static String HOUR_MINUTES_PATTERN = "HH:mm";

    private static SimpleDateFormat simpleDateFormat;
    private static Calendar calendar = Calendar.getInstance();

    public static String tranformDate(long date){
        Date date1 = new Date(date);
        simpleDateFormat = new SimpleDateFormat(DATE_TO_STRING_DETAIAL_PATTERN);
        return simpleDateFormat.format(date1);
    }
    public static String subTwoDate(long oldDate) {
        calendar.setTimeInMillis(System.currentTimeMillis());
        int currentData = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.setTimeInMillis(oldDate);
        int oldD = calendar.get(Calendar.DAY_OF_YEAR);
        int subDate = currentData - oldD;
        simpleDateFormat = new SimpleDateFormat(HOUR_MINUTES_PATTERN);
        if (subDate == 0){
            return simpleDateFormat.format(new Date(oldDate));
        }else if (subDate == 1){
            return "昨天" + simpleDateFormat.format(new Date(oldDate));
        }else if (subDate == 2){
            return "前天" + simpleDateFormat.format(new Date(oldDate));
        }else {
            simpleDateFormat.applyPattern(MONTH_AND_HOUR_PATTERN);
            return simpleDateFormat.format(new Date(oldDate));
        }
    }
}

package com.domed.api.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName DateUtils
 * @Description TODO
 * @Author 朱世鑫
 * @Date 2020-03-06 9:43
 * @Version 1.0
 **/
public class DateUtils {

    private static final String DATE_FORMATE = "yyyy-MM-dd";

    /**
     * 计算时间 yyyy-MM-dd
     * @param startTime ： 开始时间
     * @param endTime  ： 结束时间
     * @return
     */
    public static int caculateTotalTime(String startTime,String endTime) {
        return caculateTotalTime(startTime, endTime, DATE_FORMATE);
    }
    public static int caculateTotalTime(String startTime,String endTime, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date1 = null;
        Date date = null;
        Long l = 0L;
        try {
            date = formatter.parse(startTime);
            long ts = date.getTime();
            date1 = formatter.parse(endTime);
            long ts1 = date1.getTime();

            l = (ts - ts1) / (1000 * 60 * 60 * 24);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return l.intValue();
    }

    /**
     * 返回当前时间 yyyy-MM-dd
     * @return
     */
    public static String getNowStrDate() {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMATE);
        return formatter.format(new Date());
    }
    public static Date getNowDate() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMATE);
        return formatter.parse(formatter.format(new Date()));
    }
    /**
     * 毫秒转时间 yyyy-MM-dd
     * @return
     */
    public static String msToStrDate(long ms) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMATE);
        Date date = new Date();
        date.setTime(ms);
        return formatter.format(date);
    }
    /**
     * 时间转毫秒 yyyy-MM-dd
     * @return
     */
    public static long strToMs(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMATE);
        return formatter.parse(date).getTime();
    }

    /**
     * @Description: 后退天数得时间
     * @author syl
     * @date 2020/3/11 14:45
     */
    public static Date getNextDaysDate(Date beginTime,Integer days){
        //后推天数
        Calendar c = Calendar.getInstance();
        c.setTime(beginTime);
        c.add(Calendar.DAY_OF_MONTH, days);
        return c.getTime();
    }

    public static Date getNextYearsDate(Date beginTime,Integer years){
        //后推天数
        Calendar c = Calendar.getInstance();
        c.setTime(beginTime);
        c.add(Calendar.YEAR, years);
        return c.getTime();
    }

    public static String dateToStr(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMATE);
        return formatter.format(date);
    }

    public static String strToDate(String str) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMATE);
        Date newDate = formatter.parse(str);
        return formatter.format(newDate);
    }



    /**
      * @Description: 相差月份
      * @author syl
      * @date 2020/12/29 13:54
      */
    public static int getMonthSpace(Date date1, Date date2) {
//        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMATE);
        int result=0;
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        result =(cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR)) * 12 + cal1.get(Calendar.MONTH)- cal2.get(Calendar.MONTH);
        return result==0?1 : Math.abs(result);
    }

    public static Map<String, Object> getCalenderDate(Date date1) {
        Map<String, Object> resultMap = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        resultMap.put("YEAR",calendar.get(Calendar.YEAR));					//获取年份
        resultMap.put("MONTH",calendar.get(Calendar.MONTH));					//获取月份
        resultMap.put("DATE",calendar.get(Calendar.DATE));					//获取日

        resultMap.put("HOUR",calendar.get(Calendar.HOUR));					//时（12小时制）
        resultMap.put("HOUR_OF_DAY",calendar.get(Calendar.HOUR_OF_DAY));				//时（24小时制）
        resultMap.put("MINUTE",calendar.get(Calendar.MINUTE));					//分
        resultMap.put("SECOND",calendar.get(Calendar.SECOND));					//秒

        resultMap.put("DAY_OF_WEEK",calendar.get(Calendar.DAY_OF_WEEK));
        return resultMap;
    }

    /**
     * 通过秒毫秒数判断两个时间的间隔的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1,Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }
    public static String getDatePoor(Date endDate, Date nowDate) {

        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    public static Date getNextYear(Date startDate,Integer years){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.YEAR, years);
        Date time = calendar.getTime();
        return time;
    }

    public static boolean isIso8601Format(String time) {
        try {
            DateTimeFormatter.ISO_DATE_TIME.parse(time);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isValidFormat(String time) {
        try {
            DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(time);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}

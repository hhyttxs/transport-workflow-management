package com.domed.util.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
    private Date date;

    public DateFormat(Date date){
        this.date = date;
    }

    //获取原生对象的方法
    public Date getDate(){return date;};

    /**
     *获取格式化后的日期的字符串
     * @return
     */
    public String getDateStr(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    /**
     *获取格式化后的日期和时间的字符串
     * @return
     */
    public String getDateTimeStr(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }

    /**
     *获取格式化后的日期和时间的字符串
     * @return
     */
    public String getMonthDateStr(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        return df.format(date);
    }

    /**
     * 获取这天开始的字符串
     * @return
     */
    public String getDateBeginStr(){
        return getDateStr()+" 00:00:00";
    }
    /**
     * 获取这天结束的字符串
     * @return
     */
    public String getDateEndStr(){
        return getDateStr()+" 23:59:59";
    }
}

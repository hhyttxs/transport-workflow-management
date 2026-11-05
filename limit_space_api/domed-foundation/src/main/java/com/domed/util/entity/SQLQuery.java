package com.domed.util.entity;

import com.domed.util.CommonUtil;
import com.domed.util.entity.SQLQueryItem.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class SQLQuery {
    //表名
    private String tableName;
    //字段名
    private String fieldName;
    //字段值
    private String fieldValue;



    public SQLQuery(String tableName,String fieldName,String fieldValue){
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getEquals(Map params){
        return getQueryString(params,new EqualSQLQueryItem());
    }
    public String getEquals(Map params,String prefix){
        return getQueryString(params,new EqualSQLQueryItem(),prefix);
    }
    public String getLike(Map params){
        return getQueryString(params,new LikeSQLQueryItem());
    }
    public String getLike(Map params,String prefix){
        return getQueryString(params,new LikeSQLQueryItem(),prefix);
    }

    public String getDateBegin(Map params,String prefix) throws ParseException {
        if (fieldValue == null || "".equals(fieldValue)) return "";

        String rdFieldName = fieldName + UUID.randomUUID().toString().replace("-","");
        DateFormat df = CommonUtil.getDateFormatFromDatePicker(fieldValue);
        params.put(rdFieldName,df.getDateBeginStr());
        return " "+prefix+" "+tableName+"."+fieldName+new DateBeginSQLQueryItem().getQueryString(rdFieldName);
    }
    public String getDateBegin(Map params) throws ParseException {
        return getDateBegin(params,"AND");
    }

    public String getDateEnd(Map params,String prefix) throws ParseException {
        if (fieldValue == null || "".equals(fieldValue)) return "";

        String rdFieldName = fieldName + UUID.randomUUID().toString().replace("-","");
        DateFormat df = CommonUtil.getDateFormatFromDatePicker(fieldValue);
        params.put(rdFieldName,df.getDateEndStr());
        return " "+prefix+" "+tableName+"."+fieldName+new DateEndSQLQueryItem().getQueryString(rdFieldName);
    }
    public String getDateEnd(Map params) throws ParseException {
        return getDateEnd(params,"AND");
    }

    public String getQueryString(Map params, BaseSQLQueryItem sqlQueryItem){
        return getQueryString(params,sqlQueryItem,"AND");
    }
    public String getQueryString(Map params, BaseSQLQueryItem sqlQueryItem,String prefix){
        if (fieldValue == null || "".equals(fieldValue)) return "";
        params.put(fieldName,fieldValue);
        return " "+prefix+" "+tableName+"."+fieldName+sqlQueryItem.getQueryString(fieldName);
    }

    public String getPriceBegin(Map params,String prefix) throws ParseException {
        if (fieldValue == null || "".equals(fieldValue)) return "";
        String rdFieldName = fieldName + UUID.randomUUID().toString().replace("-","");
        params.put(rdFieldName,new BigDecimal(fieldValue));
        return " "+prefix+" "+tableName+"."+fieldName+new DateBeginSQLQueryItem().getQueryString(rdFieldName);
    }
    public String getPriceBegin(Map params) throws ParseException {
        return getPriceBegin(params,"AND");
    }

    public String getPriceEnd(Map params,String prefix) throws ParseException {
        if (fieldValue == null || "".equals(fieldValue)) return "";
        String rdFieldName = fieldName + UUID.randomUUID().toString().replace("-","");
        params.put(rdFieldName,new BigDecimal(fieldValue));
        return " "+prefix+" "+tableName+"."+fieldName+new DateEndSQLQueryItem().getQueryString(rdFieldName);
    }
    public String getPriceEnd(Map params) throws ParseException {
        return getPriceEnd(params,"AND");
    }
}

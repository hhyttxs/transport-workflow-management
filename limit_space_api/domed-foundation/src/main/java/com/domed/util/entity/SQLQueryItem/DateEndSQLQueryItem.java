package com.domed.util.entity.SQLQueryItem;

public class DateEndSQLQueryItem extends BaseSQLQueryItem {
    @Override
    public String getQueryString(String fieldName) {
        return " <= :"+fieldName+"  ";
    }
}

package com.domed.util.entity.SQLQueryItem;

public class EqualSQLQueryItem extends BaseSQLQueryItem {
    @Override
    public String getQueryString(String fieldName) {
        return " = :"+fieldName+" ";
    }
}

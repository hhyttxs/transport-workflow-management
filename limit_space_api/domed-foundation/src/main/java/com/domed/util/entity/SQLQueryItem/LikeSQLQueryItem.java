package com.domed.util.entity.SQLQueryItem;

public class LikeSQLQueryItem extends BaseSQLQueryItem {
    @Override
    public String getQueryString(String fieldName) {
        return " LIKE CONCAT('%',:"+fieldName+",'%') ";
    }
}

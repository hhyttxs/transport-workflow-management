package com.domed.util.entity.SQLQueryItem;

import com.domed.util.CommonUtil;
import com.domed.util.entity.DateFormat;

public class DateBeginSQLQueryItem extends BaseSQLQueryItem {
    @Override
    public String getQueryString(String fieldName) {
        return " >= :"+fieldName+"  ";
    }
}

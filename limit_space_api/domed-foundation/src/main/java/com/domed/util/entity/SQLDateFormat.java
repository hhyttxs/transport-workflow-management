package com.domed.util.entity;

/**
 * 格式化日期的对象
 */
public class SQLDateFormat{
    //表名
    private String tableName;
    //字段名
    private String fiedName;
    //别名
    private String aliasName;

    public SQLDateFormat(String tableName,String fiedName){
        this.tableName = tableName;
        this.fiedName = fiedName;
        this.aliasName = fiedName;
    }

    public SQLDateFormat(String tableName,String fiedName,String aliasName){
        this.tableName = tableName;
        this.fiedName = fiedName;
        this.aliasName = aliasName;
    }

    public String getDateTime(Boolean hasEnd){
        return "DATE_FORMAT("+tableName+"."+fiedName+", '%Y-%c-%d %H:%i:%s') AS "+aliasName+(hasEnd?", ":" ");
    }

    public String getDateTime(){
        return this.getDateTime(true);
    }

    public String getDate(Boolean hasEnd){
        return "DATE_FORMAT("+tableName+"."+fiedName+", '%Y-%c-%d') AS "+aliasName+(hasEnd?", ":" ");
    }

    public String getDate(){
        return this.getDate(true);
    }
}

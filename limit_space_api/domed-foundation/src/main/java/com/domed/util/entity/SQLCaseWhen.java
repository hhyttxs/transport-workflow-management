package com.domed.util.entity;

import java.util.Map;

/**
 * 格式化caseWhen对象
 */
public class SQLCaseWhen {
    //表名
    private String tableName;
    //字段名
    private String fiedName;
    //别名
    private String aliasName;
    //其他名称
    private String elseName;
    //参数map
    private Map transMap;

    public SQLCaseWhen(String tableName, String fiedName,Map transMap){
        this.tableName = tableName;
        this.fiedName = fiedName;
        this.aliasName = fiedName;
        this.elseName = "";
        this.transMap = transMap;
    }

    public SQLCaseWhen(String tableName, String fiedName, String aliasName,Map transMap){
        this.tableName = tableName;
        this.fiedName = fiedName;
        this.aliasName = aliasName;
        this.elseName = "";
        this.transMap = transMap;

    }

    public SQLCaseWhen(String tableName, String fiedName, String aliasName,String elseName,Map transMap){
        this.tableName = tableName;
        this.fiedName = fiedName;
        this.aliasName = aliasName;
        this.elseName = elseName;
        this.transMap = transMap;
    }

    public String getSQL(Boolean hasEnd){
        StringBuffer sb = new StringBuffer();

        sb.append(" CASE ");
        sb.append("   "+tableName + "."+fiedName+" ");
        for(Object obj :transMap.keySet()){
            String key = obj.toString();
            String value = transMap.get(key) == null ? "" : (String)transMap.get(key);
            if("".equals(value)) value = key;
            sb.append(" WHEN "+key+ " THEN '"+value + "' ");
        }
        sb.append(" ELSE  '"+ elseName+"' ");
        sb.append(" END ");
        if(aliasName != null && !"".endsWith(aliasName)){
            sb.append(" AS  "+ aliasName+"");
        }
        if(hasEnd) sb.append(",  ");

        return sb.toString();
    }

    public String getSQL(){
        return getSQL(true);
    }

    @Override
    public String toString(){
        return this.getSQL();
    }
}

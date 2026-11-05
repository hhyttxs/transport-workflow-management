package com.domed.service;

import com.domed.util.entity.Exp;

import java.util.List;
import java.util.Map;

/**
 * @ClassName IDelegator
 * @Description TODO
 * @Author 高杨
 * @Date 2019/11/28 11:34
 * @Version 1.0
 **/
public interface IDelegator {

    public List findByAnd(String entityName, Map<String, String> fields);

    public List findByAnd(String entityName, Map<String, String> fields, List orderBy);

    public List findByAnd(String entityName, List<String> column, Map<String, String> fields);

    public List findByAnd(String entityName, List<String> column, Map<String, String> fields, List orderBy);

    /* 
     *@Description left join查询
     *@Author 高杨
     *@Date 2019/12/11 17:55
     *@Param [entityNameMap, columnList, whereCondition, orderBy]
     *@return java.util.List
    **/
    public List findByView(Map<String, List<Map<String, List<Exp>>>> entityNameMap,
                           List<String> columnList, List<Exp> whereCondition, List orderBy );

    /* 
     *@Description inner join
     *@Author 高杨
     *@Date 2019/12/11 17:56
     *@Param [entityName, columnList, whereCondition, orderBy]
     *@return java.util.List
    **/
    public List findByView(String[] entityName, List<String> columnList,
                           List<Exp> whereCondition, List orderBy );
}

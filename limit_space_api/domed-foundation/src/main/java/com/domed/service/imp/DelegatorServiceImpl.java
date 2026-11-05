package com.domed.service.imp;

import com.domed.core.exception.ApplicationException;
import com.domed.core.service.ICommonService;
import com.domed.service.IDelegator;
import com.domed.util.entity.Exp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ClassName DelegatorServiceImpl
 * @Description TODO
 * @Author 高杨
 * @Date 2019/11/28 11:38
 * @Version 1.0
 **/
@Service
@Transactional
public class DelegatorServiceImpl implements IDelegator {
    @Autowired
    private ICommonService commonService;

    @Override
    public List findByAnd(String entityName, Map<String, String> fields) {
        return this.findByAnd(entityName, null, fields, null);
    }

    @Override
    public List findByAnd(String entityName, Map<String, String> fields, List orderBy) {
        List<String> columnList = null;
        return this.findByAnd(entityName, columnList, fields, orderBy);
    }

    @Override
    public List findByAnd(String entityName, List<String> columnList, Map<String, String> fields, List orderBy) {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT ");
        sb.append(columnList(columnList));
        sb.append(" FROM ");
        sb.append(entityName);
        if(fields != null && fields.size() > 0){
            sb.append(" WHERE 1=1 ");
            for (Map.Entry<String, String> entry : fields.entrySet()) {
                sb.append(" AND "+entry.getKey()+"='"+(String)entry.getValue()+"'");
            }
        }
        if(orderBy != null && orderBy.size() > 0){
            sb.append(" ORDER BY " + toOrderBy(orderBy));
        }
        //System.out.println(sb.toString());
        List<Map> list = commonService.nativeQuery(sb.toString());
        return list;
    }

    public String toOrderBy(List orderBy){
        StringBuffer sb = new StringBuffer();
        for (int i = 0;i < orderBy.size(); i++) {
            String column = (String)orderBy.get(i);
            if(i == orderBy.size() - 1){
                sb.append(column);
            }else{
                sb.append(column + ", ");
            }
        }
        return  sb.toString();
    }

    /* 
     *@Description 获取列
     *@Author 高杨
     *@Date 2019/12/11 16:06
     *@Param [columnList]
     *@return java.lang.String
    **/
    public String columnList(List<String> columnList){
        StringBuffer sb = new StringBuffer();
        if(!(columnList == null || columnList.size() == 0)){
            for (int i = 0;i < columnList.size(); i++) {
                String column = (String)columnList.get(i);
                if(i == columnList.size() - 1){
                    sb.append(column);
                }else{
                    sb.append(column + ", ");
                }
            }
        }else{
            sb.append(" * ");
        }
        return sb.toString();
    }

    public List findByAnd(String entityName, List<String> column, Map<String, String> fields) {
        return this.findByAnd(entityName, column ,fields, null);
    }


    /*
     *@Description 多表查询共同方法封装
     *@Author 高杨
     *@Date 2019/12/11 10:41
     *@Param [entityName, whereCondition]
     *@return java.util.List
    **/
    @Override
    public List findByView(String[] entityName, List<String> columnList,
                           List<Exp> whereCondition, List orderBy ) {
        StringBuffer sb = new StringBuffer();
        if(entityName == null || entityName.length == 0){
            throw new ApplicationException(commonService.getMessage(10002L, entityName));
        }
        if(whereCondition == null || whereCondition.size() == 0){
            throw new ApplicationException(commonService.getMessage(10002L, "fields"));
        }

        sb.append(" SELECT ");
        sb.append(columnList(columnList));
        sb.append(" FROM ");
        for (int i = 0; i < entityName.length; i++) {
            if(i == 0){
                sb.append(entityName[i]);
            }else{
                sb.append(" , " + entityName[i]);
            }
        }
        sb.append(" WHERE 1=1 ");
        sb.append(makeWhereString(whereCondition));
        if(orderBy != null && orderBy.size() > 0){
            sb.append(" ORDER BY " + toOrderBy(orderBy));
        }
        //System.out.println(sb.toString());
        List<Map> list = commonService.nativeQuery(sb.toString());
        return list;
    }

    /* 
     *@Description left join查询
     *@Author 高杨
     *@Date 2019/12/11 16:12
     *@Param [entityNameMap, columnList, whereCondition, orderBy]
     *@return java.util.List
    **/
    @Override
    public List findByView(Map<String, List<Map<String, List<Exp>>>> entityNameMap,
                           List<String> columnList, List<Exp> whereCondition, List orderBy ) {
        StringBuffer sb = new StringBuffer();
        if(entityNameMap == null || entityNameMap.size() == 0){
            throw new ApplicationException(commonService.getMessage(10002L, "entityNameMap"));
        }
        if(whereCondition == null || whereCondition.size() == 0){
            throw new ApplicationException(commonService.getMessage(10002L, "fields"));
        }

        sb.append(" SELECT ");
        sb.append(columnList(columnList));
        sb.append(" FROM ");
        for (Map.Entry<String, List<Map<String, List<Exp>>>> entry : entityNameMap.entrySet()) {
           sb.append(entry.getKey() + " ");//主表

           List<Map<String, List<Exp>>> leftList = entry.getValue();
           for (Map<String, List<Exp>> map : leftList) {
               Map.Entry<String, List<Exp>> entryLeft = itearFirst(map);
               sb.append(" LEFT JOIN " + entryLeft.getKey() + " ON ");

               List<Exp> entityList = entryLeft.getValue();
               for (int i = 0; i < entityList.size(); i++) {
                   Exp exp = entityList.get(i);
                   if(i == 0){
                       sb.append(exp.makeWhereString());
                   }else{
                       sb.append(" AND " + exp.makeWhereString());
                   }
               }
           }
        }
        sb.append(" WHERE 1=1 ");
        sb.append(makeWhereString(whereCondition));
        if(orderBy != null && orderBy.size() > 0){
            sb.append(" ORDER BY " + toOrderBy(orderBy));
        }

        //System.out.println(sb.toString());
        List<Map> list = commonService.nativeQuery(sb.toString());
        return list;
    }

    /* 
     *@Description 取第一个map不存在一个map多个条件，多个条件一定是多个map对象
     *@Author 高杨
     *@Date 2019/12/11 15:36
     *@Param [map]
     *@return java.util.Map.Entry
    **/
    public Map.Entry itearFirst(Map<String, List<Exp>> map){
        Map.Entry<String, List<Exp>> rtnEntry = null;
        for (Map.Entry<String, List<Exp>> entry : map.entrySet()) {
            String key = entry.getKey();
            if (key != null) {
                rtnEntry = entry;
                break;
            }
        }
        return rtnEntry;
    }

    /* 
     *@Description 设置where条件
     *@Author 高杨
     *@Date 2019/12/11 15:20
     *@Param [whereCondition]
     *@return java.lang.String
    **/
    public String makeWhereString(List<Exp> whereCondition){
        StringBuffer sb = new StringBuffer();
        for (Exp exp : whereCondition) {
            sb.append(" AND " + exp.makeWhereString());
        }
        return sb.toString();
    }
}

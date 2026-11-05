package com.domed.util;

import com.domed.constant.Constant;
import com.domed.core.exception.ApplicationException;
import com.domed.core.query.support.IPageList;
import com.domed.core.service.ICommonService;
import com.domed.util.entity.*;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class CommonUtils {
    public static Map list2MapGroupBy(List list, String paramName) {
        HashMap groupByMap = new HashMap();
        for(Object obj : list){
            Map tempMap = (Map)obj;

            String key = tempMap.get(paramName).toString();

            if(groupByMap.get(key) != null){
                List subList = (List) groupByMap.get(key);
                subList.add(tempMap);
            }else {
                List subList = new ArrayList();
                subList.add(tempMap);
                groupByMap.put(key,subList);
            }
        }

        return groupByMap;
    }

    public static String getParam(Map infoMap, String name) {
        if (infoMap == null){
            return "";
        }

        return infoMap.get(name)==null?"":infoMap.get(name).toString();
    }

    public static BigDecimal getMapValueForBigDecimal(Map map, String key) {
        if(map == null){
            return new BigDecimal(0);
        }
        BigDecimal rtnStr = map.get(key) == null ? new BigDecimal(0) : new BigDecimal(map.get(key).toString());
        return rtnStr;
    }

    public static List getParamList(Map infoMap, String paramName) {
        if (infoMap == null){
            return new ArrayList();
        }

        return infoMap.get(paramName)==null?new ArrayList(): (List) infoMap.get(paramName);
    }


    public static Map saveIPageList2ResponseBody(IPageList iPageList, Map result, List editedResult) {

        result.put("child",editedResult==null?iPageList.getResult():editedResult);
        result.put("currentPage",iPageList.getCurrentPage());
        result.put("pageCount",iPageList.getRowCount());
        result.put("totalPage",iPageList.getPages());


        return result;
    }

    public static Map saveIPageList2ResponseBody(ICommonService commonApiService, StringBuilder sb, String currentPage, String numberPage) {
        Map<String, Object> result = new HashMap<>(16);

        IPageList iPageList = commonApiService.pageQuery(sb.toString(),currentPage,numberPage,null);

        result.put("data",iPageList.getResult());
        result.put("currentPage",iPageList.getCurrentPage());
        result.put("pageCount",iPageList.getRowCount());
        result.put("totalPage",iPageList.getPages());

        return result;
    }
    public static Map saveIPageList2ResponseBody(IPageList iPageList, Map result) {
        return saveIPageList2ResponseBody(iPageList,result,null);
    }

    public static List listMap2PropList(List<Map> list, String propName) {

        List result = list.stream().map((map)->{
            return CommonUtil.getParam(map,propName);
        }).collect(Collectors.toList());

        return result;
    }


    /**
     * 根据前台传来的date字符串，获取一个formatDate的对象
     * @param inputDate
     * @return
     * @throws ParseException
     */
    public static DateFormat getDateFormatFromDatePicker(String inputDate) throws ParseException {
        inputDate = inputDate.replace("Z", " UTC");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        Date d = format.parse(inputDate);
        return new DateFormat(d);
    }

    public static DateFormat getDateFormatFromSql(String inputDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date d = format.parse(inputDate);
        return new DateFormat(d);
    }

    /**
     * 根据常量类里的名称，获得key为value,值为description的Map
     * @param mapName
     * @return
     */
    public static Map getConstantDescTransMap(String mapName){
        Map result = new HashMap();

        List<Map> list = (List<Map>) Constant.getValues(mapName);
        if(list == null) throw new ApplicationException("常量数组不存在:"+mapName);

        list.stream().reduce(result,(collect,item) ->{
            String dictionaryValue = (String) item.get("dictionaryValue");
            String dictionaryTitle = (String) item.get("dictionaryTitle");
            collect.put(dictionaryValue,dictionaryTitle);
            return collect;
        });

        return result;
    }

    /**
     * 获取case-when的sql语句
     * @param tableName
     * @param fieldName
     * @param transMap
     * @return
     */
    public static SQLCaseWhen getSQLCaseWhen(String tableName, String fieldName, Map transMap){
        return new SQLCaseWhen(tableName,fieldName,transMap);
    }
    public static SQLCaseWhen getSQLCaseWhen(String tableName, String fieldName,String aliasName,Map transMap){
        return new SQLCaseWhen(tableName,fieldName,aliasName,transMap);
    }
    public static SQLCaseWhen getSQLCaseWhenFromConstant(String tableName, String fieldName,String aliasName,String constantMapName){
        Map transMap = CommonUtil.getConstantDescTransMap(constantMapName);
        return new SQLCaseWhen(tableName,fieldName,aliasName,transMap);
    }
    public static SQLCaseWhen getSQLCaseWhenFromConstant(String tableName, String fieldName,String aliasName,String elseName,String constantMapName){
        Map transMap = CommonUtil.getConstantDescTransMap(constantMapName);
        return new SQLCaseWhen(tableName,fieldName,aliasName,elseName,transMap);
    }
    public static SQLCaseWhen getSQLCaseWhenFromConstant(String tableName, String fieldName,String constantMapName){
        return getSQLCaseWhenFromConstant(tableName,fieldName,fieldName,constantMapName);
    }
    public static SQLQuery getSQLQuery(String tableName, String fieldName, String fieldValue){
        return new SQLQuery(tableName,fieldName,fieldValue);
    }

    public static void controllerAction(Logger logger, HttpServletRequest request, HttpServletResponse response, ControllerFunctionWrapper controllerFunctionWrapper){
        new ControllerWrapper(logger, request, response, controllerFunctionWrapper).computed();
    }
}

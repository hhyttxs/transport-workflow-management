package com.domed.util;

import com.domed.common.result.Result;
import com.domed.constant.Constant;
import com.domed.core.exception.ApplicationException;
import com.domed.core.query.support.IPageList;
import com.domed.util.entity.*;
import com.domed.util.entity.TypeCheck.TypeCheckMapObj;
import com.domed.util.entity.TypeCheck.TypeCheckObj;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 常用的共通方法
 */
public class CommonUtil {
    /**
     * 把iPageList中的数据放入结果result中
     * @param iPageList
     * @param result
     */
    public static void saveIPageList2ResponseBody(IPageList iPageList,Map result){
        result.put("data",iPageList.getResult()==null?new ArrayList():iPageList.getResult());
        result.put("pageCount",iPageList.getRowCount());
        result.put("totalPage",iPageList.getPages());
    }

    /**
     * 获取格式化时间的对象
     * @param tableName
     * @param fieldName
     * @return
     */
    public static SQLDateFormat getSQLDateObj(String tableName, String fieldName){
        return new SQLDateFormat(tableName,fieldName);
    }
    public static SQLDateFormat getSQLDateObj(String tableName, String fieldName,String aliasName){
        return new SQLDateFormat(tableName,fieldName,aliasName);
    }

    /**
     * 根据常量类里的名称，获得key为value,值为description的Map
     * @param mapName
     * @return
     */
    public static Map getConstantDescTransMap(String mapName){
        Map result = new HashMap();

        List<Map> list = (List<Map>)Constant.getValues(mapName);
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
    public static SQLCaseWhen getSQLCaseWhen(String tableName, String fieldName,Map transMap){
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
    public static SQLQuery getSQLQuery(String tableName,String fieldName,String fieldValue){
        return new SQLQuery(tableName,fieldName,fieldValue);
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

    public static DateFormat getDateFormatFromSql(String inputDate,String getformat) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(getformat);
        Date d = format.parse(inputDate);
        return new DateFormat(d);
    }

    public static void controllerAction(Logger logger, HttpServletRequest request, HttpServletResponse response, ControllerFunctionWrapper controllerFunctionWrapper){
        new ControllerWrapper(logger, request, response, controllerFunctionWrapper).computed();
    }

    public static <T> Result<T> controllerResult(ControllerFunctionResult controllerFunctionResult){
        return new ControllerResult(controllerFunctionResult).computed();
    }

    /*
     *@Description 根据开始时间和结束时间计算多少小时
     *@Author 高杨
     *@Date 2020/3/14 15:34
     *@Param [beginDate, endDate]
     *@return double
    **/
    public static int getBetweenDaysByHour(String beginDate, String endDate) throws ParseException{
        beginDate = beginDate.replace("Z", " UTC");
        endDate = endDate.replace("Z", " UTC");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        Date begin = df.parse(beginDate);
        Date end = df.parse(endDate);
        double between = (end.getTime() - begin.getTime())/1000;//除以1000是为了转换成秒
        int hours = (new Double(between/3600)).intValue();
        return hours;
    }

    /**
     * 将map中的数据放入map中
     * @param infoMap
     * @param obj
     */
    public static void saveMap2Domain(Map infoMap, Object obj) {
        BeanWrapper wrapper = new BeanWrapperImpl(obj);
        PropertyDescriptor propertys[] = wrapper.getPropertyDescriptors();
        for (int i = 0; i < propertys.length; i++) {
            String name = propertys[i].getName();
            //判断属性是否可写,并且写入的方法不为空
            if (wrapper.isWritableProperty(name) && propertys[i].getWriteMethod() != null) {
                Object propertyValue = null;
                for (Object keyObject : infoMap.keySet()) {
                    String key = (String)keyObject;
                    //如果map中的某个key与属性的名字相同
                    if (key.equals(propertys[i].getName())) {
                        propertyValue = getPropertyValue(propertys[i],infoMap.get(key));
                        if (propertyValue == null){
                            continue;
                        }
                        wrapper.setPropertyValue(propertys[i].getName(), propertyValue);
                    }
                }
            }
        }
    }

    private static Object getPropertyValue(PropertyDescriptor property,Object value) {
        Object result = null;

        if(value == null || "".equals(value)){
            return null;
        }

        Class clazz = property.getPropertyType();
        if(String.class.equals(clazz)){
            result = value.toString();
        }else if(Long.class.equals(clazz)){
            result = Long.valueOf(value.toString());
        }else if(Date.class.equals(clazz)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                result = sdf.parse(value.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else if(Integer.class.equals(clazz)){
            result = Integer.valueOf(value.toString());
        }else if(Boolean.class.equals(clazz)){
            if("0".equals(value.toString())){
                result = Boolean.FALSE;
            }else{
                result = Boolean.TRUE;
            }
        }else if(BigDecimal.class.equals(clazz)){
            result = new BigDecimal(value.toString());
        }else if(Float.class.equals(clazz)){
            result = Float.valueOf(value.toString());
        }

        return result;
    }

    /**
     * 移除map中的某些key
     * @param infoMap
     * @param keys
     */
    public static Map  getKeysRemovedMap(Map infoMap, String... keys) {
        Map result = new HashMap();
        List keyList = Arrays.asList(keys);
        for(Object key : infoMap.keySet()){
            if(keyList.contains(key)) continue;
            result.put(key,infoMap.get(key));
        }
        return result;
    }

    /**
     * 获取参数校验的对象
     * @param value
     * @param description
     * @return
     */
    public static TypeCheckObj getTypeCheckObj(Object value, String description){
        return new TypeCheckObj(value,description);
    }

    public static TypeCheckObj getParam(Map infoMap, String field){
        Object value = infoMap.get(field);
        return new TypeCheckObj(value,field);
    }
    public static TypeCheckObj getParam(Map infoMap, String field,String description){
        Object value = infoMap.get(field);
        return new TypeCheckObj(value,description);
    }

    public static TypeCheckMapObj getTypeCheckMapObj(Map infoMap,Map<String,String> valueDescriptionMap){
        return new TypeCheckMapObj(infoMap,valueDescriptionMap);
    }

    public static TypeCheckMapObj getTypeCheckMapObj(Map infoMap,String ... values){
        List valueList = Arrays.asList(values);
        return new TypeCheckMapObj(infoMap,valueList);
    }

    public static Map  getPartMap(Map infoMap, String... keys) {
        Map result = new HashMap();
        List keyList = Arrays.asList(keys);
        for(Object key : keyList){
            Object value = infoMap.get(key);
            result.put(key,value);
        }
        return result;
    }

    public static BigDecimalWrapper getBigDecimalWrapper(BigDecimal value){
        return new BigDecimalWrapper(value);
    }

    public static ResponseObj getResponseInfo(String str){
        return new ResponseObj(str);
    }

    public static ResponseObj getResponseInfoWithList(String str){
        return new ResponseObj(str,"data");
    }

    /**
     * 更换map的名字
     * @return
     */
    public static Map changeMapName(Map infoMap, HashMap changeMap) {
        Map result = new HashMap();
        for(Object obj : infoMap.keySet()){
            String key = obj.toString();
            Object value = infoMap.get(key);
            if(changeMap.get(key) != null) key = CommonUtil.getParam(changeMap,key).getString();

            result.put(key,value);
        }
        return result;
    }

    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
      * @Description: 复制不是null的
      * @author syl
      * @date 2020/3/17 9:41
      */
    public static void copyPropertiesIgnoreNull(Object src, Object target){
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    /**
     * @Description: 日期加天数
     * @author syl
     * @date 2020/3/17 9:41
     */
    public static String addDay(String s, int n) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Calendar cd = Calendar.getInstance();
            cd.setTime(sdf.parse(s));
            cd.add(Calendar.DAY_OF_MONTH, n);//增加一天

            return sdf.format(cd.getTime());

        } catch (Exception e) {
            return null;
        }
    }

    public static String cast(double oldval, String sformat) {
        java.text.DecimalFormat df = new java.text.DecimalFormat(sformat);
        return (df.format(oldval).toString());
    }

    public static BigDecimal getParamB(Map infoMap, String name) {
        BigDecimal d = new BigDecimal(0);
        if (infoMap == null) {
            return d;
        } else {
            try {
                d = infoMap.get(name) == null ? new BigDecimal(0) : new BigDecimal(infoMap.get(name).toString());
            } catch (Exception var6) {
                d = new BigDecimal(0);
            }

            return d;
        }
    }
}

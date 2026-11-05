package com.domed.api.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Slf4j
public class CopyBeanUtil {

    /**
     * @return
     * @Description //复制源对象和目标对象的同名属性值,对象可以是不同类型的对象.
     * @Date 15:52 2019/6/22
     * @Param [source, target] source 源对象 target 目标对象
     **/
    public static void copyObj(Object source, Object target) throws Exception{
        Class sourceClass = source.getClass();//得到对象的Class
        Class targetClass = target.getClass();//得到对象的Class
        Field[] sourceFields = sourceClass.getDeclaredFields();//得到Class对象的所有属性
        Field[] targetFields = targetClass.getDeclaredFields();//得到Class对象的所有属性

        for (Field sourceField : sourceFields) {
            String name = sourceField.getName();//属性名
            if ("serialVersionUID".equals(name)) {
                continue;
            }
            Class type = sourceField.getType();//属性类型
            String methodName = name.substring(0, 1).toUpperCase() + name.substring(1);
            Method getMethod = sourceClass.getMethod("get" + methodName);//得到属性对应get方法
            Object value = getMethod.invoke(source);//执行源对象的get方法得到属性值
            for (Field targetField : targetFields) {
                String targetName = targetField.getName();//目标对象的属性名
                if ("serialVersionUID".equals(targetName)) {
                    continue;
                }
                if (targetName.equals(name) && value != null
                        && !"".equals(value.toString().trim())){
                    if(target.getClass().getDeclaredField(name).getType().equals(type)){
                        Method setMethod = targetClass.getMethod("set" + methodName, type);//属性对应的set方法
                        setMethod.invoke(target, value);//执行目标对象的set方法
                    }else {
                        log.info(name + ",属性不一样,soc属性:"+type+",target属性:"+target.getClass().getDeclaredField(name).getType());
                    }

                }
            }
        }

    }

}

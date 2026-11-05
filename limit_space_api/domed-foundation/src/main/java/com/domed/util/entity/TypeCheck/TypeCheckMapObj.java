package com.domed.util.entity.TypeCheck;

import com.domed.core.exception.ApplicationException;
import com.domed.util.CommonUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeCheckMapObj {
    private Map infoMap;
    private Map<String,String> valueDescriptionMap;

    public TypeCheckMapObj(Map infoMap,Map<String,String> valueDescriptionMap){
        this.infoMap = infoMap;
        this.valueDescriptionMap = valueDescriptionMap;
    }

    public TypeCheckMapObj(Map infoMap, List<String> valueList){
        this.infoMap = infoMap;
        Map<String,String> valueDescriptionMap = new HashMap();
        for(String value: valueList){
            valueDescriptionMap.put(value,value);
        }
        this.valueDescriptionMap = valueDescriptionMap;
    }

    private TypeCheckObj getTypeCheckObj(Object key){
        Object value = this.infoMap.get(key);
        String description = key.toString();
        if(this.valueDescriptionMap.get(key) != null ) description = this.valueDescriptionMap.get(key);

        return CommonUtil.getTypeCheckObj(value,description);
    }

    public void checkNull(){
        for(Object key : this.valueDescriptionMap.keySet()){
            this.getTypeCheckObj(key).checkNull();
        }
    };

    public void checkInt(){
        for(Object key : this.valueDescriptionMap.keySet()){
            this.getTypeCheckObj(key).checkInt();
        }
    };

    public void checkLong(){
        for(Object key : this.valueDescriptionMap.keySet()){
            this.getTypeCheckObj(key).checkLong();
        }
    };

    public void checkDecimal(){
        for(Object key : this.valueDescriptionMap.keySet()){
            this.getTypeCheckObj(key).checkDecimal();
        }
    };
    public void checkDateTime(){
        for(Object key : this.valueDescriptionMap.keySet()){
            this.getTypeCheckObj(key).checkDecimal();
        }
    };

}

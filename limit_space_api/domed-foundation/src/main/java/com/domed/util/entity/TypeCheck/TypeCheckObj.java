package com.domed.util.entity.TypeCheck;

import com.domed.core.exception.ApplicationException;
import com.domed.util.CommonUtil;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TypeCheckObj {
    private String description;
    private Object value;

    @Override
    public String toString() {
        return "TypeCheckObj{" +
                "description='" + description + '\'' +
                ", value=" + value +
                '}';
    }

    public TypeCheckObj(Object value, String description){
        if("".equals(value)) value = null;
        this.value = value;
        this.description = description;
    }

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public Object getValue() {return value;}

    public void setValue(Object value) {this.value = value;}

    public TypeCheckObj checkInt(){
        if (this.value == null) return this;
        try{
            Integer intValue = Integer.valueOf(value.toString());
        }catch (Exception e){
            throw new ApplicationException(this.description+"必须为整数");
        }
        return this;
    }

    public TypeCheckObj checkLong(){
        if (this.value == null) return this;
        try{
            Long longValue = Long.valueOf(value.toString());
        }catch (Exception e){
            throw new ApplicationException(this.description+"必须为长整数");
        }
        return this;
    }

    public TypeCheckObj checkDecimal(){
        if (this.value == null) return this;
        try{
            BigDecimal decimalValue = new BigDecimal(value.toString());
        }catch (Exception e){
            throw new ApplicationException(this.description+"必须为数字");
        }
        return this;
    }

    public TypeCheckObj checkDateTime(){
        if (this.value == null) return this;

        try{
            Date data = CommonUtil.getDateFormatFromDatePicker(value.toString()).getDate();
        }catch (Exception e){
            throw new ApplicationException(this.description+"必须为日期");
        }
        return this;
    }

    public TypeCheckObj checkList(){
        try{
            List list = (List)value;
        }catch (Exception e){
            throw new ApplicationException(this.description+"必须为列表");
        }
        return this;
    }

    public TypeCheckObj checkNull(){
        if(this.value == null) throw new ApplicationException(this.description+"不能为空");
        if(this.value instanceof String && "".equals(this.value)) throw new ApplicationException(this.description+"不能为空");
        return this;
    }



    public Integer getInt(){
         this.checkInt();
         return value == null?null:Integer.valueOf(value.toString());
    }

    public Long getLong(){
        this.checkLong();
        return value == null?null:Long.valueOf(value.toString());
    }

    public BigDecimal getDecimal(){
        this.checkDecimal();
        return  value == null?null:new BigDecimal(value.toString());
    }

    public Date getDate() throws ParseException {
        this.checkDateTime();
        return  value == null?null:CommonUtil.getDateFormatFromDatePicker(value.toString()).getDate();
    }

    public List getList() {
        this.checkList();
        return  value == null?new ArrayList():(List)value;
    }


    public String getString(){
        return value == null?"":value.toString();
    }
}

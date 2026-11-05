package com.domed.api.util.entity;

import com.domed.core.exception.ApplicationException;

import java.util.Map;

public class DataAuthModelDTO {
    private StringBuffer sb;
    private String staff_id;
    private String functionModel_id;
    private String tableName;
    private Map param;
    private String extraOrStr;

    public StringBuffer getSb() {
        return sb;
    }

    public void setSb(StringBuffer sb) {
        this.sb = sb;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public String getFunctionModel_id() {
        return functionModel_id;
    }

    public void setFunctionModel_id(String functionModel_id) {
        this.functionModel_id = functionModel_id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map getParam() {
        return param;
    }

    public void setParam(Map param) {
        this.param = param;
    }

    public String getExtraOrStr() {
        return extraOrStr;
    }

    public void setExtraOrStr(String extraOrStr) {
        this.extraOrStr = extraOrStr;
    }

    /**
     * 检验参数
     */
    public void check(){
        if(sb == null) throw new ApplicationException("sql不能为空");
        if(staff_id == null) throw new ApplicationException("员工id不能为空");
        if(functionModel_id == null) throw new ApplicationException("模块id不能为空");
        if(tableName == null) throw new ApplicationException("表名不能为空");
        if(param == null) throw new ApplicationException("param不能为空");
    }
}

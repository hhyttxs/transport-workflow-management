package com.domed.util.entity;

import com.alibaba.fastjson.JSON;
import com.domed.core.exception.ApplicationException;

import java.util.List;
import java.util.Map;

public class ResponseObj {

    private String msg;
    private String code;
    private Map data;
    private List list;

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }

    public Map getData() {
        if(!"0".equals(code)) throw new ApplicationException(msg);
        return data;
    }

    public List getList() {
        if(!"0".equals(code)) throw new ApplicationException(msg);
        return list;
    }

    public ResponseObj(String nativeResponse) {

        Map resultMap = JSON.parseObject(nativeResponse);
        if(resultMap.get("msg") != null) this.msg = resultMap.get("msg").toString();
        if(resultMap.get("code") != null) this.code =  resultMap.get("code").toString();
        if(resultMap.get("data") != null) this.data = JSON.parseObject(resultMap.get("data").toString());
    }

    public ResponseObj(String nativeResponse,String listName) {

        Map resultMap = JSON.parseObject(nativeResponse);
        if(resultMap.get("msg") != null) this.msg = resultMap.get("msg").toString();
        if(resultMap.get("code") != null) this.code =  resultMap.get("code").toString();
        if(resultMap.get("data") != null) this.list = JSON.parseArray(resultMap.get("data").toString());
    }

    public boolean isSuccess(){
        return this.code.equals("0");
    }
}

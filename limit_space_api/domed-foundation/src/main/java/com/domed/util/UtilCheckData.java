package com.domed.util;

import com.domed.core.dao.IGenericDAO;
import com.domed.core.exception.ApplicationException;
import com.domed.core.service.ICommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @ClassName UtilCheckData
 * @Description 校验数据
 * @Author 高杨
 * @Date 2020/3/11 20:27
 * @Version 1.0
 **/
public class UtilCheckData {

    /**
     * @Title: 如何返回true那么字符串就是空，是null或者""都是空
     * @Description:
     * @author 高杨
     * @date 2022/6/14 17:28
     * @return:
     */
    public static boolean ckStrNull(String value){
        if(value == null || value.trim().equals("")){
            return true;
        }
        return  false;
    }

}

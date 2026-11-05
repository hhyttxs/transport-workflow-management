package com.domed.api.service.commonService.impl;

import com.domed.api.bean.PageModel;
import com.domed.api.service.commonService.IPageService;
import com.domed.api.service.commonService.base.BaseDao;
import com.domed.core.dao.IGenericDAO;
import com.domed.core.query.support.IPageList;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author LiRong
 * @Date 2024/4/13 12:51
 **/
@Service
@Transactional
public class PageServiceImpl implements IPageService {

    @Autowired
    private BaseDao baseDao;

    @Override
    public PageModel queryPageList(String sql, String page, String rows, List<Object> param) {
        if (page == null || "".equals(page)) page = "1";
        int pageNumberInt = Integer.valueOf(page);
        PageModel pagemodel = baseDao.doList(sql, pageNumberInt, Integer.valueOf(rows), param, new PageModel());
        return pagemodel;
    }


}

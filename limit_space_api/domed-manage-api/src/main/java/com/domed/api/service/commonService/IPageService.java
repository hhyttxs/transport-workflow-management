package com.domed.api.service.commonService;

import com.domed.api.bean.PageModel;
import com.domed.core.query.support.IPageList;
import com.domed.core.query.support.IQueryObject;

import java.util.List;

/**
 * @Description
 * @Author LiRong
 * @Date 2024/4/13 12:51
 **/
public interface IPageService {
    PageModel queryPageList(String sql, String page, String rows, List<Object> param);
}

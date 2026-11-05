package com.domed.util;

import com.domed.core.query.support.IPageList;
import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class PageTool {

    /**
      * @Description: 分页信息
    　* @param
    　* @return
    　* @throws
    　* @author syl
    　* @date 2020/8/24 9:01
    　*/
    public static PageInfo getListPageInfo(Integer pageIndex, Integer pageSize, List list){
        PageInfo pageInfo = new PageInfo();
        Integer currentPageNum = pageIndex<=1  ? 1 :pageIndex;
        Integer pageShowNum = pageSize <=0 ? list.size() : pageSize;
        Integer totalPageNum = (list.size() % pageShowNum != 0) ? (list.size()/pageShowNum + 1): list.size()/pageShowNum;
        pageInfo.setPageNum(currentPageNum);
        pageInfo.setPageSize(pageShowNum);
        pageInfo.setTotal(list.size());
        pageInfo.setPages(totalPageNum);
        return pageInfo;
    }

    /**
      * @Description: 分页数据
    　* @param
    　* @return
    　* @throws
    　* @author syl
    　* @date 2020/8/24 9:02
    　*/
    public static List dataPaging(PageInfo pageInfo, List list){
        Integer totalPageNum = pageInfo.getPages();
        Integer pageShowNum = pageInfo.getPageSize();
        Integer currentPageNum = pageInfo.getPageNum();
        currentPageNum = currentPageNum > totalPageNum ? totalPageNum : currentPageNum;
        Integer startIndex = (currentPageNum - 1) * pageShowNum;
        startIndex = startIndex <= 0 ? 0 : startIndex;
        Integer endIndex =  currentPageNum*pageShowNum > list.size()? list.size() : currentPageNum*pageShowNum;
        // 数据分页
        list = list.subList(startIndex, endIndex);
        pageInfo.setList(list);
        return list;
    }

    public static void savePageInfo2ResponseBody(PageInfo pageInfo, Map result){
        result.put("data",pageInfo.getList()==null?new ArrayList():pageInfo.getList());
        result.put("pageCount",pageInfo.getTotal());
        result.put("totalPage",pageInfo.getPages());
    }
}

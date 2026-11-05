package com.domed.api.bean;

import java.util.List;
import java.util.Map;

public class PageModel<T> {

    private int totalRecords = 0;// 总记录数

    private List<T> list;// 结果集

    private List<Map<String,Object>> listMap;

    private int pageNumber;// 页码，第几页

    private int pageSize;// 每页多少条

    private T a;// 泛型类

    private int totalpages;// 总页数

    private int fromIndex;// 从哪条开始
    private int toIndex;// 到哪条结束

    public List<Map<String, Object>> getListMap() {
        return listMap;
    }

    public void setListMap(List<Map<String, Object>> listMap) {
        this.listMap = listMap;
    }

    // 得到首页(可选择使用，也可以不适用)
    public int getTopPageNumber() {
        return 1;
    }

    // 上一页(可选择使用，也可以不适用)
    public int getPreviousPageNumber() {
        if (pageNumber <= 1) {
            return 1;
        }
        return pageNumber - 1;
    }

    // 下一页(可选择使用，也可以不适用)
    public int getNextPage() {
        if (pageNumber >= this.getTotalpages()) {
            return this.getTotalpages() == 1 ? 1 : this.getTotalpages();
        }
        return pageNumber + 1;
    }

    public int getTotalpages() {
        return totalpages;
    }

    // 计算总页数
    public void setTotalpages() {
        int totalPage = totalRecords / pageSize;
        int k = totalRecords % pageSize;
        if (k != 0) {
            totalPage = totalPage + 1;
        }
        this.totalpages = totalPage;
    }

    public int getLastPage() {
        return this.getTotalpages() == 1 ? 1 : getTotalpages();
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    // 计算从哪条数据开始
    public int getFromIndex() {
        if (this.pageNumber == 1) {
            this.fromIndex = 0;
        }
        if (this.pageNumber > 1 && this.pageNumber <= this.getTotalpages()) {
            this.fromIndex = (pageNumber - 1) * 10;
        }
        return fromIndex;
    }

    public void setFromIndex(Integer fromIndex) {
        this.fromIndex = fromIndex;
    }

    public int getToIndex() {
        return toIndex;
    }

    public void setToIndex(int toIndex) {
        this.toIndex = toIndex;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    // 分情况得到当前页
    public void setPageNumber(int pageNum) {
        if (pageNum <= totalpages && pageNum > 0) {// 当前页正常情况
            this.pageNumber = pageNum;
        }
        if (pageNum > totalpages) {// 当前页比总页数大
            this.pageNumber = totalpages;
        }
        if (pageNum <= 0) {// 当页码小于或者不等于的非正常0
            this.pageNumber = 1;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setA(T a) {
        this.a = a;
    }

    public T getA() {
        return a;
    }

    // 试用于数字分页(最右侧最大值)
    public Integer getMaxPage() {
        if (this.getTotalpages() > 10 && pageNumber > this.getTotalpages() - 5) {
            return this.getTotalpages();
        } else if (this.getTotalpages() < 10) {
            return this.getTotalpages();
        } else if (pageNumber <= 5 && this.getTotalpages() > 10) {
            return 10;
        } else {
            return pageNumber + 5;
        }
    }

    // 试用于数字左侧最小值
    public Integer getMinPage() {
        if (this.getMaxPage() < 10) {
            return 1;
        }
        if (pageNumber < 5) {
            return 1;
        } else {
            return pageNumber - 4;
        }
    }

    // 计算从fromIndex后的多少条数据取出
    public int getNumIndex() {
        if (this.pageNumber == this.getTotalpages()) {// 最后一页的时候

            return this.getTotalRecords() % this.pageSize;
        } else {// 正常情况下
            return this.pageSize;
        }

    }

    private Map<String,String> conditionMap;

    public Map<String, String> getConditionMap() {
        return conditionMap;
    }

    public void setConditionMap(Map<String, String> conditionMap) {
        this.conditionMap = conditionMap;
    }
}

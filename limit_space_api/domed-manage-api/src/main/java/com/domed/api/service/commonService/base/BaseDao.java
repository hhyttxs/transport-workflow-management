package com.domed.api.service.commonService.base;

import com.domed.api.bean.PageModel;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 基础数据库操作类
 */
public interface BaseDao<T> {

    /**
     * 保存一个对象
     *
     * @param o 对象
     */
    public void save(T o);


    /**
     * 清除缓存 持久对象变成脱管对象
     */
    public void evict(T o);

    /**
     * 更新一个对象
     *
     * @param o 对象
     */
    public void update(T o);

    /**
     * 保存或更新对象
     *
     * @param o 对象
     */
    public void saveOrUpdate(T o);

    /**
     * 合并一个对象
     *
     * @param o 对象
     */
    public void merge(T o);

    /**
     * 删除一个对象
     *
     * @param o 对象
     */
    public void delete(T o);

    /**
     * 查找对象集合
     *
     * @param hql
     * @param param
     * @return List<T>
     */
    public List<T> find(String hql, Object... param);

    /**
     * 查找对象集合
     *
     * @param hql
     * @param param
     * @return List<T>
     */
    public List<T> find(String hql, List<Object> param);

    /**
     * 查找对象集合,带分页
     *
     * @param hql
     * @param page  当前页
     * @param rows  每页显示记录数
     * @param param
     * @return 分页后的List<T>
     */
    public List<T> find(String hql, int page, int rows, List<Object> param);

    /**
     * 查找对象集合,带分页
     *
     * @param hql
     * @param page  当前页
     * @param rows  每页显示记录数
     * @param param
     * @return 分页后的List<T>
     */
    public List<T> findPageByObj(String hql, int page, int rows, Object... param);

    /**
     * 获得一个对象
     *
     * @param c  对象类型
     * @param id
     * @return Object
     */
    public T get(Class<T> c, Serializable id);

    /**
     * 获得一个对象
     *
     * @param hql
     * @param param
     * @return Object
     */
    public T get(String hql, Object... param);

    /**
     * 获得一个对象
     *
     * @param hql
     * @param param
     * @return Object
     */
    public T getByObjList(String hql, List<Object> param);

    /**
     * select count(*) from 类
     *
     * @param hql
     * @param param
     * @return Long
     */
    public Long count(String hql, Object... param);


    /**
     * select count(*) from 类
     *
     * @param hql
     * @param param
     * @return Long
     */
    //public Integer countByJdbc(String sql, Object... param);

    /**
     * select count(*) from 类
     *
     * @param hql
     * @param param
     * @return Long
     */
    public Long count(String hql, List<Object> param);

    /**
     * 执行HQL语句
     *
     * @param hql
     * @return 相应数目
     */
    public Integer executeHql(String hql);

    /**
     * 执行HQL语句
     *
     * @param hql
     * @param param
     * @return
     */
    public Integer executeHql(String hql, Object... param);

    /**
     * 执行HQL语句
     *
     * @param hql
     * @param param
     * @return
     */
    public Integer executeHql(String hql, List<Object> param);

    /**
     * 鎵ц鍘熺敓sql(澧烇紝鍒狅紝鏀
     *
     * @param sql
     * @return
     */
    public Integer excuteBySql(String sql, Object... param);

    /**
     * 鎵ц鍘熺敓sql(鏌
     *
     * @param sql
     * @return
     */
    public List excuteBySqlForFind(String sql, Object... param);

    public List excuteBySqlForFindListParam(String sql, List<Object> param);


    /**
     * 鍒嗛〉鏌ヨ鏂规硶
     *
     * @param sql
     * @param c
     * @param page
     * @param rows
     * @return
     */
    public List excuteBySqlForFindPage(String sql, int page, int rows, Object... param);

    public List<T> excuteBySqlForFindEntity(String sql, Class<T> c, Object... param);

    /*
     * @Description 根据sql 查询非数据库实体对象列表
     * @Param sql 数据库sql语句 （查询列必须与实体对象列名一直）
     * @Param c 实体类反射对象 实体类内部属性必须都是 String 类型
     * @Param param 查询参数
     * @Return java.util.List<T> 查询的实体对象
     * @Author LiRong
     * @Date 2024/3/21 12:33
     **/
    public List<T> excuteBySqlForFindObj(String sql, Class<T> c, Object... param);

    public List<T> excuteBySqlForFindPageEntity(String sql, Class<T> c, int page, int rows, Object... param);

    public BigDecimal excuteBySqlCount(String sql, Object... param);

    public List excuteListObjBySqlForFindPage(String sql, int page, int rows, List<Object> param);

    public List<T> excuteListObjBySqlForFindPageEntity(String sql, Class<T> c, int page, int rows, List<Object> param);

    public BigDecimal excuteBySqlCount(String sql, List<Object> param);

    public PageModel<T> doList(String sql, int page, int rows, List<Object> param, PageModel<T> pagemodel);

}

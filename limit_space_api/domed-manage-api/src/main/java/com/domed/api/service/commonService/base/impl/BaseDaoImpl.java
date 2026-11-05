package com.domed.api.service.commonService.base.impl;

import com.domed.api.bean.PageModel;
import com.domed.api.service.commonService.base.BaseDao;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManagerFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;


/**
 * 基础DAO
 */
@Repository("baseDao")
public class BaseDaoImpl<T> implements BaseDao<T> {

    @PersistenceContext
    private EntityManager entityManager;

    public Session getCurrentSession() {
        Session session = entityManager.unwrap(Session.class);
        return session;
    }

    public void save(T o) {
        this.getCurrentSession().save(o);
    }

    public void update(T o) {
        this.getCurrentSession().update(o);
    }

    public void saveOrUpdate(T o) {
        this.getCurrentSession().saveOrUpdate(o);
    }

    public void merge(T o) {
        this.getCurrentSession().merge(o);
    }

    public void evict(T o) {
        getCurrentSession().evict(o);
    }


    public void delete(T o) {
        this.getCurrentSession().delete(o);
    }

    public List<T> find(String hql, List<Object> param) {
        Query q = this.getCurrentSession().createQuery(hql);
        if (param != null && param.size() > 0) {
            for (Integer i = 0; i < param.size(); i++) {
                q.setParameter(i.toString(), param.get(i));
            }
        }
        return q.list();
    }

    public List<T> find(String hql, Object... param) {
        Query q = this.getCurrentSession().createQuery(hql);
        if (param != null && param.length > 0) {
            for (Integer i = 0; i < param.length; i++) {
                q.setParameter(i.toString(), param[i]);
            }
        }
        return q.list();
    }

    public List<T> find(String hql, int page, int rows, List<Object> param) {
        Query q = this.getCurrentSession().createQuery(hql);
        if (param != null && param.size() > 0) {
            for (Integer i = 0; i < param.size(); i++) {
                q.setParameter(i.toString(), param.get(i));
            }
        }
        return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
    }

    public List<T> findPageByObj(String hql, int page, int rows, Object... param) {
        Query q = this.getCurrentSession().createQuery(hql);
        if (param != null && param.length > 0) {
            for (Integer i = 0; i < param.length; i++) {
                q.setParameter(i.toString(), param[i]);
            }
        }
        return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
    }

    public T get(Class<T> c, Serializable id) {
        return (T) this.getCurrentSession().get(c, id);
    }

    public T get(String hql, Object... param) {
        List l = this.find(hql, param);
        if (l != null && l.size() > 0) {
            return (T) l.get(0);
        }
        return null;
    }

    public T getByObjList(String hql, List<Object> param) {
        List l = this.find(hql, param);
        if (l != null && l.size() > 0) {
            return (T) l.get(0);
        }
        return null;
    }

    public Long count(String hql, Object... param) {
        Query q = this.getCurrentSession().createQuery(hql);
        if (param != null && param.length > 0) {
            for (Integer i = 0; i < param.length; i++) {
                q.setParameter(i.toString(), param[i]);
            }
        }
        return (Long) q.uniqueResult();
    }

    public Long count(String hql, List<Object> param) {
        Query q = this.getCurrentSession().createQuery(hql);
        if (param != null && param.size() > 0) {
            for (Integer i = 0; i < param.size(); i++) {
                q.setParameter(i.toString(), param.get(i));
            }
        }
        return (Long) q.uniqueResult();
    }

    public Integer executeHql(String hql) {
        Query q = this.getCurrentSession().createQuery(hql);
        return q.executeUpdate();
    }

    public Integer executeHql(String hql, Object... param) {
        Query q = this.getCurrentSession().createQuery(hql);
        if (param != null && param.length > 0) {
            for (Integer i = 0; i < param.length; i++) {
                q.setParameter(i.toString(), param[i]);
            }
        }
        return q.executeUpdate();
    }

    public Integer executeHql(String hql, List<Object> param) {
        Query q = this.getCurrentSession().createQuery(hql);
        if (param != null && param.size() > 0) {
            for (Integer i = 0; i < param.size(); i++) {
                q.setParameter(i.toString(), param.get(i));
            }
        }
        return q.executeUpdate();
    }

    /**
     * 鎵ц鍘熺敓sql,(澧烇紝鍒狅紝鏀
     */
    @Override
    public Integer excuteBySql(String sql, Object... param) {
        SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
        if (param != null && param.length > 0) {
            for (Integer i = 0; i < param.length; i++) {
                query.setParameter(i.toString(), param[i]);
            }
        }
        return (Integer) query.executeUpdate();
    }

    /**
     * 鎵ц鍘熺敓sql(鏌
     *
     * @param sql
     * @return
     */
    @Override
    public List excuteBySqlForFind(String sql, Object... param) {
        SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
        if (param != null && param.length > 0) {
            for (Integer i = 0; i < param.length; i++) {
                query.setParameter(i.toString(), param[i]);
            }
        }
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List list = query.list();
        return list;
    }

    @Override
    public List excuteBySqlForFindListParam(String sql, List<Object> param) {
        SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
        if (param != null && param.size() > 0) {
            for (Integer i = 0; i < param.size(); i++) {
                query.setParameter(i.toString(), param.get(i));
            }
        }
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List list = query.list();
        return list;
    }

    /**
     * 鎵ц鍘熺敓sql(鍒嗛〉鏌ヨ)
     *
     * @param sql
     * @return
     */
    @Override
    public List excuteBySqlForFindPage(String sql, int page, int rows, Object... param) {
        SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
        if (param != null && param.length > 0) {
            for (Integer i = 0; i < param.length; i++) {
                query.setParameter(i.toString(), param[i]);
            }
        }
        query.setFirstResult((page - 1) * rows).setMaxResults(rows);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        //	query.addEntity(c);
        List list = query.list();
        return list;
    }

    @Override
    public List<T> excuteBySqlForFindEntity(String sql, Class<T> c,
                                            Object... param) {
        // TODO Auto-generated method stub
        SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
        if (param != null && param.length > 0) {
            for (Integer i = 0; i < param.length; i++) {
                query.setParameter(i.toString(), param[i]);
            }
        }
        query.addEntity(c);
        List<T> list = query.list();
        return list;
    }

    @Override
    public List<T> excuteBySqlForFindObj(String sql, Class<T> c, Object... param) {
        // TODO Auto-generated method stub
        SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
        if (param != null && param.length > 0) {
            for (Integer i = 0; i < param.length; i++) {
                query.setParameter(i.toString(), param[i]);
            }
        }
        Field[] declaredFields = c.getDeclaredFields();
        for (Field field : declaredFields) {
            String name = field.getName();
            query.addScalar(name, StandardBasicTypes.STRING);
        }
        query.setResultTransformer(Transformers.aliasToBean(c));
        List<T> list = query.list();
        return list;
    }

    @Override
    public List<T> excuteBySqlForFindPageEntity(String sql, Class<T> c,
                                                int page, int rows, Object... param) {
        // TODO Auto-generated method stub
        SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
        if (param != null && param.length > 0) {
            for (Integer i = 0; i < param.length; i++) {
                query.setParameter(i.toString(), param[i]);
            }
        }
        query.setFirstResult((page - 1) * rows).setMaxResults(rows);
        //query.setResultTransformer(Transformers.aliasToBean(c));
        query.addEntity(c);
        List<T> list = query.list();
        return list;
    }

    public BigDecimal excuteBySqlCount(String sql, Object... param) {
        SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
        if (param != null && param.length > 0) {
            for (Integer i = 0; i < param.length; i++) {
                query.setParameter(i.toString(), param[i]);
            }
        }
        return (BigDecimal) query.uniqueResult();
    }

    public List excuteListObjBySqlForFindPage(String sql, int page, int rows, List<Object> param) {
        SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
        if (param != null && param.size() > 0) {
            for (Integer i = 0; i < param.size(); i++) {
                query.setParameter(i.toString(), param.get(i));
            }
        }
        query.setFirstResult((page - 1) * rows).setMaxResults(rows);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        //	query.addEntity(c);
        List list = query.list();
        return list;
    }

    @Override
    public List<T> excuteListObjBySqlForFindPageEntity(String sql, Class<T> c,
                                                       int page, int rows, List<Object> param) {
        // TODO Auto-generated method stub
        SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
        if (param != null && param.size() > 0) {
            for (Integer i = 0; i < param.size(); i++) {
                query.setParameter(i.toString(), param.get(i));
            }
        }
        query.setFirstResult((page - 1) * rows).setMaxResults(rows);
        //query.setResultTransformer(Transformers.aliasToBean(c));
        query.addEntity(c);
        List<T> list = query.list();
        return list;
    }

    public BigDecimal excuteBySqlCount(String sql, List<Object> param) {
        SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
        if (param != null && param.size() > 0) {
            for (Integer i = 0; i < param.size(); i++) {
                query.setParameter(i.toString(), param.get(i));
            }
        }
        BigDecimal bigDecimal = new BigDecimal(query.uniqueResult().toString());
        return bigDecimal;
    }

    /**
     * 分页封装的方法
     *
     * @param sql
     * @param page
     * @param rows
     * @param param
     * @return
     */
    @Override
    public PageModel<T> doList(String sql, int page, int rows, List<Object> param, PageModel<T> pagemodel) {
        SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
        if (param != null && param.size() > 0) {
            for (Integer i = 0; i < param.size(); i++) {
                query.setParameter(i.toString(), param.get(i));
            }
        }
        if (rows != 1) {
            if (rows == 0) rows = 1;//特殊处理 交通局网页分页 TODO 20210624 lirong
            query.setFirstResult((page - 1) * rows).setMaxResults(rows);
        }
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        //	query.addEntity(c);
        List list = query.list();

        sql = "select count(*) from (" + sql + ")";

        BigDecimal count1 = excuteBySqlCount(sql, param);

        //封装实体
        pagemodel.setListMap(list);
        pagemodel.setTotalRecords(count1.intValue());
        pagemodel.setPageSize(rows);
        pagemodel.setTotalpages();
        pagemodel.setPageNumber(page);
        return pagemodel;
    }

}

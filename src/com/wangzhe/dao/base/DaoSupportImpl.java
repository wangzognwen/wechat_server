package com.wangzhe.dao.base;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.persistence.Table;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.wangzhe.util.Paging;

public class DaoSupportImpl<T> implements DaoSupport<T>{
	protected Class<?> clazz ;
	protected Logger logger;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public DaoSupportImpl() {
		this.clazz = this.getSuperClassGenricType();
		this.logger = Logger.getLogger(clazz);
	}
	
	@SuppressWarnings("unchecked")
	public T getObjById(int id) {
		return (T) sessionFactory.getCurrentSession().get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		return sessionFactory.getCurrentSession().createQuery("from " + clazz.getSimpleName()).list();
	}
	
	@SuppressWarnings("unchecked")
	public T getTByParams(T t) {
		return (T) sessionFactory.getCurrentSession().createCriteria(clazz).add(Example.create(t)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<T> getAllByParams(T t) {
		return sessionFactory.getCurrentSession().createCriteria(clazz).add(Example.create(t)).list();
	}
	
	public T getTByParams(Map<String, Object> params) {
		List<T> results = getAllByParams(params);
		if(results != null && results.size() > 0){
			return results.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<T> getAllByParams(Map<String, Object> params) {
		Criteria criteria = currentSession().createCriteria(clazz);
		if(params != null){
			for(String key : params.keySet()){
				if(key.equals("modifyDate")){
					criteria.addOrder(Order.asc("modifyDate"))
						.add(Restrictions.gt("modifyDate", params.get(key)));
				}else {
					criteria.add(Restrictions.eq(key, params.get(key)));
				}
			}
		}
		return criteria.list();
	}

	public void updateObj(T t) {
		sessionFactory.getCurrentSession().update(t);
	}

	public boolean addObj(T t) {
		try {
			currentSession().save(t);
			return true;
		} catch (Exception e) {		
			logger.error(e.getMessage());
			return false;
		}
	}

	public void deleteObj(int id) {
		try {
			T t = this.getObjById(id);
			sessionFactory.getCurrentSession().delete(t);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
	}

	@SuppressWarnings("unchecked")
	public List<T> getObjByPaging(Paging<T> paging) {
		this.clazz = getSuperClassGenricType();
		Query query = sessionFactory.getCurrentSession().createQuery("from " + clazz.getSimpleName());
		query.setFirstResult((paging.getNowPage() - 1) * paging.getPageSize());
		query.setMaxResults(paging.getPageSize());
		return query.list();
	}

	public Class<?> getSuperClassGenricType() {
		ParameterizedType pt=	(ParameterizedType) this.getClass().getGenericSuperclass();
		Class<?> clazz = (Class<?>) pt.getActualTypeArguments()[0];
		return clazz;
	}

	public final Session currentSession() {
		return sessionFactory.getCurrentSession();
	}
	
}

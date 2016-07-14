package com.wangzhe.dao;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.wangzhe.bean.UserBean;
import com.wangzhe.dao.base.DaoSupportImpl;


@Repository
public class UserDao extends DaoSupportImpl<UserBean> {
	@SuppressWarnings("unchecked")
	public List<UserBean> searchUser(String propName,String value){
		List<UserBean> list = new ArrayList<UserBean>();
		list = currentSession().createCriteria(UserBean.class).add(Restrictions.like(propName,value, MatchMode.ANYWHERE)).list();
		return list;
	}
	
	public boolean isUserExist(String userName){
		Criteria criteria = currentSession().createCriteria(UserBean.class).add(Restrictions.eq(UserBean.USERNAME, userName));
		if(criteria.list().size()>0){
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserBean> searchUserByM(String search){
		if(search == null || "".equals(search)){
			return null;
		}
		Criteria criteria = currentSession().createCriteria(UserBean.class).
			add(Restrictions.or(Restrictions.ilike("loginname", search,MatchMode.ANYWHERE), 
					Restrictions.ilike("useremail", search,MatchMode.ANYWHERE)));
			
		return criteria.list();
	}
	
	public UserBean getUserByUName(String uname){
		Criteria criteria = currentSession().createCriteria(UserBean.class).add(Restrictions.eq("loginname", uname));
		return (UserBean) criteria.uniqueResult();
	}
	
	public void updateUser(String userName, String field, Object value){
		Query query = currentSession().createQuery("update UserBean set "
				+ field + " = \'" + value + "\', modifyDate = " + System.currentTimeMillis()
				+ " where userName = \'" + userName + "\'");
		query.executeUpdate();
	}
	
}

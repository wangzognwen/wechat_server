package com.wangzhe.service;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wangzhe.bean.UserBean;
import com.wangzhe.dao.UserDao;
import com.wangzhe.util.Paging;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao userDao;
		
	public List<UserBean> getAllUser() {
		// TODO Auto-generated method stub
		return userDao.getAll();
	}
	
	public UserBean getUserById(int userid) {
		// TODO Auto-generated method stub
		return userDao.getObjById(userid);
	}
	
	public List<UserBean> getUsersByPaging(Paging paging) {
		// TODO Auto-generated method stub
		return userDao.getObjByPaging(paging);
	}
	
	@Transactional
	public void updateUser(UserBean user) {
		userDao.updateObj(user);	
	}
	
	@Transactional
	public void deleteUser(int userid) {
		userDao.deleteObj(userid);
	}

	@Transactional
	public List<UserBean> searchUser(String propName, String value) {
		return userDao.searchUser(propName, value);
	}

	public List<UserBean> exportUser(String[] ids) {
		List<UserBean> list = new ArrayList<UserBean>();
		for(int i=0; i<ids.length; i++){
			int id = Integer.parseInt(ids[i]);
			UserBean user = userDao.getObjById(id);
			list.add(user);
		}
		return list;
	}

	@Transactional
	public void bulkDelUser(String[] ids) {
		for(int i=0; i<ids.length; i++){
			int id = Integer.parseInt(ids[i]);
			userDao.deleteObj(id);
		}
	}

	public boolean isUserExist(String loginname) {
		return userDao.isUserExist(loginname);
	}
	
	@Transactional
	public String addUser(UserBean user) {
		if(this.isUserExist(user.getUserName())){
			return "fail";
		}
		userDao.addObj(user);
		return "succ";
		
	}
	
	@Transactional
	public UserBean getUserByParams(UserBean user) {
		return userDao.getTByParams(user);
	}

	public List<UserBean> getAllUserByParams(UserBean user) {
		return userDao.getAllByParams(user);
	}

	public List<UserBean> searchUserByM(String search,int ownerid) {
	
		return null;
	}


	public String getPhotoByUName(String uname) {
		UserBean user = userDao.getUserByUName(uname);
		if(user == null){
			return null;
		}
		return user.getHeadUrl();
	}
	
	@Transactional
	public UserBean updateUser(String userName, String field, Object value) {
		userDao.updateUser(userName, field, value);
		UserBean userBean = new UserBean();
		userBean.setUserName(userName);
		userBean = getUserByParams(userBean);
		return userBean;
	}

	@Transactional
	public List<UserBean> getUpdatedData(String userName,
			long modifyDate) {
		return userDao.getMyFriendsByModifyDate(userName, modifyDate);
	}

	

	
	
	

	
	

}

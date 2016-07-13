package com.wangzhe.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wangzhe.bean.FriendBean;
import com.wangzhe.dao.FriendDao;

@Service
public class FriendServiceImpl implements FriendService{
	@Autowired
	private FriendDao friendDao;

	@Transactional
	public List<FriendBean> getFriendsByOwnerName(String ownerName, long modifyDate) {
		Map<String, Object> params= new HashMap<String, Object>();
		params.put(FriendBean.OWNER_NAME, ownerName);
		params.put(FriendBean.MODIFY_DATE, modifyDate);
		return friendDao.getAllByParams(params);
	}

}

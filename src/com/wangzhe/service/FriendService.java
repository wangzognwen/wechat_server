package com.wangzhe.service;

import java.util.List;

import com.wangzhe.bean.FriendBean;
import com.wangzhe.bean.UserBean;
import com.wangzhe.util.Paging;

public interface FriendService {
	public List<FriendBean> getFriendsByOwnerName(String ownerName, long lastModifyDate);
}

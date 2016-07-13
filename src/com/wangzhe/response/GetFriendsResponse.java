package com.wangzhe.response;

import java.util.List;

import com.wangzhe.bean.FriendBean;

public class GetFriendsResponse extends BaseResponse{
	private List<FriendBean> friendBeans;

	public GetFriendsResponse(int code, String msg, List<FriendBean> friendBeans) {
		super(code, msg);
		this.friendBeans = friendBeans;
	}

}

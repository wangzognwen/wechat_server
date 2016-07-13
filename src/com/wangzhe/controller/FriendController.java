package com.wangzhe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.wangzhe.bean.FriendBean;
import com.wangzhe.response.GetFriendsResponse;
import com.wangzhe.service.FriendService;

@Controller
@EnableWebMvc
public class FriendController extends BaseController{
	
	@Autowired
	private FriendService friendService;
	
	@RequestMapping(value="/getMyFriends")
	public @ResponseBody GetFriendsResponse getFriends(@RequestParam(value = "ownerName", required = false) String ownerName, 
			@RequestParam(value = "modifyDate", required = false) Long modifyDate){
		GetFriendsResponse response = null;
		if(ownerName == null || "".equals(modifyDate)){
			response = new GetFriendsResponse(1, "param_is_invalid", null);
		}else {
			long lastModifyDate = 0;
			try{
				lastModifyDate = modifyDate.longValue();
			}catch(Exception e){}
			List<FriendBean> friendBeans = friendService.getFriendsByOwnerName(ownerName, lastModifyDate);
			response = new GetFriendsResponse(0, "success", friendBeans);
		}
		return response;
	}
}
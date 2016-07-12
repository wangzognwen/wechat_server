package com.wangzhe.service;

import org.springframework.stereotype.Service;

import com.wangzhe.bean.UserBean;
import com.wangzhe.response.TokenResponse;

public interface TokenService {
	public String newToken(String userName);
	
	public TokenResponse checkToken(String token);
}

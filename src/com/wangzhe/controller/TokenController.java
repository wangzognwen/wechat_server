package com.wangzhe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangzhe.response.TokenResponse;
import com.wangzhe.service.TokenService;
import com.wangzhe.service.TokenServiceImpl;

@Controller
public class TokenController {
	@Autowired
	private TokenService tokenService;

	@RequestMapping(path = "/newToken")
	public @ResponseBody TokenResponse refreshToken(@RequestParam("token") String oldToken){
		TokenResponse tokenResponse = tokenService.refreshToken(oldToken);
		return tokenResponse;
	}
}

package com.wangzhe.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.validation.Valid;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.wangzhe.bean.UserBean;
import com.wangzhe.net.Xmpp;
import com.wangzhe.response.BaseResponse;
import com.wangzhe.response.LoginResponse;
import com.wangzhe.response.RegisterResponse;
import com.wangzhe.response.UpdateUserResponse;
import com.wangzhe.service.TokenService;
import com.wangzhe.service.UserService;
import com.wangzhe.util.keyUtil;


@Controller
@EnableWebMvc
public class UserController extends BaseController{
	@Autowired
	private UserService userService;
	@Autowired
	private Xmpp xmpp;
	@Autowired
	private TokenService tokenService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody LoginResponse login(@Valid @ModelAttribute("user") UserBean userBean, BindingResult bindingResult){
		LoginResponse loginResponse = null;
		if(bindingResult.hasErrors()){
			loginResponse = new LoginResponse(1, "userName or passWord is not right", null);
			return loginResponse;
		}
		userBean = getUser(userBean);
		if(userBean != null){
			String token = tokenService.newToken(userBean.getUserName());
			loginResponse = new LoginResponse(0, "success", token, userBean);
		}else{
			loginResponse = new LoginResponse(1, "userName or passWord is not right", null);
		}
		return loginResponse;
	}
	
	@RequestMapping(value="/addUser", method=RequestMethod.POST)
	public @ResponseBody RegisterResponse addUser(@Valid @ModelAttribute("user") UserBean userBean, BindingResult bindingResult){
		RegisterResponse response = null;
		if(bindingResult.hasErrors()){
			FieldError fieldError = bindingResult.getFieldError();
			String errFiled = fieldError.getField();
			response = new RegisterResponse(1, fieldError.getCode(), errFiled);
			return response;
		}
		String result = userService.addUser(userBean);
		if(result.equals("succ")){
			xmpp.register(userBean.getUserName(), userBean.getPassWord());
			response = new RegisterResponse(0, "success", null);
		}else{
			response = new RegisterResponse(2, "user_already_existed", null);
		}
		return response;
	}
	
	@RequestMapping(value="/updateUser", method=RequestMethod.POST)
	public @ResponseBody UpdateUserResponse updateUser(@RequestParam("userName") String userName, 
			@RequestParam("field") String field, @RequestParam("value") Object value){
		UpdateUserResponse response = null;
		boolean canUpdate = true;
		if(field.equals("") || field.equals(UserBean.USERNAME) || field.equals(UserBean.ID)
				|| field.equals(UserBean.CREATEDATE) || field.equals(UserBean.MODIFYDATE)){
			response = new UpdateUserResponse(1, "refuse_modify", null);
			canUpdate = false;
		}else if(field.equals(UserBean.PASSWORD)){
			String passWord = (String) value;
			if(passWord.length() < 6 || passWord.length() > 16){
				response = new UpdateUserResponse(2, "modified_value_invalid", null);
				canUpdate = false;
			}
		}
		
		if(canUpdate){
			UserBean userBean = userService.updateUser(userName, field, value);
			response = new UpdateUserResponse(0, "success", userBean);
		}
		
		return response;
	}
	
	private UserBean getUser(UserBean userBean){
		userBean = userService.getUserByParams(userBean);
		return userBean;
	} 
}

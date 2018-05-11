package com.clubank.position.controller.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.clubank.position.common.response.ResponseJson;
import com.clubank.position.manager.customer.service.CustomerUserService;

/**
 * 登录、注销
 * @author Liangwl
 *
 */
@Controller
public class LoginController {

	@Autowired
	private CustomerUserService customerUserService;
	
	/**
	 * 用户登录
	 * @param param
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/login" , method = RequestMethod.POST)
	@ResponseBody
	public ResponseJson userLogin(@RequestBody JSONObject param, HttpSession session){
		
		return customerUserService.userLogin(param.getString("account"), param.getString("password"));
	}
	
	/**
	 * 用户注销
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logout" , method = RequestMethod.POST)
	@ResponseBody
	public ResponseJson userExit(HttpServletRequest request){
		
		return customerUserService.userLogout(request.getHeader("token"));
	}
}

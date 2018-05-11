package com.clubank.position.manager.customer.service;

import com.clubank.position.common.response.ResponseJson;

public interface CustomerUserService {

	/**
	 * 用户登录
	 * @param account 登录账号
	 * @param password 登录密码
	 * @return
	 */
	ResponseJson userLogin(String account, String password);
	
	/**
	 * 用户注销
	 * @param token
	 * @return
	 */
	ResponseJson userLogout(String token);
}

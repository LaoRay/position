package com.clubank.position.manager.customer.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.clubank.position.common.redis.JedisClient;
import com.clubank.position.common.redis.RedisKeyCode;
import com.clubank.position.common.response.ResponseJson;
import com.clubank.position.common.response.ResultCode;
import com.clubank.position.common.util.DESUtils;
import com.clubank.position.common.util.ResponseUtil;
import com.clubank.position.common.util.StringUtil;
import com.clubank.position.manager.customer.entity.Customer;
import com.clubank.position.manager.customer.entity.CustomerUser;
import com.clubank.position.manager.customer.mapper.CustomerMapper;
import com.clubank.position.manager.customer.mapper.CustomerUserMapper;
import com.clubank.position.manager.customer.service.CustomerUserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerUserServiceImpl implements CustomerUserService {

	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private CustomerUserMapper customerUserMapper;
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${token.expire}")
	private Integer token_expire;
	
	@Override
	public ResponseJson userLogin(String account, String password) {
		log.info("登录账号：account：{}，登录时间：loginDate：{}", account, new Date());
		CustomerUser user = customerUserMapper.selectByAccount(account);
		if(user == null){
			return ResponseUtil.buildJson(ResultCode.DATA_NOT_FOUND, "账号不存在");
		}
		if(user.getStatus() == 0){// 状态：0-无效 1-有效
			return ResponseUtil.buildJson(ResultCode.FAIL, "账号已无效");
		}
		
		Customer customer = customerMapper.selectByPrimaryKey(user.getCustomerId());
		if(customer == null){
			return ResponseUtil.buildJson(ResultCode.FAIL, "此用户不属于任何客户方");
		}
		
		//判断用户有无权限
		if(user.getRole() == null){
			return ResponseUtil.buildJson(ResultCode.DATA_NOT_FOUND, "您没有访问权限");
		}
		//判断密码是否正确
		if(!DESUtils.posEncode(password).equals(user.getPassword())){
			return ResponseUtil.buildJson(ResultCode.ACCOUNT_ERROR, "密码错误");
		}
		String token = UUID.randomUUID().toString();
		//session中保存的内容
		JSONObject session = new JSONObject();
		session.put("userAccount", user.getAccount());//用户账号
		session.put("userName", user.getName());//用户名称
		// 将登录的session保存在redis中
		jedisClient.set(RedisKeyCode.LOGIN_TOKEN.getValue() + token, session.toString());
		jedisClient.expire(RedisKeyCode.LOGIN_TOKEN.getValue() + token, token_expire); // 设置过期时间(秒)
		
		Map<String, Object> map = new HashMap<>();
		map.put("customerId", user.getCustomerId());
		map.put("token", token);
		map.put("role", user.getRole());
		return ResponseUtil.buildObjectJson(ResultCode.SUCC, map, "登录成功");
	}

	@Override
	public ResponseJson userLogout(String token) {
		log.info("用户注销：token：{}", token);
		if(StringUtil.isBlank(token)){
			return ResponseUtil.buildJson(ResultCode.PARAM_ERROR, "注销失败，token为空");
		}
		Long result = jedisClient.del(RedisKeyCode.LOGIN_TOKEN.getValue() + token);
		log.info("用户注销，删除redis的key【{}】，删除结果：{}", RedisKeyCode.LOGIN_TOKEN.getValue() + token, result);
		return ResponseUtil.buildJson(ResultCode.SUCC, "注销成功");
	}

}

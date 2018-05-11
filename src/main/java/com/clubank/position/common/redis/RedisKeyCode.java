package com.clubank.position.common.redis;

/**
 * redis中的key
 * 
 *
 */
public enum RedisKeyCode {
	
	/**
	 * 客户端登录的token
	 */
	LOGIN_TOKEN("token:login:"),
	
	/**
	 * 终端最新坐标
	 */
	GPS_LOCATION("GPS:");
	
	private String value; 
	
	private RedisKeyCode(String value){
		this.value = value;
	}
	
	public String getValue() {
        return value;
    }
	
}

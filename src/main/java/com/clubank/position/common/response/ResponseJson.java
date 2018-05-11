package com.clubank.position.common.response;

import com.alibaba.fastjson.JSON;

/**
 * 响应对象-基本
 * @author YangWei
 *
 */
public class ResponseJson {
	
	private final int code;
	
	private String msg;
	
	public ResponseJson(ResultCode code, String msg) {
		this.code = code.getValue();
		this.msg = msg;
	}
	
	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
	
	public String toJsonString() {
		return JSON.toJSONString(this);
	}
	
}

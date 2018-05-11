package com.clubank.position.common.response;

/**
 * 响应对象-对象
 * @author YangWei
 *
 */
public class ResponseObjectJson extends ResponseJson {
	
	private Object data;
	
	public ResponseObjectJson(ResultCode code, Object data, String msg) {
		super(code, msg);
		this.data = data;
	}

	public Object getData() {
		return data;
	}
	
}

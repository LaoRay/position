package com.clubank.position.common.response;

/**
 * 响应对象-id
 * @author YangWei
 *
 */
public class ResponseIdJson extends ResponseJson {
	
	private String id;
	
	public ResponseIdJson(ResultCode code, String id, String msg) {
		super(code, msg);
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
}

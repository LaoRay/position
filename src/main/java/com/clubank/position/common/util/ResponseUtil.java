package com.clubank.position.common.util;

import com.clubank.position.common.response.ResponseIdJson;
import com.clubank.position.common.response.ResponseJson;
import com.clubank.position.common.response.ResponseObjectJson;
import com.clubank.position.common.response.ResultCode;

/**
 * 响应对象util
 * 
 * @author YangWei
 *
 */
public class ResponseUtil {

	/**
	 * 构建默认的响应对象
	 * @param code
	 * @param msg
	 * @return
	 */
	public static ResponseJson buildJson(ResultCode code, String msg) {
		if (StringUtil.isBlank(msg)) {
			msg = "操作成功";
		}
		return new ResponseJson(code, msg);
	}
	
	/**
	 * 构建object响应对象
	 * @param code
	 * @param data
	 * @param msg
	 * @return
	 */
	public static ResponseObjectJson buildObjectJson(ResultCode code, Object data, String msg) {
		return new ResponseObjectJson(code, data, msg);
	}
	
	/**
	 * 构建object响应对象
	 * @param code
	 * @param data
	 * @return
	 */
	public static ResponseObjectJson buildObjectJson(ResultCode code, Object data) {
		return new ResponseObjectJson(code, data, "操作成功");
	}
	
	/**
	 * 构建id响应对象
	 * @param code
	 * @param id
	 * @param msg
	 * @return
	 */
	public static ResponseIdJson buildIdJson(ResultCode code, String id, String msg) {
		return new ResponseIdJson(code, id, msg);
	}
	
	/**
	 * 构建id响应对象
	 * @param code
	 * @param id
	 * @return
	 */
	public static ResponseIdJson buildIdJson(ResultCode code, String id) {
		return new ResponseIdJson(code, id, "操作成功");
	}
	
}

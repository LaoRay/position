package com.clubank.position.common.response;

/**
 * 响应结果代码
 * @author YangWei
 *
 */
public enum ResultCode {
	
	/**
	 * 成功
	 */
	SUCC(200),
	
	/**
	 * 失败
	 */
	FAIL(201),
	
	/**
	 * token过期
	 */
	TOKEN_INVALID(301),
	
	/**
	 * 用户名或密码错误
	 */
	ACCOUNT_ERROR(302),
	
	/**
	 * 帐号被禁用
	 */
	ACCOUNT_DISABLED(303),
	
	/**
	 * 参数长度太长
	 */
	PARAM_LENGTH(401),
	
	/**
	 * 参数错误
	 */
	PARAM_ERROR(402),
	
	/**
	 * 数量超过上限
	 */
	AMOUNT_LIMIT(403),
	
	/**
	 * 服务器内部错误
	 */
	SERVER_ERROR(500),
	
	/**
	 * 数据已存在
	 */
	DATA_EXIST(501),
	
	/**
	 * 数据不存在
	 */
	DATA_NOT_FOUND(502),
	
	/**
	 * 接口不存在
	 */
	INTER_NOT_FOUND(503),
	
	/**
	 * 未知
	 */
	UNKOWN(-999);
	
	private ResultCode(int value) {
		this.value = value;
	}
	
	public static boolean isSucc(String code) {
		return SUCC.getValueS().equals(code);
	}
	
	public int getValue() {
		return this.value;
	}
	
	public String getValueS() {
		return String.valueOf(this.value);
	}
	
	private int value;
}

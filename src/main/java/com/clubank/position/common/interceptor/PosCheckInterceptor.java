package com.clubank.position.common.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.clubank.position.common.redis.JedisClient;
import com.clubank.position.common.redis.RedisKeyCode;
import com.clubank.position.common.response.ResponseJson;
import com.clubank.position.common.response.ResultCode;
import com.clubank.position.common.util.ResponseUtil;
import com.clubank.position.common.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 拦截器
 * @author
 *
 */
@Slf4j
public class PosCheckInterceptor implements HandlerInterceptor {

	@Autowired
	private JedisClient jedisClient;
	
	@Value("${token.expire}")
	private Integer token_expire;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 请求的token
		String token = request.getHeader("token");
		String jsonString = jedisClient.get(RedisKeyCode.LOGIN_TOKEN.getValue() + token);
		if (StringUtil.isBlank(token) || StringUtil.isBlank(jsonString)) {
			log.info("您还未登录或登录已失效，请重新登录！");
			ResponseJson result = ResponseUtil.buildJson(ResultCode.TOKEN_INVALID, "您还未登录或登录已失效，请重新登录！");
			
			response.setContentType("application/json;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write(result.toJsonString());
			out.flush();
			out.close();
			return false;
		}
		jedisClient.expire(RedisKeyCode.LOGIN_TOKEN.getValue() + token, token_expire); // 重新设置过期时间(秒)
		return true;
	}

	public void postHandle(HttpServletRequest req, HttpServletResponse resp, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e)
			throws Exception {
		if (e != null) {
			// 控制台打印
			e.printStackTrace();
			// 记录log
			log.error("服务器内部错误", e);

			response.setContentType("application/json;charset=UTF-8");
			PrintWriter out = response.getWriter();
			ResponseJson result = ResponseUtil.buildJson(ResultCode.SERVER_ERROR, "服务器内部错误");
			out.write(result.toJsonString());
			out.flush();
			out.close();
		}
	}
}

package com.clubank.position.common.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 自定义过滤器
 * @author
 *
 */
public class PosFilter extends OncePerRequestFilter {
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 跨域请求处理
		if (request.getHeader("Origin") != null) {
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, customerId, token, role, websocket_name");
        }
        filterChain.doFilter(request, response);
	}
	
}

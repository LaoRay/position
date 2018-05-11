package com.clubank.position.manager.cart.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.clubank.position.common.response.ResponseJson;

public interface ImageService {

	/**
	 * 上传图片
	 * @param file 
	 * @return
	 */
	public ResponseJson uploadImage(MultipartFile file);
	
	/**
	 * 下载图片
	 * @param response
	 * @param path
	 */
	public void downImage(HttpServletResponse response, String path);
}

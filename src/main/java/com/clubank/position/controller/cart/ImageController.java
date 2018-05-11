package com.clubank.position.controller.cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.clubank.position.common.response.ResponseJson;
import com.clubank.position.manager.cart.service.ImageService;

/**
 * 图片处理
 * @author Liangwl
 *
 */
@Controller
public class ImageController {

	@Autowired
	private ImageService imageService;
	
	/**
	 * 图片上传
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/imageUpload", method = RequestMethod.POST)
	@ResponseBody
	public ResponseJson imageUpload(@RequestParam(value = "file") MultipartFile file) {
		return imageService.uploadImage(file);
	}
	
	/**
	 * 图片下载
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/image/car/{date}/{id}", method = RequestMethod.GET)
	public void imageDown(HttpServletRequest request, HttpServletResponse response) {
		imageService.downImage(response, request.getServletPath());
	}
}

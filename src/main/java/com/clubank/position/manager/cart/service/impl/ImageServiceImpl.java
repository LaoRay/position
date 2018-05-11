package com.clubank.position.manager.cart.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.util.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.clubank.position.common.response.ResponseJson;
import com.clubank.position.common.response.ResultCode;
import com.clubank.position.common.util.ResponseUtil;
import com.clubank.position.manager.cart.service.ImageService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

	@Value("${IMAGE_ROOT_PATH}")
	private String imageRootPath;
	
	@Override
	public ResponseJson uploadImage(MultipartFile file) {
		log.info("上传图片：{}", file.getName());
		try {
			StringBuffer path = new StringBuffer(imageRootPath); //根路径
			path.append("image/");
			path.append("car"); 
			path.append("/");
			path.append(DateUtil.formatDate(new Date(), "yyyyMMdd")); //日期
			path.append("/");
			path.append(UUID.randomUUID().toString()); // 生成的新文件名：uuid
			
			return uploadImage(file, path.toString());
		} catch (Exception e) {
			log.error("服务器内部错误，上传图片失败", e);
			return ResponseUtil.buildJson(ResultCode.SERVER_ERROR, "服务器内部错误，上传图片失败！");
		}
	
	}
	
	/**
	 * 保存文件
	 * @param file
	 * @param path
	 * @return
	 * @throws Exception
	 */
	private ResponseJson uploadImage(MultipartFile file, String path) throws Exception {
		if (file == null) {
			return ResponseUtil.buildJson(ResultCode.PARAM_ERROR, "上传文件不能为空");
		}
		String fileName = file.getOriginalFilename();// 获得文件名
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();// 获得文件后缀
		
		// 判断上传的文件是否为图片
		if (!suffix.matches("^[(jpg)|(bmp)|(png)|(gif)]+$")) {
			return ResponseUtil.buildJson(ResultCode.PARAM_ERROR, "请上传PNG、JPG、GIF、BMP格式的图片文件");
		}
		String fullPath = path + "." + suffix; //全部路径
		File dir = new File(fullPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// MultipartFile自带的解析方法 转存文件
		file.transferTo(dir);
		log.info("图片上传成功：{}", fullPath);
		return ResponseUtil.buildObjectJson(ResultCode.SUCC, fullPath.replace(imageRootPath, ""), "图片上传成功");
	}

	@Override
	public void downImage(HttpServletResponse response, String path) {
		BufferedInputStream bis = null;
        BufferedOutputStream out = null;
        log.info("下载图片：" + path);
		try {
        	//获取输入流 
			bis = new BufferedInputStream(new FileInputStream(new File(imageRootPath + path)));
	        //设置文件下载名  
	        String filename = path.substring(path.lastIndexOf("\\") + 1);  
	        //转码，免得文件名中文乱码  
	        filename = URLEncoder.encode(filename,"UTF-8");  
	        //设置文件下载头  
	        response.addHeader("Content-Disposition", "attachment;filename=" + filename);    
	        //设置文件ContentType类型，这样设置，会自动判断下载文件类型    
	        response.setContentType("multipart/form-data");   
	        out = new BufferedOutputStream(response.getOutputStream());  
	        int len = 0;  
	        while((len = bis.read()) != -1){  
	            out.write(len);  
	        }  
		} catch (Exception e) {
			log.error("系统没找到图片：" + path);
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (out != null) {
	            	out.close();
	            }
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}
}

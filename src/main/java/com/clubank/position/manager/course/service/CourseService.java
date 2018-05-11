package com.clubank.position.manager.course.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.clubank.position.common.response.ResponseJson;
import com.clubank.position.manager.course.entity.Course;

public interface CourseService {
	ResponseJson selectCourseById(Long id);
	ResponseJson insertCourse(Long customerId, Course course);
	ResponseJson editPartCourse(Long id, String name);
	ResponseJson editAllCourse(Long id, String borderLocations);
	ResponseJson courseImgSave(Long id, String fileLocation);
	ResponseJson selectCourseList(Long customerId);
	ResponseJson deleteCourse(Long id);
	
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
	
	ResponseJson getAllCourse(Long customerId);
}

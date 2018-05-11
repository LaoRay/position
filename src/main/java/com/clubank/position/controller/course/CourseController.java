package com.clubank.position.controller.course;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.clubank.position.common.response.ResponseJson;
import com.clubank.position.common.response.ResultCode;
import com.clubank.position.common.util.ResponseUtil;
import com.clubank.position.manager.course.entity.Course;
import com.clubank.position.manager.course.service.CourseService;


/**
 * 场地管理
 * @author wangtao1030
 *
 */
@Controller
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	
	/**
	 * 新增场地
	 * @param course
	 * @return
	 */
	@RequestMapping(value = "/course/save" , method = RequestMethod.POST)
	@ResponseBody
	public ResponseJson addOrEditCourse(HttpServletRequest request, @RequestBody Course course){
		if(request.getHeader("customerId") == null){
			return ResponseUtil.buildJson(ResultCode.PARAM_ERROR, "参数不能为空");
		}
		return courseService.insertCourse(Long.parseLong(request.getHeader("customerId")), course);
	}
	
	/**
	 * 图片上传
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/course/imageUpload", method = RequestMethod.POST)
	@ResponseBody
	public ResponseJson imageUpload(@RequestParam(value = "file") MultipartFile file) {
		return courseService.uploadImage(file);
	}
	
	/**
	 * 保存场地图片
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/course/courseImgSave", method = RequestMethod.POST)
	@ResponseBody
	public ResponseJson courseImgSave(@RequestBody JSONObject param) {
		return courseService.courseImgSave(param.getLong("id"),param.getString("courseImage"));
	}
	
	/**
	 * 图片下载
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/image/course/{date}/{id}", method = RequestMethod.GET)
	public void imageDown(HttpServletRequest request, HttpServletResponse response) {
		courseService.downImage(response, request.getServletPath());
	}
	
	
	/**
	 * 编辑分场（分场只可以修改名称）
	 * @param course
	 * @return
	 */
	@RequestMapping(value = "/course/edit" , method = RequestMethod.POST)
	@ResponseBody
	public ResponseJson editPartCourse(@RequestBody JSONObject param){
		
		return courseService.editPartCourse(param.getLong("id"),param.getString("name"));
	}
	
	/**
	 * 编辑全场（分场只可以修改范围）
	 * @param course
	 * @return
	 */
	@RequestMapping(value = "/course/editAll" , method = RequestMethod.POST)
	@ResponseBody
	public ResponseJson editAllCourse(@RequestBody JSONObject param){
		return courseService.editAllCourse(param.getLong("id"),param.getString("borderLocations"));
	}
	
	/**
	 * 查询全部分场信息
	 * @param app
	 * @return
	 */
	@RequestMapping(value = "/course/list" , method = RequestMethod.POST)
	@ResponseBody
	public ResponseJson getCourse(HttpServletRequest request){
		if(request.getHeader("customerId") == null){
			return ResponseUtil.buildJson(ResultCode.PARAM_ERROR, "参数不能为空");
		}
		return courseService.selectCourseList(Long.parseLong(request.getHeader("customerId")));
	}
	
	/**
	 * 根据id查询分场信息
	 * @param app
	 * @return
	 */
	@RequestMapping(value = "/course/getCourseById" , method = RequestMethod.POST)
	@ResponseBody
	public ResponseJson getCourseById(@RequestBody JSONObject param){
		return courseService.selectCourseById(param.getLong("id"));
	}
	
	/**
	 * 删除分场信息
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/course/delete",method = RequestMethod.POST)
	@ResponseBody
	public ResponseJson deleteCourse(@RequestBody JSONObject param){
		
		return courseService.deleteCourse(param.getLong("id"));
	}
	
	/**
	 * 查询全场信息
	 * @param app
	 * @return
	 */
	@RequestMapping(value = "/course/getAllCourse" , method = RequestMethod.POST)
	@ResponseBody
	public ResponseJson getAllCourse(HttpServletRequest request){
		if(request.getHeader("customerId") == null){
			return ResponseUtil.buildJson(ResultCode.PARAM_ERROR, "参数不能为空");
		}
		return courseService.getAllCourse(Long.parseLong(request.getHeader("customerId")));
	}
}

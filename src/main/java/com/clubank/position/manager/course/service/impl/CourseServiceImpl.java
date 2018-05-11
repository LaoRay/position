package com.clubank.position.manager.course.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.clubank.position.common.response.ResponseJson;
import com.clubank.position.common.response.ResultCode;
import com.clubank.position.common.util.ResponseUtil;
import com.clubank.position.common.util.StringUtil;
import com.clubank.position.manager.course.entity.Course;
import com.clubank.position.manager.course.entity.LatLng;
import com.clubank.position.manager.course.mapper.CourseInfoMapper;
import com.clubank.position.manager.course.service.CourseService;
import com.clubank.position.manager.keypoint.mapper.KeypointMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseServiceImpl implements CourseService{
	
	@Autowired
	private CourseInfoMapper courseInfoMapper;
	
	@Autowired
	private KeypointMapper keypointMapper;
	
	@Value("${IMAGE_ROOT_PATH}")
	private String imageRootPath;

	@Override
	public ResponseJson insertCourse(Long customerId, Course course) {
		if(course == null){
			return ResponseUtil.buildJson(ResultCode.PARAM_ERROR, "参数不能为空");
		}
		//验证球场名称的唯一性
		Course courseInfo = courseInfoMapper.selectCourseInfoByAppName(course.getName());
		if(courseInfo != null && !courseInfo.getId().equals(course.getId())){
			return ResponseUtil.buildJson(ResultCode.PARAM_ERROR, "场地名称已存在");
		}
		String msg = null;
		//球场主键为空，执行新增。反之，执行编辑。
		if(StringUtil.isBlank(course.getId())){
			course.setCustomer_id(customerId);
			course.setFocus(getCenterOfGravityPoint(course.getBorderLocations()));
			course.setStatus(1);
			course.setCreateTime(new Date());
			courseInfoMapper.insertSelective(course);
			msg = "新增成功";
		}else{
//			app.setModifyDate(new Date());
//			appInfoMapper.updateByPrimaryKeySelective(app);
//			msg = "编辑成功";
		}
		return ResponseUtil.buildObjectJson(ResultCode.SUCC, course.getId(),msg);
	}
	
	@Override
	public ResponseJson editPartCourse(Long id, String name) {
		if(id == null || name == null){
			return ResponseUtil.buildJson(ResultCode.PARAM_ERROR, "参数不能为空");
		}
		courseInfoMapper.updateByPrimaryKey(id,name,null,null,new Date());
		return ResponseUtil.buildJson(ResultCode.SUCC, "编辑成功");
	}
	
	@Override
	public ResponseJson editAllCourse(Long id, String borderLocations) {
		if(id == null || borderLocations == null){
			return ResponseUtil.buildJson(ResultCode.PARAM_ERROR, "参数不能为空");
		}
		courseInfoMapper.updateByPrimaryKey(id,null,borderLocations,null,new Date());
		return ResponseUtil.buildJson(ResultCode.SUCC, "编辑成功");
	}
	
	@Override
	public ResponseJson selectCourseList(Long customerId) {
		List<Course> list = courseInfoMapper.selectCourseList(customerId);
		return ResponseUtil.buildObjectJson(ResultCode.SUCC, list, "查询成功");
	}
	
	@Override
	public ResponseJson courseImgSave(Long id, String courseImage) {
		if(id == null || courseImage == null){
			return ResponseUtil.buildJson(ResultCode.PARAM_ERROR, "参数不能为空");
		}
		courseInfoMapper.updateByPrimaryKey(id,null,null,courseImage,new Date());
		return ResponseUtil.buildJson(ResultCode.SUCC, "保存");
	}
	
	@Override
	public ResponseJson deleteCourse(Long id) {
		if(id == null){
			return ResponseUtil.buildJson(ResultCode.PARAM_ERROR, "参数不能为空");
		}
		
		int keypoint = keypointMapper.selectKeypointTotal(id,null,null);//该球场内球洞数量
		if(keypoint>0){
			return ResponseUtil.buildJson(ResultCode.PARAM_ERROR, "请先删除场内球洞，回场点信息");
		}
		
		courseInfoMapper.deleteByPrimaryKey(id);
		return ResponseUtil.buildJson(ResultCode.SUCC, "删除成功");
	}
	
	public static String getCenterOfGravityPoint(String strPoints) {  
		List<LatLng> mPoints = new ArrayList<LatLng>();
		JSONArray platformList = JSON.parseArray(strPoints);	
		for (Object jsonObject : platformList) {
			 LatLng mPoint = JSONObject.parseObject(jsonObject.toString(), LatLng.class);
			 mPoints.add(mPoint);
		}
	    double area = 0.0;//多边形面积  
	    double Gx = 0.0, Gy = 0.0;// 重心的x、y  
	    for (int i = 1; i <= mPoints.size(); i++) {  
	        double iLat = mPoints.get(i % mPoints.size()).getLat();  
	        double iLng = mPoints.get(i % mPoints.size()).getLng();  
	        double nextLat = mPoints.get(i - 1).getLat();  
	        double nextLng = mPoints.get(i - 1).getLng();  
	        double temp = (iLat * nextLng - iLng * nextLat) / 2.0;  
	        area += temp;  
	        Gx += temp * (iLat + nextLat) / 3.0;  
	        Gy += temp * (iLng + nextLng) / 3.0;  
	    }  
	    Gx = Gx / area;  
	    Gy = Gy / area;  
	    return new LatLng(Gx,Gy).toString();  
	}

	@Override
	public ResponseJson uploadImage(MultipartFile file) {
		log.info("上传图片：{}", file.getName());
		try {
			StringBuffer path = new StringBuffer(imageRootPath); //根路径
			path.append("image/");
			path.append("course"); 
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

	@Override
	public ResponseJson selectCourseById(Long id) {
		if(id == null){
			return ResponseUtil.buildJson(ResultCode.PARAM_ERROR, "参数不能为空");
		}
		Course courseInfo = courseInfoMapper.selectCourseInfoById(id);
		return ResponseUtil.buildObjectJson(ResultCode.SUCC, courseInfo, "查询成功");
	}

	@Override
	public ResponseJson getAllCourse(Long customerId) {
		// TODO Auto-generated method stub
		Course course = courseInfoMapper.getAllCourse(customerId);
		return ResponseUtil.buildObjectJson(ResultCode.SUCC, course, "查询成功");
	}

}

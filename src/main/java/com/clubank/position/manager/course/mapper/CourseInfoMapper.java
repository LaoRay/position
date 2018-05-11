package com.clubank.position.manager.course.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.clubank.position.manager.course.entity.Course;


public interface CourseInfoMapper {
	/**
     * 通过场地名称获得场地信息
     * @param courseName 场地名称
     * @return
     */
    Course selectCourseInfoByAppName(String courseName);
    
    /**
     * 通过场地名称获得场地信息
     * @param courseName 场地名称
     * @return
     */
    Course selectCourseInfoById(Long id);
    
    int insertSelective(Course record);
    
    List<Course> selectCourseList(@Param("customerId")Long customerId);
    
    int deleteByPrimaryKey(Long id);
    
    int updateByPrimaryKey(@Param("id") Long id,@Param("name") String name,@Param("borderLocations") String borderLocations,@Param("fileLocation") String fileLocation,@Param("updateTime") Date updateTime);
    
    /**
     * 通过全场
     * @param 
     * @return
     */
    Course getAllCourse(@Param("customerId")Long customerId);
}

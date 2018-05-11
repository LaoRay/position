package com.clubank.position.manager.keypoint.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.clubank.position.manager.keypoint.entity.Keypoint;

public interface KeypointMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Keypoint record);

    int insertSelective(Keypoint record);

    Keypoint selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Keypoint record);

    int updateByPrimaryKey(Keypoint record);
    
    /**
     * 通过关键点类型查询、关键点名称模糊查询到的球场关键点个数
     * @param golfCourseId 球场主键
     * @param type 关键点类型（1-球洞 2-出发台 3-回场点）
     * @param name 关键点名称
     * @return
     */
    int selectKeypointTotal(@Param("golfCourseId")Long golfCourseId, @Param("type")Integer type, @Param("name")String name);
    
    /**
     * 通过关键点类型查询、关键点名称模糊查询到的球场关键点列表
     * @param golfCourseId 球场主键
     * @param type 关键点类型（1-球洞 2-出发台 3-回场点）
     * @param name 关键点名称
     * @param startIndex 起始索引
     * @param pageSize 页容量
     * @return
     */
    List<Keypoint> selectKeypointList(@Param("golfCourseId")Long golfCourseId, @Param("type")Integer type, 
    		@Param("name")String name, @Param("startIndex")Integer startIndex, @Param("pageSize")Integer pageSize);
    
    /**
     * 通过名称查询球场关键点
     * @param golfCourseId 球场主键
     * @param name 关键点名称
     * @return
     */
    Keypoint selectKeypointByName(@Param("golfCourseId")Long golfCourseId, @Param("name")String name);
   
    /**
     * 查询所有关键点信息
     * @param golfCourseId 球场主键
     * @param name 关键点名称
     * @return
     */
    List<Keypoint> selectAllKeypointList();
}
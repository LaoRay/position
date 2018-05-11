package com.clubank.position.manager.keypoint.service;

import com.clubank.position.common.response.ResponseJson;
import com.clubank.position.manager.keypoint.entity.Keypoint;

public interface KeypointService {

	/**
	 * 创建和编辑球场关键点
	 * @param record
	 * @return
	 */
	 ResponseJson insertOrUpdateKeypoint(Keypoint record);
	 
	 /**
	  * 删除球场关键点
	  * @param id
	  * @return
	  */
	 ResponseJson delteKeypoint(Long id);
	 
	 /**
	  * 获得关键点列表
	  * @param golfCourseId 球场主键
	  * @param type 关键点类型（1-球洞 2-出发台 3-回场点）
	  * @param name 关键点名称
	  * @param pageIndex 页码下标
	  * @param pageSize 页容量
	  * @return
	  */
	 ResponseJson selectKeypointList(Long golfCourseId, Integer type, String name, Integer pageIndex, Integer pageSize);
	 
	 /**
	  * 查询关键点信息
	  * @param id 关键点主键
	  * @return
	  */
	 ResponseJson selectKeypointInfo(Long id);
}

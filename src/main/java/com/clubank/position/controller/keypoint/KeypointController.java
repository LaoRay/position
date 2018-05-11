package com.clubank.position.controller.keypoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.clubank.position.common.response.ResponseJson;
import com.clubank.position.manager.keypoint.entity.Keypoint;
import com.clubank.position.manager.keypoint.service.KeypointService;

/**
 * 球场关键点管理
 * @author Liangwl
 *
 */
@Controller
@RequestMapping("/keypoint")
public class KeypointController {
	
	@Autowired
	private KeypointService keypointService;
	
	/**
	 * 新增或编辑球场关键点（1-球洞 2-出发台 3-回场点）
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/addOrEdit",method = RequestMethod.POST)
	@ResponseBody
	public ResponseJson insertOrUpdateKeypoint(@RequestBody Keypoint record){
		
		return keypointService.insertOrUpdateKeypoint(record);
	}
	
	/**
	 * 删除球场关键点
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public ResponseJson deleteKeypoint(@RequestBody JSONObject param){
		
		return keypointService.delteKeypoint(param.getLong("id"));
	}
	
	/**
	 * 获得关键点列表
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	@ResponseBody
	public ResponseJson selectKeypointList(@RequestBody JSONObject param){
		
		return keypointService.selectKeypointList(param.getLong("golfCourseId"), param.getInteger("type"),
				param.getString("name"), param.getInteger("pageIndex"), param.getInteger("pageSize"));
	}
	
	/**
	 * 获得关键点信息
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/info",method = RequestMethod.POST)
	@ResponseBody
	public ResponseJson selectKeypointInfo(@RequestBody JSONObject param){
		
		return keypointService.selectKeypointInfo(param.getLong("id"));
	}
}

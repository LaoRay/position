package com.clubank.position.manager.keypoint.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clubank.position.common.response.ResponseJson;
import com.clubank.position.common.response.ResultCode;
import com.clubank.position.common.util.PageObject;
import com.clubank.position.common.util.ResponseUtil;
import com.clubank.position.manager.keypoint.entity.Keypoint;
import com.clubank.position.manager.keypoint.mapper.KeypointMapper;
import com.clubank.position.manager.keypoint.service.KeypointService;

@Service
public class KeypointServiceImpl implements KeypointService {
	
	@Autowired
	private KeypointMapper keypointMapper;

	@Override
	public ResponseJson insertOrUpdateKeypoint(Keypoint record) {
		if(record == null || record.getName() == null || record.getGolfCourseId() == null){
			return ResponseUtil.buildJson(ResultCode.PARAM_ERROR, "参数不能为空");
		}
		//判断关键点的唯一性
		Keypoint keypoint = keypointMapper.selectKeypointByName(record.getGolfCourseId(), record.getName());
		if(keypoint != null && !keypoint.getId().equals(record.getId())){
			return ResponseUtil.buildJson(ResultCode.DATA_EXIST, "已存在");
		}
		Long id = null;
		if(record.getId() == null){
			record.setCreateTime(new Date());
			keypointMapper.insertSelective(record);
			
			id = keypointMapper.selectKeypointByName(record.getGolfCourseId(), record.getName()).getId();
		}else{
			record.setUpdateTime(new Date());
			keypointMapper.updateByPrimaryKeySelective(record);
			id = record.getId();
		}
		return ResponseUtil.buildIdJson(ResultCode.SUCC, id.toString(), "操作成功");
	}

	@Override
	public ResponseJson delteKeypoint(Long id) {
		if(id == null){
			return ResponseUtil.buildJson(ResultCode.PARAM_ERROR, "参数不能为空");
		}
		keypointMapper.deleteByPrimaryKey(id);
		return ResponseUtil.buildJson(ResultCode.SUCC, "删除成功");
	}

	@Override
	public ResponseJson selectKeypointList(Long golfCourseId, Integer type, String name, Integer pageIndex, Integer pageSize) {
		if(golfCourseId == null || type == null){
			return ResponseUtil.buildJson(ResultCode.PARAM_ERROR, "参数不能为空");
		}
		PageObject<Keypoint> page = new PageObject<>(pageIndex, pageSize);
		int total = keypointMapper.selectKeypointTotal(golfCourseId, type, name);
		List<Keypoint> list = keypointMapper.selectKeypointList(golfCourseId, type, name, page.getStart(), pageSize);
		page.setRows(total);
		page.setDataList(list);
		return ResponseUtil.buildObjectJson(ResultCode.SUCC, page, "查询成功");
	}

	@Override
	public ResponseJson selectKeypointInfo(Long id) {
		if(id == null){
			return ResponseUtil.buildJson(ResultCode.PARAM_ERROR, "参数不能为空");
		}
		Keypoint keypoint = keypointMapper.selectByPrimaryKey(id);
		return ResponseUtil.buildObjectJson(ResultCode.SUCC, keypoint, "查询成功");
	}
}

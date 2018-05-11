package com.clubank.position.manager.cart.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.clubank.position.common.response.ResponseJson;
import com.clubank.position.common.response.ResultCode;
import com.clubank.position.common.util.PageObject;
import com.clubank.position.common.util.ResponseUtil;
import com.clubank.position.manager.cart.entity.Car;
import com.clubank.position.manager.cart.mapper.CarMapper;
import com.clubank.position.manager.cart.service.CarService;

@Service
public class CarServiceImpl implements CarService {

	@Autowired
	private CarMapper carMapper;
	
	@Override
	public ResponseJson insertOrUpdateCar(Car record) {
		if(record == null || record.getCustomerId() == null){
			return ResponseUtil.buildJson(ResultCode.PARAM_ERROR, "参数不能为空");
		}
		//判断车辆唯一性
		Car carA = carMapper.selectCarByCarNo(record.getCustomerId(), record.getCarNo());
		if(carA != null && !carA.getId().equals(record.getId())){
			return ResponseUtil.buildJson(ResultCode.DATA_EXIST, "车辆编号已存在");
		}
		//判断终端设备唯一性
		Car carB = carMapper.selectCarByTerminalNo(record.getTerminalNo());
		if(carB != null && !carB.getId().equals(record.getId())){
			return ResponseUtil.buildJson(ResultCode.DATA_EXIST, "设备编号已存在");
		}
		if(record.getId() == null){
			record.setCreateTime(new Date());
			carMapper.insertSelective(record);
		}else{
			record.setUpdateTime(new Date());
			carMapper.updateByPrimaryKeySelective(record);
		}
		return ResponseUtil.buildJson(ResultCode.SUCC, "操作成功");
	}

	@Override
	public ResponseJson deleteCar(JSONArray carIds) {
		if(carIds == null || carIds.isEmpty()){
			return ResponseUtil.buildJson(ResultCode.PARAM_ERROR, "参数不能为空");
		}
		List<Car> list = JSON.parseArray(carIds.toString(), Car.class);
		for (Car car : list) {
			carMapper.deleteByPrimaryKey(car.getId());
		}
		return ResponseUtil.buildJson(ResultCode.SUCC, "删除成功");
	}

	@Override
	public ResponseJson selectCarList(Long customerId, Long categoryId, String carNo, String terminalNo,
			Integer pageIndex, Integer pageSize) {
		PageObject<Map<String, Object>> page = new PageObject<>(pageIndex, pageSize);
		int total = carMapper.selectCarTotal(customerId, categoryId, carNo, terminalNo);
		List<Map<String, Object>> list = carMapper.selectCarList(customerId, categoryId, carNo, terminalNo, page.getStart(), pageSize);
		page.setRows(total);
		page.setDataList(list);
		return ResponseUtil.buildObjectJson(ResultCode.SUCC, page, "查询成功");
	}

	@Override
	public ResponseJson selectCarInfo(Long id) {
		if(id == null){
			return ResponseUtil.buildJson(ResultCode.PARAM_ERROR, "参数不能为空");
		}
		Map<String, Object> map = carMapper.selectCarInfo(id);
		return ResponseUtil.buildObjectJson(ResultCode.SUCC, map, "查询成功");
	}
}

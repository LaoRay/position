package com.clubank.position.manager.cart.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clubank.position.common.response.ResponseJson;
import com.clubank.position.common.response.ResultCode;
import com.clubank.position.common.util.ResponseUtil;
import com.clubank.position.manager.cart.entity.Car;
import com.clubank.position.manager.cart.entity.CarCategory;
import com.clubank.position.manager.cart.mapper.CarCategoryMapper;
import com.clubank.position.manager.cart.mapper.CarMapper;
import com.clubank.position.manager.cart.service.CarCategoryService;

@Service
public class CarCategoryServiceImpl implements CarCategoryService {
	
	@Autowired
	private CarCategoryMapper carCategoryMapper;
	@Autowired
	private CarMapper carMapper;

	@Override
	public ResponseJson insertOrUpdateCarCategory(CarCategory record) {
		if(record == null || record.getCustomerId() == null){
			return ResponseUtil.buildJson(ResultCode.PARAM_ERROR, "参数不能为空");
		}
		//判断车辆类型是否唯一
		CarCategory carCategory = carCategoryMapper.selectCarCategoryByName(record.getName());
		if(carCategory != null && !carCategory.getId().equals(record.getId())){
			return ResponseUtil.buildJson(ResultCode.DATA_EXIST, "已存在");
		}
		if(record.getId() == null){
			record.setCreateTime(new Date());
			carCategoryMapper.insertSelective(record);
		}else{
			record.setUpdateTime(new Date());
			carCategoryMapper.updateByPrimaryKeySelective(record);
		}
		return ResponseUtil.buildJson(ResultCode.SUCC, "操作成功");
	}

	@Override
	public ResponseJson deleteCarCategory(Long id) {
		if(id == null){
			return ResponseUtil.buildJson(ResultCode.PARAM_ERROR, "参数不能为空");
		}
		//判断此车辆类型是否在使用
		List<Car> list = carMapper.selectCarByCategoryId(id);
		if(list != null && list.size() > 0){
			return ResponseUtil.buildJson(ResultCode.DATA_EXIST, "此类型已被使用，不可删除！");
		}
		carCategoryMapper.deleteByPrimaryKey(id);
		return ResponseUtil.buildJson(ResultCode.SUCC, "删除成功");
	}

	@Override
	public ResponseJson selectCarCategoryList(Long customerId) {
		List<CarCategory> list = carCategoryMapper.selectCarCategoryList(customerId);
		return ResponseUtil.buildObjectJson(ResultCode.SUCC, list, "查询成功");
	}

	@Override
	public ResponseJson selectCarCategoryInfo(Long id) {
		if(id == null){
			return ResponseUtil.buildJson(ResultCode.PARAM_ERROR, "参数不能为空");
		}
		CarCategory carCategory = carCategoryMapper.selectByPrimaryKey(id);
		return ResponseUtil.buildObjectJson(ResultCode.SUCC, carCategory, "查询成功");
	}

}

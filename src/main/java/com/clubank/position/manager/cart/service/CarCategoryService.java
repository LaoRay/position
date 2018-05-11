package com.clubank.position.manager.cart.service;

import com.clubank.position.common.response.ResponseJson;
import com.clubank.position.manager.cart.entity.CarCategory;

public interface CarCategoryService {

	/**
	 * 新增或编辑车辆类型
	 * @param record
	 * @return
	 */
	ResponseJson insertOrUpdateCarCategory(CarCategory record);
	
	/**
	 * 删除车辆类型
	 * @param id 车辆类型id
	 * @return
	 */
	ResponseJson deleteCarCategory(Long id);
	
	/**
	 * 获得车辆类型列表
	 * @param customerId 客户方主键
	 * @return
	 */
	ResponseJson selectCarCategoryList(Long customerId);
	
	/**
	 * 获得车辆类型信息
	 * @param id 主键
	 * @return
	 */
	ResponseJson selectCarCategoryInfo(Long id);
}

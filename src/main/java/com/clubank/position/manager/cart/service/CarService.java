package com.clubank.position.manager.cart.service;

import com.alibaba.fastjson.JSONArray;
import com.clubank.position.common.response.ResponseJson;
import com.clubank.position.manager.cart.entity.Car;

public interface CarService {

	/**
	 * 新增或编辑车辆
	 * @param record
	 * @return
	 */
	ResponseJson insertOrUpdateCar(Car record);
	
	/**
	 * 删除车辆（批量删除）
	 * @param Ids 车辆id数组
	 * @return
	 */
	ResponseJson deleteCar(JSONArray ids);
	
	/**
	 * 含模糊查询获得车辆列表并分页
	 * @param customerId 客户方id
	 * @param categoryId 类型id
	 * @param carNo 车辆编号
	 * @param terminalNo 设备编号
	 * @param pageIndex 起始下标
	 * @param pageSize 页容量
	 * @return
	 */
	ResponseJson selectCarList(Long customerId, Long categoryId, String carNo, String terminalNo,
			Integer pageIndex, Integer pageSize);
	
	/**
	 * 获得车辆信息
	 * @param id 主键
	 * @return
	 */
	ResponseJson selectCarInfo(Long id);
}

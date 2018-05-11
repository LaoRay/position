package com.clubank.position.controller.cart;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.clubank.position.common.response.ResponseJson;
import com.clubank.position.common.response.ResultCode;
import com.clubank.position.common.util.ResponseUtil;
import com.clubank.position.manager.cart.entity.Car;
import com.clubank.position.manager.cart.entity.CarCategory;
import com.clubank.position.manager.cart.service.CarCategoryService;
import com.clubank.position.manager.cart.service.CarService;

/**
 * 球车管理
 * @author Liangwl
 *
 */
@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CarService carService;
	@Autowired
	private CarCategoryService carCategoryService;
	
	/**
	 * 新增或编辑车辆类型
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/addOrEditCarCategory", method = RequestMethod.POST)
	@ResponseBody
	public ResponseJson insertOrUpdateCarCategory(@RequestBody CarCategory record){
		
		return carCategoryService.insertOrUpdateCarCategory(record);
	}
	
	/**
	 * 删除车辆类型
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/deleteCarCategory", method = RequestMethod.POST)
	@ResponseBody
	public ResponseJson deleteCarCategory(@RequestBody JSONObject param){
		
		return carCategoryService.deleteCarCategory(param.getLong("id"));
	}
	
	/**
	 * 获得车辆类型信息
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/carCategoryInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseJson selectCarCategoryInfo(@RequestBody JSONObject param){
		
		return carCategoryService.selectCarCategoryInfo(param.getLong("id"));
	}
	
	/**
	 * 获得车辆类型列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/carCategoryList", method = RequestMethod.POST)
	@ResponseBody
	public ResponseJson selectCarCategoryList(HttpServletRequest request){
		if(request.getHeader("customerId") == null){
			return ResponseUtil.buildJson(ResultCode.PARAM_ERROR, "参数不能为空");
		}
		return carCategoryService.selectCarCategoryList(Long.valueOf(request.getHeader("customerId")));
	}
	
	/**
	 * 新增或编辑车辆
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/addOrEditCar", method = RequestMethod.POST)
	@ResponseBody
	public ResponseJson insertOrUpdateCar(@RequestBody Car record){
		
		return carService.insertOrUpdateCar(record);
	}
	
	/**
	 * 删除车辆（批量删除）
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/deleteCar", method = RequestMethod.POST)
	@ResponseBody
	public ResponseJson deleteCar(@RequestBody JSONObject param){
		
		return carService.deleteCar(param.getJSONArray("carIds"));
	}
	
	/**
	 * 获得车辆信息
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/carInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseJson selectCarInfo(@RequestBody JSONObject param){
		
		return carService.selectCarInfo(param.getLong("id"));
	}
	
	/**
	 * 获得车辆列表并分页
	 * @param request
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/carList", method = RequestMethod.POST)
	@ResponseBody
	public ResponseJson selectCarList(HttpServletRequest request, @RequestBody JSONObject param){
		if(request.getHeader("customerId") == null){
			return ResponseUtil.buildJson(ResultCode.PARAM_ERROR, "参数不能为空");
		}
		return carService.selectCarList(Long.valueOf(request.getHeader("customerId")), param.getLong("categoryId"), 
				param.getString("carNo"), param.getString("terminalNo"), param.getInteger("pageIndex"), param.getInteger("pageSize"));
	}
}

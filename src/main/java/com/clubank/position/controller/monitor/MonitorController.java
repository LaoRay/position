package com.clubank.position.controller.monitor;

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
import com.clubank.position.monitor.service.MonitorService;

@Controller
@RequestMapping("/monitor")
public class MonitorController {
	
	@Autowired
	private MonitorService monitorService;
	
	/**
	 * 球车球洞轨迹
	 * @param 球车id
	 * @return
	 */
	@RequestMapping(value = "/showCarHoleDetail" , method = RequestMethod.POST)
	@ResponseBody
	public ResponseJson showCarHoleDetail(@RequestBody JSONObject param){

		return monitorService.showCarHoleDetail(param.getString("terminalNo"));
	}
	
	/**
	 * 球车行车（1小时）
	 * @param 球车id
	 * @return
	 */
	@RequestMapping(value = "/showCarDriveDetail" , method = RequestMethod.POST)
	@ResponseBody
	public ResponseJson showCarDriveDetail(@RequestBody JSONObject param){

		return monitorService.showCarDriveDetail(param.getLong("carId"));
	}
	
	/**
	 * 搜索按钮
	 * @param 搜索条件
	 * @return
	 */
	@RequestMapping(value = "/search" , method = RequestMethod.POST)
	@ResponseBody
	public ResponseJson search(@RequestBody JSONObject param){
		return monitorService.search(param.getString("info"));
	}
	
	/**
	 * 点击球车查看信息
	 * @param 设备编号
	 * @return
	 */
	@RequestMapping(value = "/carDetail" , method = RequestMethod.POST)
	@ResponseBody
	public ResponseJson carDetail(@RequestBody JSONObject param){

		return monitorService.carDetail(param.getString("terminalNo"));
	}
	
	/**
	 * 球车位置信息
	 * @param 客户信息
	 * @return
	 */
	@RequestMapping(value = "/carLocation" , method = RequestMethod.POST)
	@ResponseBody
	public ResponseJson carLocation(HttpServletRequest request){
		if(request.getHeader("customerId") == null){
			return ResponseUtil.buildJson(ResultCode.PARAM_ERROR, "参数不能为空");
		}
		return monitorService.carLocation(Long.parseLong(request.getHeader("customerId")));
	}
	
}

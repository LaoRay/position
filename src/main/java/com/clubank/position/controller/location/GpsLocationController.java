package com.clubank.position.controller.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.clubank.position.common.response.ResponseJson;
import com.clubank.position.common.response.ResultCode;
import com.clubank.position.manager.location.entity.GpsLocations;
import com.clubank.position.manager.location.service.GpsLocationService;

@RestController
@RequestMapping("/gps")
public class GpsLocationController {

	@Autowired
	private GpsLocationService gpsLocationService;

	/**
	 * 接收GPS定位信息
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/data", method = RequestMethod.POST)
	public ResponseJson receiveLocation(@RequestBody GpsLocations gpsLocations) {
		gpsLocationService.processGpsLocations(gpsLocations);
		return new ResponseJson(ResultCode.SUCC, "SUCCESS");
	}
}

package com.clubank.position.manager.location.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.clubank.position.common.redis.JedisClient;
import com.clubank.position.common.redis.RedisKeyCode;
import com.clubank.position.common.util.GPSUtil;
import com.clubank.position.common.websocket.WebSocketHandler;
import com.clubank.position.manager.course.entity.LatLng;
import com.clubank.position.manager.keypoint.entity.Keypoint;
import com.clubank.position.manager.keypoint.mapper.KeypointMapper;
import com.clubank.position.manager.location.entity.GpsLocations;
import com.clubank.position.manager.location.mapper.GpsLocationsMapper;
import com.clubank.position.manager.location.service.GpsLocationService;
import com.clubank.position.monitor.entity.PassingHole;
import com.clubank.position.monitor.mapper.MonitorMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GpsLocationServiceImpl implements GpsLocationService {

	@Autowired
	private JedisClient jedisClient;
	@Autowired
	private WebSocketHandler webSocketHandler;
	@Autowired
	private GpsLocationsMapper gpsLocationsMapper;
	@Autowired
	private KeypointMapper keypointMapper;
	@Autowired
	private MonitorMapper monitorMapper;

	@Override
	public void processGpsLocations(GpsLocations gpsLocations) {
		log.debug("位置信息:{}",
				JSON.toJSONStringWithDateFormat(gpsLocations, "yyyy-MM-dd HH:mm:ss", SerializerFeature.PrettyFormat));
		// 保存数据库
		gpsLocationsMapper.insertSelective(gpsLocations);
		// 百度纠偏
		double[] bd_gps = GPSUtil.gps84_To_bd09(gpsLocations.getLatitude(), gpsLocations.getLongitude());
		gpsLocations.setLatitude(bd_gps[0]);
		gpsLocations.setLongitude(bd_gps[1]);
		// 缓存REDIS
		jedisClient.set(RedisKeyCode.GPS_LOCATION.getValue() + gpsLocations.getTerminalNo(),
				JSON.toJSONStringWithDateFormat(gpsLocations, "yyyy-MM-dd HH:mm:ss"));
		// 小车过球洞信息
		savePassHole(gpsLocations);
		// 推送前端实时坐标信息
		webSocketHandler.sendMessageToUsers(
				new TextMessage(JSON.toJSONStringWithDateFormat(gpsLocations, "yyyy-MM-dd HH:mm:ss")));
	}

	/**
	 * 球车是否经过球洞增加轨迹数据，回场后清除轨迹
	 * 
	 * @param gpsLocations
	 * @return
	 */
	private void savePassHole(GpsLocations gpsLocations) {
		List<Keypoint> keypoints = keypointMapper.selectAllKeypointList();
		double jl;
		LatLng carLoa = new LatLng(gpsLocations.getLatitude(), gpsLocations.getLongitude());
		LatLng keypointLoa = new LatLng();
		for (Keypoint keypoint : keypoints) {
			JSONObject obj = JSON.parseObject(keypoint.getLocation());
			Double latitude = obj.getDouble("lat");
			Double longitude = obj.getDouble("lng");
			keypointLoa.setLat(latitude);
			keypointLoa.setLng(longitude);
			jl = getLatLngDistance(carLoa, keypointLoa);// 球车和球洞的距离

			if (jl < keypoint.getRadius()) {// 进入球洞范围
				PassingHole ph = new PassingHole();
				if (keypoint.getType() == 1) {// 球洞
					int total = monitorMapper.selectTotal(gpsLocations.getTerminalNo(), keypoint.getId());
					if (total==0) {
						// 数据库保存过洞信息
						ph.setBallHoleId(keypoint.getId());
						ph.setTerminalNo(gpsLocations.getTerminalNo());
						ph.setTime(new Date());
						ph.setStatue(0);
						monitorMapper.insertSelective(ph);
					}
				} else if (keypoint.getType() == 3) {// 回场点
					monitorMapper.updatePassingHole(gpsLocations.getTerminalNo());
				}
			} 
		}
	}

	/**
	 * 计算两点之间距离
	 * 
	 * @param start
	 * @param end
	 * @return Double 单位m
	 */
	private double getLatLngDistance(LatLng start, LatLng end) {
		double lat1 = (Math.PI / 180) * start.getLat();
		double lat2 = (Math.PI / 180) * end.getLat();
		double lon1 = (Math.PI / 180) * start.getLng();
		double lon2 = (Math.PI / 180) * end.getLng();
		// 地球半径
		double R = 6371.004;
		// 两点间距离 m，如果想要米的话，结果*1000就可以了
		double dis = Math
				.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1)) * R;
		return dis * 1000;
	}
}

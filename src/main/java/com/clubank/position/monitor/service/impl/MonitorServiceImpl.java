package com.clubank.position.monitor.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.clubank.position.common.redis.JedisClient;
import com.clubank.position.common.response.ResponseJson;
import com.clubank.position.common.response.ResultCode;
import com.clubank.position.common.util.GPSUtil;
import com.clubank.position.common.util.ResponseUtil;
import com.clubank.position.manager.cart.entity.Car;
import com.clubank.position.manager.cart.mapper.CarMapper;
import com.clubank.position.manager.course.entity.LatLng;
import com.clubank.position.manager.keypoint.entity.Keypoint;
import com.clubank.position.monitor.entity.CarLocation;
import com.clubank.position.monitor.entity.PassingHole;
import com.clubank.position.monitor.mapper.MonitorMapper;
import com.clubank.position.monitor.service.MonitorService;

@Service
public class MonitorServiceImpl implements MonitorService {
	
	@Autowired
	private MonitorMapper monitorMapper;
	
	@Autowired
	private CarMapper carMapper;
	
	@Autowired
	private JedisClient jedisClient;

	@Override
	public ResponseJson showCarHoleDetail(String terminalNo) {
		if(terminalNo == null){
			return ResponseUtil.buildJson(ResultCode.PARAM_ERROR, "参数不能为空");
		}
		List<PassingHole> list = monitorMapper.selectHoleInfoByTerminalNo(terminalNo);
		return ResponseUtil.buildObjectJson(ResultCode.SUCC, list, "查询成功");
	}

	@Override
	public ResponseJson showCarDriveDetail(Long carId) {
		if(carId == null){
			return ResponseUtil.buildJson(ResultCode.PARAM_ERROR, "参数不能为空");
		}
		Car car = carMapper.selectByPrimaryKey(carId);
		if(car == null){
			return ResponseUtil.buildJson(ResultCode.DATA_NOT_FOUND, "数据不存在");
		}
		List<LatLng> list = monitorMapper.selectDriveInfoByCarId(car.getTerminalNo());
		List<LatLng> listRes = new ArrayList<LatLng>();
		for (LatLng latLng : list) {
			double[] bd_gps = GPSUtil.gps84_To_bd09(latLng.getLat(), latLng.getLng());
			latLng.setLat(bd_gps[0]);
			latLng.setLng(bd_gps[1]);
			listRes.add(latLng);
		}
		return ResponseUtil.buildObjectJson(ResultCode.SUCC, list, "查询成功");
	}

	@Override
	public ResponseJson search(String info) {
		List<Car> cars = monitorMapper.searchCarByInfo(info);
		List<Keypoint> Keypoints = monitorMapper.searchKeypointByInfo(info);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("cars", cars);
		map.put("Keypoints", Keypoints);
		return ResponseUtil.buildObjectJson(ResultCode.SUCC, map, "查询成功");
	}

	@Override
	public ResponseJson carDetail(String terminalNo) {
		if(terminalNo == null){
			return ResponseUtil.buildJson(ResultCode.PARAM_ERROR, "参数不能为空");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Car car = carMapper.selectCarByTerminalNo(terminalNo);
		map.put("id", car.getId());
		map.put("carNo", car.getCarNo());
		map.put("terminalNo", car.getTerminalNo());
//		map.put("playerName", getPlayerInfo(car.getCarNo()));
		map.put("playerName", "张三");
		map.put("playerTel", "13888888888");
		return ResponseUtil.buildObjectJson(ResultCode.SUCC, map, "查询成功");
	}
	
	
	/**
	 * 从其他系统接口获取高尔夫球车用户信息
	 * param 球车编号
	 * */
	public String getPlayerInfo(String carNo){
		try{
			final String SERVER_URL="http://localhost:8080/sms/sendcode";
			CloseableHttpClient httpClient = HttpClients.createDefault();
	        HttpPost httpPost = new HttpPost(SERVER_URL);
	        // 设置请求的header
	        // httpPost.addHeader("appKey", APP_KEY);
	        // 设置请求的的参数，requestBody参数
	        //List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	        //nvps.add(new BasicNameValuePair("tempCode", TEMP_CODE));
	        //httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
	        // 执行请求
	        HttpResponse response = httpClient.execute(httpPost);
	        String res = EntityUtils.toString(response.getEntity(), "utf-8");
	
	        System.out.println(res);
	        return res;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ResponseJson carLocation(Long customerId) {
		List<CarLocation> carls = monitorMapper.getCarLocation(customerId);
		List<CarLocation> carlRes = new ArrayList<CarLocation>();
		String carKey;
		String carRes;
		for (CarLocation carLocation : carls) {
			carKey = "GPS:"+carLocation.getTerminalNo();
			carRes = jedisClient.get(carKey);
			if(carRes!=null){
				JSONObject obj=JSON.parseObject(carRes);
				Double latitude = obj.getDouble("latitude");
				Double longitude = obj.getDouble("longitude");
				LatLng ll = new LatLng(latitude,longitude);
				carLocation.setLocation(ll.toString());
			}
			carlRes.add(carLocation);
		}
		return ResponseUtil.buildObjectJson(ResultCode.SUCC, carlRes, "查询成功");
	}

}

package com.clubank.position.monitor.service;

import com.clubank.position.common.response.ResponseJson;

public interface MonitorService {
	
	ResponseJson showCarHoleDetail(String terminalNo);
	ResponseJson showCarDriveDetail(Long carId);
	ResponseJson search(String info);
	ResponseJson carDetail(String terminalNo);
	ResponseJson carLocation(Long customerId);

}

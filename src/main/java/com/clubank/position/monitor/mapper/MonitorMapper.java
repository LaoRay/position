package com.clubank.position.monitor.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.clubank.position.manager.cart.entity.Car;
import com.clubank.position.manager.course.entity.LatLng;
import com.clubank.position.manager.keypoint.entity.Keypoint;
import com.clubank.position.monitor.entity.CarLocation;
import com.clubank.position.monitor.entity.PassingHole;

public interface MonitorMapper {
	List<PassingHole> selectHoleInfoByTerminalNo(@Param("terminalNo")String terminalNo);
	List<LatLng> selectDriveInfoByCarId(@Param("terminalId")String terminalId);
	
	List<Car> searchCarByInfo(@Param("info")String info);
	List<Keypoint> searchKeypointByInfo(@Param("info")String info);
	
	List<CarLocation> getCarLocation(@Param("customerId")Long customerId);
	
	int insertSelective(PassingHole passingHole);

    int updatePassingHole(@Param("terminalNo") String terminalId);
    
   /* int deletePassingHole(@Param("terminalNo") String terminalId);*/
    
    int selectTotal(@Param("terminalNo")String terminalNo, @Param("ballHoleId")Long ballHoleId);
	
}

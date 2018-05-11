package com.clubank.position.monitor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarLocation {

	/**
	 * 主键
	 */
    private Long id;

    /**
     * 客户方主键
     */
    private Long customerId;

    /**
     * 车辆类型id
     */
    private Long categoryId;

    /**
     * 车辆编号
     */
    private String carNo;

    /**
     * 终端编号
     */
    private String terminalNo;
    
    /**
     * 球车类型名称
     * */
    private String name;
    
    /**
     * 车辆图标
     */
    private String carIcon;

    /**
     * 车辆拥堵图标
     */
    private String blockCarIcon;

    /**
     * 车辆回场图标
     */
    private String backCarIcon;
    
    /**
     * 车辆警告图标
     */
    private String warnCarIcon;
    
    /**
     * 车辆位置
     */
    private String location;
}

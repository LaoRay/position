package com.clubank.position.manager.cart.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 车辆信息
 * @author Liangwl
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
	/**
	 * 主键
	 */
    private Long id;

    /**
     * 客户方主键
     */
    private Long customerId;

    /**
     * 序号
     */
    private Integer orderNum;

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
     * 状态
     */
    private Integer status;

    /**
     * 描述
     */
    private String description;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;
    
    /**
     * 关联表 车辆类型名称
     */
    private String name;
}
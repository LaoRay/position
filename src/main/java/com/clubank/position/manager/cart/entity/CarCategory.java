package com.clubank.position.manager.cart.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 车辆类型
 * @author Liangwl
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarCategory {
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
     * 名称
     */
    private String name;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 是否显示：0-不显示 1-显示
     */
    private Byte isShow;

    /**
     * 是否判断拥堵：0-不判断 1-判断
     */
    private Byte isJudgeBlock;

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
}
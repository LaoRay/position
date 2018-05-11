package com.clubank.position.manager.keypoint.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 球场关键点信息
 * @author Liangwl
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Keypoint {
	/**
	 * 主键
	 */
    private Long id;

    /**
     * 球场主键
     */
    private Long golfCourseId;

    /**
     * 序号
     */
    private Integer orderNum;

    /**
     * 类型：1-球洞 2-出发台 3-回场点
     */
    private Integer type;
    
    /**
     * 名称
     */
    private String name;

    /**
     * 坐标
     */
    private String location;

    /**
     * 半径
     */
    private Float radius;

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
}
package com.clubank.position.manager.course.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 球场信息表
 * @author wangtao1030
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
	/**
	 * 1主键
	 */
    private Long id;
    /**
	 * 2客户方主键
	 */
    private Long customer_id;
    /**
	 * 3序号
	 */
    private Integer order_num;
    /**
	 * 4名称
	 */
    private String name;
    /**
	 * 5边界线
	 */
    private String borderLocations;
    /**
	 * 6重心
	 */
    private String focus;
    /**
	 * 7图片
	 */
    private String courseImage;
    /**
	 * 8状态
	 */
    private Integer status;
    /**
	 * 9场地类型 1--全场  2--分场
	 */
    private Integer type;
    /**
	 * 10描述
	 */
    private String description;
    /**
	 * 11备注
	 */
    private String remarks;
    /**
	 * 12创建时间
	 */
    private Date createTime;
    /**
	 * 13修改时间
	 */
    private Date updateTime;
}

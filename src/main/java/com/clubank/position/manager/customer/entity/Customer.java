package com.clubank.position.manager.customer.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 客户方信息
 * @author Liangwl
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
	/**
	 * 主键
	 */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 联系电话
     */
    private String contactPhone;

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
package com.clubank.position.manager.customer.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 客户方人员信息
 * @author Liangwl
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUser {
	/**
	 * 主键
	 */
    private Long id;

    /**
     * 客户方主键
     */
    private Long customerId;

    /**
     * 名称
     */
    private String name;

    /**
     * 状态：0-无效 1-有效
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
     * 角色：1-管理员 2-操作员
     */
    private Integer role;

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
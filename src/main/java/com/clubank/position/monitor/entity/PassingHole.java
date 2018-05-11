package com.clubank.position.monitor.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 球车过球洞信息
 * @author wangtao1030
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassingHole {
	/**
	 * 1主键
	 */
	private Long id;
	/**
	 * 2终端编号
	 */
	private String terminalNo;
	/**
	 * 3球洞主键
	 */
	private Long ballHoleId;
	/**
	 * 4过球洞信息
	 */
	private Date time;
	/**
	 * 5状态  0-正在出车 1已回场
	 */
	private Integer statue;
	
	/**
	 * 6关联球洞表(球洞坐标)
	 */
	private String location;
	
	/**
	 * 7关联球洞表(球洞名称)
	 */
	private String name;
}

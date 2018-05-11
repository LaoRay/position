package com.clubank.position.manager.location.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class GpsLocations {

	private Long id;

	private String terminalNo;

	private Double longitude;

	private Double latitude;

	private Integer elevation;

	private Double speed;

	private Integer direction;

	private Date time;
}
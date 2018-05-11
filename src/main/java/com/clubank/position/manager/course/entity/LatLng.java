package com.clubank.position.manager.course.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LatLng {
	private double lat;
	private double lng;

	@Override
	public String toString() {
		return "{\"lng\":" + lng + ", \"lat\":" + lat +"}" ;
	}

}

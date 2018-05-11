package com.clubank.position.manager.location.mapper;

import java.util.List;

import com.clubank.position.manager.location.entity.GpsLocations;

public interface GpsLocationsMapper {
	int deleteByPrimaryKey(Long id);

	int insert(GpsLocations record);

	int insertSelective(GpsLocations record);

	int insertBatch(List<GpsLocations> list);

	GpsLocations selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(GpsLocations record);

	int updateByPrimaryKey(GpsLocations record);
}
package com.cognine.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.cognine.model.Region;

@Mapper
public interface RegionMapper {

	String getRegions = "select id as regionId,regionname from regions order by regionname";

	@Select(getRegions)
	List<Region> getRegions();

}

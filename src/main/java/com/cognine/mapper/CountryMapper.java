package com.cognine.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.cognine.model.Country;

@Mapper
public interface CountryMapper {

	public String getCountries = "select id as countryId,country as countryName from country where "
			+ "active = true order by country asc";

	@Select(getCountries)
	List<Country> getCountries();

}

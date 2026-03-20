package com.cognine.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cognine.dao.CountDao;
import com.cognine.mapper.CountMapper;
import com.cognine.model.ClientsFilter;

@Repository
public class CountDaoImpl implements CountDao{

    @Autowired
	private CountMapper countMapper;

   @Override
	public int getFilteredClientsCount(ClientsFilter clientsFilter) {
		return countMapper.getFilteredClientsCount(clientsFilter);
	}
    @Override
	public int getAllFilteredClientsCount(ClientsFilter clientsFilter) {
		return countMapper.getAllFilteredClientsCount(clientsFilter);
	}
}

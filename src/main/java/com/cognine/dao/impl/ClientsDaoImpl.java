package com.cognine.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.cognine.dao.ClientsDao;
import com.cognine.mapper.ClientsMapper;
import com.cognine.model.Clients;
import com.cognine.model.ClientsFilter;

@Repository
public class ClientsDaoImpl implements ClientsDao{
    @Autowired
	private ClientsMapper clientsMapper;
    @Override
	public List<Clients> getFilteredClients(ClientsFilter clientsFilter) {
		return clientsMapper.getFilteredClients(clientsFilter);
	}

	@Override
	public List<Clients> getAllFilteredClients(ClientsFilter clientsFilter) {
		return clientsMapper.getAllFilteredClients(clientsFilter);
	}

}

package com.cognine.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.cognine.dao.ClientsDao;
import com.cognine.mapper.ClientsMapper;
import com.cognine.model.Clients;
import com.cognine.model.ClientsFilter;
import com.cognine.model.RoleBasedClients;

@Repository
public class ClientsDaoImpl implements ClientsDao{
    @Autowired
	private ClientsMapper clientsMapper;
    @Override
	public List<Clients> getFilteredClients(ClientsFilter clientsFilter) {
		return clientsMapper.getFilteredClients(clientsFilter);
	}
	@Override
	public List<Clients> getClientsDropdown(RoleBasedClients roleBasedClients) {
		return clientsMapper.getClientsDropdown(roleBasedClients);
	}

	@Override
	public List<Clients> getAllFilteredClients(ClientsFilter clientsFilter) {
		return clientsMapper.getAllFilteredClients(clientsFilter);
	}
	@Override
	public List<Clients> getPmoClients(RoleBasedClients roleBasedClients) {

		return clientsMapper.getPmoClients(roleBasedClients);
	}

	@Override
	public List<Clients> getOtherRolesClients(RoleBasedClients roleBasedClients) {
		return clientsMapper.getOtherRolesClients(roleBasedClients);
	}
	@Override
	public List<Clients> getProjectManagerRoleClients(RoleBasedClients roleBasedClients) {
		return clientsMapper.getProjectManagerRoleClients(roleBasedClients);
	}

	@Override
	public List<Clients> getProjectTechLeadRoleClients(RoleBasedClients roleBasedClients) {
		return clientsMapper.getProjectTechLeadRoleClients(roleBasedClients);
	}

	@Override
	public List<Clients> getOnlyProjectManagerOrTechLeadClients(RoleBasedClients roleBasedClients) {
		return clientsMapper.getOnlyProjectManagerOrTechLeadClients(roleBasedClients);
	}

}

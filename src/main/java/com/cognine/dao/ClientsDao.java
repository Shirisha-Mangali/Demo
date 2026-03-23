package com.cognine.dao;

import com.cognine.model.ClientsFilter;
import com.cognine.model.RoleBasedClients;
import com.cognine.model.Clients;
import java.util.List;

public interface ClientsDao {
    List<Clients> getFilteredClients(ClientsFilter clientsFilter);
    List<Clients> getAllFilteredClients(ClientsFilter clientsFilter);
    List<Clients> getClientsDropdown(RoleBasedClients roleBasedClients);
	List<Clients> getPmoClients(RoleBasedClients roleBasedClients);
	List<Clients> getOtherRolesClients(RoleBasedClients roleBasedClients);
    List<Clients> getProjectManagerRoleClients(RoleBasedClients roleBasedClients);
    List<Clients> getProjectTechLeadRoleClients(RoleBasedClients roleBasedClients);
    List<Clients> getOnlyProjectManagerOrTechLeadClients(RoleBasedClients roleBasedClients);

}

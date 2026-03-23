package com.cognine.service;

import com.cognine.model.Clients;
import com.cognine.model.ClientsFilter;
import com.cognine.model.Pagination;
import com.cognine.model.RoleBasedClients;
import java.util.List;

public interface ClientsService {
    Pagination getFilteredClients(ClientsFilter clientsFilter);
    List<Clients> getClientsDropdown(RoleBasedClients roleBasedClients);
}

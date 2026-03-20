package com.cognine.dao;

import com.cognine.model.ClientsFilter;
import com.cognine.model.Clients;
import java.util.List;

public interface ClientsDao {
    List<Clients> getFilteredClients(ClientsFilter clientsFilter);
    List<Clients> getAllFilteredClients(ClientsFilter clientsFilter);
}

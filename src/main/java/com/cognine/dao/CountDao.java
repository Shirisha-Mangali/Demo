package com.cognine.dao;

import com.cognine.model.ClientsFilter;

public interface CountDao {
    int getFilteredClientsCount(ClientsFilter clientsFilter);
    int getAllFilteredClientsCount(ClientsFilter clientsFilter);
}

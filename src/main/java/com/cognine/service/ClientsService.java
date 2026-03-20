package com.cognine.service;

import com.cognine.model.ClientsFilter;
import com.cognine.model.Pagination;

public interface ClientsService {
    Pagination getFilteredClients(ClientsFilter clientsFilter);
}

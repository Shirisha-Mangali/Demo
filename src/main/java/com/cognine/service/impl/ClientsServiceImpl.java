package com.cognine.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognine.dao.ClientsDao;
import com.cognine.dao.CountDao;
import com.cognine.model.Clients;
import com.cognine.model.Pagination;
import com.cognine.model.ClientsFilter;
import com.cognine.service.ClientsService;
import java.util.List;

@Service
public class ClientsServiceImpl implements ClientsService {
    @Autowired
	private ClientsDao clientsDao;
    @Autowired
	private CountDao countDao;

    @Override
    public Pagination getFilteredClients(ClientsFilter clientsFilter){
        Pagination pagination =new Pagination();
        List<Clients> clients=null;
        int count=0;
        String columnName="createdat";
        String sortType="desc";
        if(clientsFilter.getSortColumn().isEmpty()){
            columnName="c1."+columnName;
            clientsFilter.setSortColumn(columnName);
        }else{
            if(clientsFilter.getSortColumn().equals("country")){
                columnName="c2."+clientsFilter.getSortColumn();
                clientsFilter.setSortColumn(columnName);
            }
            else if(clientsFilter.getSortColumn().equals("regionname")){
                columnName="r."+clientsFilter.getSortColumn();
                clientsFilter.setSortColumn(columnName);
            }
            else{
                columnName="c1."+clientsFilter.getSortColumn();
                clientsFilter.setSortColumn(columnName);
            }
        }
        if(clientsFilter.getSortType().isEmpty()){
            clientsFilter.setSortType(sortType);
        }
        if(clientsFilter.getSearchActive().equals("")){
            clients=clientsDao.getAllFilteredClients(clientsFilter);
            count=countDao.getAllFilteredClientsCount(clientsFilter);
        }
        else{
            clients=clientsDao.getFilteredClients(clientsFilter);
            count=countDao.getFilteredClientsCount(clientsFilter);
        }
        pagination.setData(clients);
        pagination.setCount(count);
        return pagination;
    }
}

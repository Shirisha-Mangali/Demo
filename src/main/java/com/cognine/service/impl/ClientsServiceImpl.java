package com.cognine.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognine.dao.ClientsDao;
import com.cognine.dao.CountDao;
import com.cognine.model.Clients;
import com.cognine.model.Pagination;
import com.cognine.model.RoleBasedClients;
import com.cognine.model.ClientsFilter;
import com.cognine.service.ClientsService;
import com.cognine.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    private List<Clients> getUniqueClients(List<Clients> clients){
	       return	clients.stream()
          .collect(Collectors.toMap(Clients::getId, role -> role, (existing, replacement) -> existing))
         .values()
         .stream()
         .collect(Collectors.toList());
	}
	
	
	private List<Clients> getProjectManagerAndTechLeadClients(RoleBasedClients roleBasedClients) {
		List<Clients> clients = new ArrayList<>();
		if(roleBasedClients.getIsTechLeadOrManager()) {
		 clients.addAll(clientsDao.getOnlyProjectManagerOrTechLeadClients(roleBasedClients));
		} 
		if(roleBasedClients.getIsDevOrQA()) {
		 clients.addAll(clientsDao.getOtherRolesClients(roleBasedClients));
		}
		clients.addAll(clientsDao.getProjectManagerRoleClients(roleBasedClients));
		clients.addAll(clientsDao.getProjectTechLeadRoleClients(roleBasedClients));
		return 	getUniqueClients(clients);
	}


    @Override
    public List<Clients>getClientsDropdown(RoleBasedClients roleBasedClients){
        List<Clients>clients=new ArrayList<>();
        if(roleBasedClients.getRoleName().equals(AppConstants.ROLE_ADMIN)|| roleBasedClients.getRoleName().equals(AppConstants.ROLE_PMO_LEAD) ){
            clients=clientsDao.getClientsDropdown(roleBasedClients);
        }
        else if(roleBasedClients.getRoleName().equals(AppConstants.ROLE_PMO)){
            clients.addAll(clientsDao.getPmoClients(roleBasedClients));
            clients.addAll(clientsDao.getPmoClients(roleBasedClients));
			if(roleBasedClients.getIsTechLeadOrManager()) {
			  clients.addAll(getProjectManagerAndTechLeadClients(roleBasedClients));
			  clients = getUniqueClients(clients);
			}
			if(roleBasedClients.getIsDevOrQA()) {
				clients.addAll(clientsDao.getOtherRolesClients(roleBasedClients));
				clients = getUniqueClients(clients);
			}
        }
        else if(roleBasedClients.getRoleName().equals(AppConstants.ROLE_PROJECT_MANAGER) ||	roleBasedClients.getRoleName().equals(AppConstants.ROLE_TECH_LEAD)) {
			clients.addAll(getProjectManagerAndTechLeadClients(roleBasedClients));	
		}else {
			clients = clientsDao.getOtherRolesClients(roleBasedClients);
		}
		return clients;
    }
}

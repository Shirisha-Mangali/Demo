package com.cognine.contr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import com.cognine.model.Pagination;
import com.cognine.service.ClientsService;
import com.cognine.model.ClientsFilter;
import com.cognine.model.Clients;
import com.cognine.model.RoleBasedClients;
import java.util.List;

@RestController
public class ClientsController {
    @Autowired
	private ClientsService clientsService;

    @PostMapping("/getfilteredclients")
    @Secured("ROLE_ADMIN")
    public Pagination getFilteredClients(@RequestBody ClientsFilter clientsfilter){
        return clientsService.getFilteredClients(clientsfilter);
    }

    @PostMapping("getClientslist")
    public List<Clients>getClientsDropdown(@RequestBody RoleBasedClients roleBasedClients){
        return clientsService.getClientsDropdown(roleBasedClients);
    }
}

package com.cognine.contr;
import com.cognine.service.RolesService;
import com.cognine.model.Employee;
import com.cognine.model.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import com.cognine.utils.Status;
import com.cognine.model.UpdateEmployeeRoles;



@RestController
public class RolesController {
    @Autowired
    private RolesService rolesService;
    @GetMapping("/viewroles")
    @Secured("ROLE_ADMIN")
    public List<Roles>getRoles(){
        return rolesService.getRoles();
    }

	@PostMapping("/updateassignedemployeeroles")
	@Secured("ROLE_ADMIN")
	public Status updateEmployeeRoles(@RequestBody UpdateEmployeeRoles updateEmployeeRoles) {
		return rolesService.updateEmployeeRoles(updateEmployeeRoles);

	}
}

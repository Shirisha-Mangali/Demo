package com.cognine.service;
import java.util.List;
import com.cognine.utils.Status;
import com.cognine.model.Employee;
import com.cognine.model.Roles;
import com.cognine.model.UpdateEmployeeRoles;

public interface RolesService {
    List<Roles> getRoles();


	Status updateEmployeeRoles(UpdateEmployeeRoles updateEmployeeRoles);

}

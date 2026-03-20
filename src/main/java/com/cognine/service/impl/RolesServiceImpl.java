package com.cognine.service.impl;

import java.util.List;
import com.cognine.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;

import com.cognine.model.Employee;
import com.cognine.model.Roles;
import com.cognine.service.RolesService;
import com.cognine.dao.RolesDao;
import org.springframework.stereotype.Service;
import com.cognine.model.UpdateEmployeeRoles;

@Service
public class RolesServiceImpl implements RolesService {
    @Autowired
    private RolesDao rolesDao;
   // Pagination pagination=new Pagination();
    public List<Roles> getRoles(){
        List<Roles> viewRoles = rolesDao.getRoles();
		return viewRoles;
    }

	@Override
	public Status updateEmployeeRoles(UpdateEmployeeRoles updateEmployeeRoles) {
		Status status = Status.FAILED;
		if (updateEmployeeRoles != null) {
			if (updateEmployeeRoles.getEmployeeId() != 0) {
				if (updateEmployeeRoles.getDeletedRoles().isEmpty()) {
					status = Status.SUCCESS;
				} else {
					for (Roles deleteRole : updateEmployeeRoles.getDeletedRoles()) {
						if (rolesDao.deleteEmployeeRoles(deleteRole, updateEmployeeRoles.getEmployeeId()) == 1) {
							status = Status.SUCCESS;
						} else {
							status = Status.FAILED;
						}
					}
				}
				if (updateEmployeeRoles.getAddedRoles().isEmpty()) {
					status = Status.SUCCESS;
				} else {
					for (Roles addRole : updateEmployeeRoles.getAddedRoles()) {
						if (rolesDao.assignRolesToEmployee(updateEmployeeRoles.getEmployeeId(), addRole) > 0) {
							status = Status.SUCCESS;
						} else {
							status = Status.FAILED;
						}
					}
				}
				if (updateEmployeeRoles.getUpdatedRoles().isEmpty()) {
					status = Status.SUCCESS;
				} else {
					for (Roles editRole : updateEmployeeRoles.getUpdatedRoles()) {

						if (editRole.isPrimary() == true) {
							if (rolesDao.updatePrimaryRoles(updateEmployeeRoles.getEmployeeId()) > 0) {
								if (rolesDao.editEmployeeRoles(updateEmployeeRoles.getEmployeeId(), editRole) > 0) {
									status = Status.SUCCESS;
								}
							} else {
								status = Status.FAILED;
							}
						} else {
							if (rolesDao.editEmployeeRoles(updateEmployeeRoles.getEmployeeId(), editRole) > 0) {
								status = Status.SUCCESS;
							} else {
								status = Status.FAILED;
							}
						}
					}

				}
			} else {
				status = Status.REQUIRED_VALUES_SHOULD_NOT_BE_NULL;
			}
		} else

		{
			status = Status.EXPECTING_SOME_DATA;
		}
		return status;
	}

}

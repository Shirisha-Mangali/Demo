//src/main/java/com/cognine/config.UserAutheritiesExtractor.java
package com.cognine.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import com.cognine.model.Employee;
import com.cognine.model.Roles;
import com.cognine.service.EmployeeService;

public class UserAutheritiesExtractor implements Converter<Jwt, Collection<GrantedAuthority>> {

	private final EmployeeService employeeService;

	public UserAutheritiesExtractor(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@Override
	public Collection<GrantedAuthority> convert(Jwt jwt) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		Employee employeeData = null;
		try {
			employeeData = employeeService.getEmployeeByEmail(jwt.getClaims().get("unique_name").toString());//extract gmail from jwt 

		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Roles role : employeeData.getEmployeeRoles()) {
			if (role.getRoleName().equals("ROLE_ADMIN")) {
				authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			} else if (role.getRoleName().equals("ROLE_PMO")) {
				authorities.add(new SimpleGrantedAuthority("ROLE_PMO"));
			} else if (role.getRoleName().equals("ROLE_PMO.LEAD")) {
				authorities.add(new SimpleGrantedAuthority("ROLE_PMO.LEAD"));
			} 
			else if (role.getRoleName().equals("ROLE_PROJECT_MANAGER")) {
				authorities.add(new SimpleGrantedAuthority("ROLE_PROJECT_MANAGER"));
			} 
			else if (role.getRoleName().equals("ROLE_TECH.LEAD")) {
				authorities.add(new SimpleGrantedAuthority("ROLE_TECH.LEAD"));
			} else if (role.getRoleName().equals("ROLE_DEVELOPER")) {
				authorities.add(new SimpleGrantedAuthority("ROLE_DEVELOPER"));
			} else {
				authorities.add(new SimpleGrantedAuthority("ROLE_QA"));
			}
		}
		return authorities;
	}
}

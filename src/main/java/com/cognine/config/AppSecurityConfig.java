//src/mian/java/com/cognine/config.AppSecurityConfig.java
package com.cognine.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import com.cognine.service.EmployeeService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class AppSecurityConfig {

	@Autowired
	private EmployeeService employeeService;

	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
		converter.setJwtGrantedAuthoritiesConverter(new UserAutheritiesExtractor(employeeService));
		return converter;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().exceptionHandling().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("*").authenticated().antMatchers(SWAGGER_URL_LIST).permitAll().anyRequest()
				.authenticated().and().oauth2ResourceServer().jwt() // Configure JWT authentication
				.jwtAuthenticationConverter(jwtAuthenticationConverter()); // Use the jwtAuthenticationConverter bean
		return http.build();
	}

	private static final String[] SWAGGER_URL_LIST = { "/swagger-ui/**", "/v3/api-docs/**", "/openapi/**", };

}

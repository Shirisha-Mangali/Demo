package com.cognine.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

//import com.cognine.model.ClientBillingType;
import com.cognine.model.Clients;
import com.cognine.model.ClientsFilter;
import com.cognine.model.RoleBasedClients;

@Mapper
public interface ClientsMapper {

	String addClient = "INSERT INTO public.clients(clientname, address, city, state, active, createdat,"
			+ " updatedat,regionid, countryid, clientbillingtypeid) VALUES (#{clients.clientName}, #{clients.address}, #{clients.city}, "
			+ " #{clients.state}, #{clients.active}," + " now(), now(),#{clients.regionId}, #{countryId}, #{clients.clientBillingTypeId})";
	String getClients = "select c1.id, c1.clientname,c1.createdat as cteated_date, r.region, c1.address, c1.city, c1.state, "
			+ "c1.active,c2.id as countryId,c2.country as countryName from clients c1"
			+ " join country c2 on c2.id = c1.countryid"
			+ "join c1.regionid = r.id where active = true order by c1.createdat desc "
			+ "Offset (#{pageNum} - 1) * #{pageSize} limit #{pageSize}";
	String isClientExists = "select count(1) from clients where clientname = #{clientName}";
	String editClient = " <script> update clients set regionid = #{clients.regionId},address = #{clients.address}, city = #{clients.city},state = #{clients.state},active = #{clients.active}, "
			+ "updatedat = now(), countryid = #{countryId} <if test='clients.clientBillingTypeId != 0'> , clientbillingtypeid =  #{clients.clientBillingTypeId} </if>  where clientname = #{clients.clientName} </script> ";
	String getClientsDropdown = " select id,clientname as clientName,active from clients  order by clientname asc ";

	String getFilteredClients = "SELECT c1.id,c1.clientname, c1.createdat AS created_date,r.id AS regionId,r.regionname, c1.address, c1.city, c1.state, c1.active, "
			+ "    c2.country AS countryName, c2.id AS countryId,cbt.id AS clientbillingtypeid,cbt.clientbillingtype,COUNT(p1.id) AS projectCount "
			+ "FROM clients c1 JOIN country c2 ON c2.id = c1.countryid JOIN regions r ON r.id = c1.regionid LEFT JOIN client_billing_type cbt ON cbt.id = c1.clientbillingtypeid "
			+ "LEFT JOIN projects p1 ON c1.id = p1.clientid WHERE (c1.clientname ILIKE '%${searchClientName}%' AND r.regionname ILIKE '%${searchRegion}%' AND c1.address ILIKE '%${searchAddress}%' "
			+ "AND c1.city ILIKE '%${searchCity}%' AND c1.state ILIKE '%${searchState}%' AND c2.country ILIKE '%${searchCountry}%' AND c1.active::text = #{searchActive}) GROUP BY c1.id, c1.clientname, c1.createdat, r.id, r.regionname,"
			+ "	c1.address, c1.city, c1.state, c1.active, c2.country, c2.id, cbt.id, cbt.clientbillingtype ORDER BY ${sortColumn} ${sortType} OFFSET (#{pageNum} - 1) * #{pageSize} LIMIT #{pageSize}";

	String getAllFilteredClients = "SELECT c1.id, c1.clientname, c1.createdat AS created_date, r.id AS regionId, r.regionname, c1.address, c1.city, c1.state, "
			+ "c1.active, c2.country AS countryName, c2.id AS countryId, cbt.id AS clientbillingtypeid, cbt.clientbillingtype, COUNT(p1.id) AS projectCount "
			+ "FROM clients c1 JOIN country c2 ON c2.id = c1.countryid JOIN regions r ON r.id = c1.regionid "
			+ "LEFT JOIN client_billing_type cbt ON cbt.id = c1.clientbillingtypeid LEFT JOIN projects p1 ON c1.id = p1.clientid WHERE (c1.clientname ILIKE '%${searchClientName}%' "
			+ "AND r.regionname ILIKE '%${searchRegion}%' AND c1.address ILIKE '%${searchAddress}%' AND c1.city ILIKE '%${searchCity}%' AND c1.state ILIKE '%${searchState}%' "
			+ "AND c2.country ILIKE '%${searchCountry}%') GROUP BY c1.id, c1.clientname, c1.createdat, r.id, r.regionname, c1.address, c1.city, c1.state, c1.active, c2.country, c2.id, cbt.id, cbt.clientbillingtype "
			+ "ORDER BY c1.active DESC, ${sortColumn} ${sortType} OFFSET (#{pageNum} - 1) * #{pageSize} LIMIT #{pageSize}";
	
	String getPmoClients = " select distinct c.clientname,c.id, case when ((current_date between p.startdate and p.enddate) or (p.enddate is null and p.startdate <= current_date)) or c.active "
			+ " then true else false END as active FROM employee e join project_pmos pp on e.id = pp.employeeid  "
			+ " join projects p on p.id = pp.projectid join clients c on c.id = p.clientid  where e.id = #{employeeId}  and pp.status = true order by c.clientname asc ";


	String getOtherRolesClients = " select distinct c.clientname,c.id, c.active FROM employee e join project_resources pr on pr.employeeid =  e.id "
			+ "join projects p on p.id = pr.projectid join clients c on c.id = p.clientid where e.id = #{employeeId} and pr.active = true and "
			+ "((current_date BETWEEN p.startdate AND p.enddate) OR (p.enddate IS NULL and p.startdate <= current_date)) AND ((current_date BETWEEN pr.startdate AND pr.enddate) OR (pr.enddate IS NULL and pr.startdate <= current_date)) "
			+ "order by c.clientname asc ";
			
	String getClientBillingTypes = " select * from client_billing_type order by client_billing_type asc ";

	
	String getProjectManagerRoleClients = " select distinct c.clientname,c.id, case when "
			+ "((current_date between p.startdate and p.enddate) or "
			+ "(p.enddate is null and p.startdate <= current_date)) or c.active  "
			+ "then true else false END as active FROM employee e join project_managers pm on e.id = pm.employeeid   "
			+ " join projects p on p.id = pm.projectid join clients c on c.id = p.clientid  where e.id = #{employeeId} "
			+ "and pm.status = true and  ((current_date BETWEEN p.startdate AND p.enddate) OR (p.enddate IS NULL and p.startdate <= current_date)) order by c.clientname asc";
	
	String getProjectTechLeadRoleClients = "select distinct c.clientname,c.id, case when "
			+ "((current_date between p.startdate and p.enddate) or "
			+ "(p.enddate is null and p.startdate <= current_date)) or c.active  "
			+ "then true else false END as active FROM employee e join project_tech_leads ptl on e.id = ptl.employeeid  "
			+ " join projects p on p.id = ptl.projectid join clients c on c.id = p.clientid  where e.id = #{employeeId} "
			+ "and ptl.status = true  and ((current_date BETWEEN p.startdate AND p.enddate) OR (p.enddate IS NULL and p.startdate <= current_date)) order by c.clientname asc ";
	
	String getOnlyProjectManagerOrTechLeadClients = "select distinct c.clientname,c.id, c.active FROM employee e join project_resources pr on pr.employeeid =  e.id "
			+ "join projects p on p.id = pr.projectid join clients c on c.id = p.clientid  "
			+ "join roles r on r.id = pr.roleid where e.id = #{employeeId} and pr.active = true and (r.rolename = 'ROLE_PROJECT_MANAGER' OR r.rolename = 'ROLE_TECH.LEAD') and "
			+ "((current_date BETWEEN p.startdate AND p.enddate) OR (p.enddate IS NULL and p.startdate <= current_date))  and  ((current_date BETWEEN pr.startdate AND pr.enddate) OR (pr.enddate IS NULL and pr.startdate <= current_date))  "
			+ " order by c.clientname asc ";
	
	String deleteClientData = "DELETE FROM clients WHERE id = #{clientId}";

	@Insert(addClient)
	int addClient(Clients clients, int countryId);

	@Select(getClients)
	List<Clients> getClients(int pageNum, int pageSize);

	@Select(isClientExists)
	int isClientExists(String clientName);

	@Update(editClient)
	int editClient(Clients clients, int countryId);

	@Select(getClientsDropdown)
	List<Clients> getClientsDropdown(RoleBasedClients roleBasedClients);

	@Select(getFilteredClients)
	List<Clients> getFilteredClients(ClientsFilter clientsFilter);

	@Select(getAllFilteredClients)
	List<Clients> getAllFilteredClients(ClientsFilter clientsFilter);

	@Select(getPmoClients)
	List<Clients> getPmoClients(RoleBasedClients roleBasedClients);

	@Select(getOtherRolesClients)
	List<Clients> getOtherRolesClients(RoleBasedClients roleBasedClients);

	// @Select(getClientBillingTypes)
	// List<ClientBillingType> getClientBillingTypes();
	
	@Select(getProjectManagerRoleClients)
	List<Clients> getProjectManagerRoleClients(RoleBasedClients roleBasedClients);
	
	@Select(getProjectTechLeadRoleClients)
	List<Clients> getProjectTechLeadRoleClients(RoleBasedClients roleBasedClients);
	
	@Select(getOnlyProjectManagerOrTechLeadClients)
	List<Clients> getOnlyProjectManagerOrTechLeadClients(RoleBasedClients roleBasedClients);
     
	@Delete(deleteClientData)
	int deleteClientData(int clientId);
	
}

package com.cognine.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ProjectConfiguration {

	private int id;
	private String clientName;
	private int clientId;
	private String projectName;
	private String description;
	private Date startDate;
	private Date endDate;
	private int employeeId;
	private int projectId;
	private String notes;
	private String projectAliasEmail;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private boolean active;
	private String employeeName;
	private List<Pmos> addedPmos = new ArrayList<Pmos>();
	private List<Pmos> deletedPmos = new ArrayList<Pmos>();
	private List<Pmos> editPmos = new ArrayList<Pmos>();
	private List<Pmos> projectPmoDetalis = new ArrayList<>();
	private List<ProjectResource> projectResources = new ArrayList<>();
	private boolean status;
	private List<ProjectAttachments> addedAttachments = new ArrayList<ProjectAttachments>();
	private List<ProjectAttachments> removedAttachments = new ArrayList<ProjectAttachments>();
	private List<ProjectAttachments> projectAttachments;
	private List<TechStack> projectTechStacks = new ArrayList<TechStack>();
	private List<TechStack> addedProjectTechStacks = new ArrayList<TechStack>();
	private List<TechStack> removedProjectTechStacks = new ArrayList<TechStack>();

	private List<ProjectRole> addedProjectTechLeads = new ArrayList<ProjectRole>();
	private List<ProjectRole> updateProjectTechLeads = new ArrayList<ProjectRole>();
	private List<ProjectRole> projectTechLeads = new ArrayList<ProjectRole>();
	private List<ProjectRole> addedProjectManagers = new ArrayList<ProjectRole>();
	private List<ProjectRole> updateProjectManagers = new ArrayList<ProjectRole>();
	private List<ProjectRole> projectManagers = new ArrayList<ProjectRole>();

	private int hoursPerDay;

	@JsonInclude(JsonInclude.Include.ALWAYS)
	private boolean actualStoryPoints;

	private Pmos pmos = new Pmos();

	public ProjectConfiguration() {
		super();
	}

	public ProjectConfiguration(String clientName, String projectName) {
		super();
		this.clientName = clientName;
		this.projectName = projectName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHoursPerDay() {
		return hoursPerDay;
	}

	public void setHoursPerDay(int hoursPerDay) {
		this.hoursPerDay = hoursPerDay;
	}

	public boolean getActualStoryPoints() {
		return actualStoryPoints;
	}

	public void setActualStoryPoints(boolean actualStoryPoints) {
		this.actualStoryPoints = actualStoryPoints;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public List<Pmos> getAddedPmos() {
		return addedPmos;
	}

	public void setAddedPmos(List<Pmos> addedPmos) {
		this.addedPmos = addedPmos;
	}

	public List<Pmos> getDeletedPmos() {
		return deletedPmos;
	}

	public void setDeletedPmos(List<Pmos> deletedPmos) {
		this.deletedPmos = deletedPmos;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public List<Pmos> getEditPmos() {
		return editPmos;
	}

	public void setEditPmos(List<Pmos> editPmos) {
		this.editPmos = editPmos;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getProjectAliasEmail() {
		return projectAliasEmail;
	}

	public void setProjectAliasEmail(String projectAliasEmail) {
		this.projectAliasEmail = projectAliasEmail;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Pmos getPmos() {
		return pmos;
	}

	public void setPmos(Pmos pmos) {
		this.pmos = pmos;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<Pmos> getProjectPmoDetalis() {
		return projectPmoDetalis;
	}

	public void setProjectPmoDetalis(List<Pmos> projectPmoDetalis) {
		this.projectPmoDetalis = projectPmoDetalis;
	}

	public List<ProjectResource> getProjectResources() {
		return projectResources;
	}

	public void setProjectResources(List<ProjectResource> projectResources) {
		this.projectResources = projectResources;
	}

	public List<ProjectAttachments> getAddedAttachments() {
		return addedAttachments;
	}

	public void setAddedAttachments(List<ProjectAttachments> addedAttachments) {
		this.addedAttachments = addedAttachments;
	}

	public List<ProjectAttachments> getRemovedAttachments() {
		return removedAttachments;
	}

	public void setRemovedAttachments(List<ProjectAttachments> removedAttachments) {
		this.removedAttachments = removedAttachments;
	}

	public List<ProjectAttachments> getProjectAttachments() {
		return projectAttachments;
	}

	public void setProjectAttachments(List<ProjectAttachments> projectAttachments) {
		this.projectAttachments = projectAttachments;
	}

	public List<TechStack> getProjectTechStacks() {
		return projectTechStacks;
	}

	public void setProjectTechStacks(List<TechStack> projectTechStacks) {
		this.projectTechStacks = projectTechStacks;
	}

	public List<TechStack> getAddedProjectTechStacks() {
		return addedProjectTechStacks;
	}

	public void setAddedProjectTechStacks(List<TechStack> addedProjectTechStacks) {
		this.addedProjectTechStacks = addedProjectTechStacks;
	}

	public List<TechStack> getRemovedProjectTechStacks() {
		return removedProjectTechStacks;
	}

	public void setRemovedProjectTechStacks(List<TechStack> removedProjectTechStacks) {
		this.removedProjectTechStacks = removedProjectTechStacks;
	}

	public List<ProjectRole> getAddedProjectTechLeads() {
		return addedProjectTechLeads;
	}

	public void setAddedProjectTechLeads(List<ProjectRole> addedProjectTechLeads) {
		this.addedProjectTechLeads = addedProjectTechLeads;
	}

	public List<ProjectRole> getUpdateProjectTechLeads() {
		return updateProjectTechLeads;
	}

	public void setUpdateProjectTechLeads(List<ProjectRole> updateProjectTechLeads) {
		this.updateProjectTechLeads = updateProjectTechLeads;
	}

	public List<ProjectRole> getAddedProjectManagers() {
		return addedProjectManagers;
	}

	public void setAddedProjectManagers(List<ProjectRole> addedProjectManagers) {
		this.addedProjectManagers = addedProjectManagers;
	}

	public List<ProjectRole> getUpdateProjectManagers() {
		return updateProjectManagers;
	}

	public void setUpdateProjectManagers(List<ProjectRole> updateProjectManagers) {
		this.updateProjectManagers = updateProjectManagers;
	}

	public List<ProjectRole> getProjectTechLeads() {
		return projectTechLeads;
	}

	public void setProjectTechLeads(List<ProjectRole> projectTechLeads) {
		this.projectTechLeads = projectTechLeads;
	}

	public List<ProjectRole> getProjectManagers() {
		return projectManagers;
	}

	public void setProjectManagers(List<ProjectRole> projectManagers) {
		this.projectManagers = projectManagers;
	}

	@Override
	public String toString() {
		return "ProjectConfiguration [id=" + id + ", clientName=" + clientName + ", clientId=" + clientId
				+ ", projectName=" + projectName + ", description=" + description + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", employeeId=" + employeeId + ", projectId=" + projectId + ", notes="
				+ notes + ", projectAliasEmail=" + projectAliasEmail + ", active=" + active + ", employeeName="
				+ employeeName + ", addedPmos=" + addedPmos + ", deletedPmos=" + deletedPmos + ", editPmos=" + editPmos
				+ ", projectPmoDetalis=" + projectPmoDetalis + ", projectResources=" + projectResources + ", status="
				+ status + ", pmos=" + pmos + "]";
	}

}

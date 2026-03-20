package com.cognine.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Employee {
    private int id;
    private String email;
    private String employeeName;
    private List<Roles> employeeRoles;
    private Date created_date;
    private Date updated_date;
    private int currentReportingHeadId;
    private Date doj;
    private String currentReportingHead;
    private String jobTitle;
    private String department;
    private boolean isActive;
    private String newReportingHead;
    private boolean status;
}

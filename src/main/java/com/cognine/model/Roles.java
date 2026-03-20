package com.cognine.model;
import java.util.Date;
import lombok.Data;

@Data
public class Roles {
    private int id;
    private String roleName;
    private String displayRoleName;
    private String description;
    private Date createdAt;
	private Date updatedAt;
	private boolean active;
	private boolean primary; 
}

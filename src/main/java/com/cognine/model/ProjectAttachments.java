package com.cognine.model;

public class ProjectAttachments {
	
	private int attachmentId;
	private String  fileName;
	private byte[] attachment;
	private boolean isActive;
	private String fileType;
	private int  projectId;
	private int projectAttachmentMappingId;
	
	
	public int getAttachmentId() {
		return attachmentId;
	}
	public void setAttachmentId(int attachmentId) {
		this.attachmentId = attachmentId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public byte[] getAttachment() {
		return attachment;
	}
	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}
	public boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	public int getProjectAttachmentMappingId() {
		return projectAttachmentMappingId;
	}
	public void setProjectAttachmentMappingId(int projectAttachmentMappingId) {
		this.projectAttachmentMappingId = projectAttachmentMappingId;
	}
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	
}

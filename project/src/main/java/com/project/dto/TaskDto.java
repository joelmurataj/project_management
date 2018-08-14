package com.project.dto;

import java.io.Serializable;
import java.util.Date;

public class TaskDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private Date start;
	private Integer daysOfWork;
	private String tema;
	private int status;
	private int employeeId;
	private int projectId;
	private String usernameEmployee;
	private String projectName;
	private String statusName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Integer getDaysOfWork() {
		return daysOfWork;
	}
	public void setDaysOfWork(Integer daysOfWork) {
		this.daysOfWork = daysOfWork;
	}
	public String getTema() {
		return tema;
	}
	public void setTema(String tema) {
		this.tema = tema;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public String getUsernameEmployee() {
		return usernameEmployee;
	}
	public void setUsernameEmployee(String usernameEmployee) {
		this.usernameEmployee = usernameEmployee;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
}

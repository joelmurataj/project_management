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
	private boolean active;
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
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((daysOfWork == null) ? 0 : daysOfWork.hashCode());
		result = prime * result + employeeId;
		result = prime * result + projectId;
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		result = prime * result + ((tema == null) ? 0 : tema.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskDto other = (TaskDto) obj;
		if (daysOfWork == null) {
			if (other.daysOfWork != null)
				return false;
		} else if (!daysOfWork.equals(other.daysOfWork))
			return false;
		if (employeeId != other.employeeId)
			return false;
		if (projectId != other.projectId)
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		if (tema == null) {
			if (other.tema != null)
				return false;
		} else if (!tema.equals(other.tema))
			return false;
		return true;
	}
	
	
}

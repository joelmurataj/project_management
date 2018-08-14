package com.project.dto;

import java.io.Serializable;
import java.util.Date;

public class ProjectDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private Date start;
	private int daysOfWork;
	private String tema;
	private int status;
	private int menagerId;
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
	public int getDaysOfWork() {
		return daysOfWork;
	}
	public void setDaysOfWork(int daysOfWork) {
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
	public int getMenagerId() {
		return menagerId;
	}
	public void setMenagerId(int menagerId) {
		this.menagerId = menagerId;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	
}

package com.project.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="project")
public class Project implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, length = 11)
	private int id;
	@Column(name = "tema", nullable = false, length = 45)
	private String tema;
	@Temporal(TemporalType.DATE)
	@Column(name = "date_start", nullable = false)
	private Date start;
	@Column(name = "daysOfWork",length = 5)
	private int daysOfWork;
	@Column(name = "active", nullable = false)
	private boolean active;
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "status", nullable = false)
	private Status status;
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "manager", nullable = false)
	private User manager;
	public Project() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}
	
}

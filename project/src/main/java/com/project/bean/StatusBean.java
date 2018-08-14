package com.project.bean;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;

import com.project.dto.StatusDto;
import com.project.service.StatusService;

@ManagedBean(name = "statusBean")
@ViewScoped
public class StatusBean {

	private ArrayList<StatusDto> statusDtoList;
	@ManagedProperty(value = "#{statusService}")
	private StatusService statusService;
	
	@PostConstruct
	public void init() {
		refresh();
	}
	public void refresh() {
		this.statusDtoList=statusService.getAllStatus();
	}
	public ArrayList<StatusDto> getStatusDtoList() {
		return statusDtoList;
	}
	public void setStatusDtoList(ArrayList<StatusDto> statusDtoList) {
		this.statusDtoList = statusDtoList;
	}
	public StatusService getStatusService() {
		return statusService;
	}
	public void setStatusService(StatusService statusService) {
		this.statusService = statusService;
	}
	
}

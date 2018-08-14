package com.project.bean;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import org.primefaces.event.RowEditEvent;

import com.project.dto.ProjectDto;
import com.project.service.ProjectService;
import com.project.utility.Message;

@ManagedBean(name = "projectBean")
@ViewScoped
public class ProjectBean {

	private ProjectDto projectDto;
	private ProjectDto selectedProject;
	private ArrayList<ProjectDto> projectDtoList;
	private ArrayList<ProjectDto> filteredProjects;
	private Date now = new Date();
	private int id;

	@ManagedProperty(value = "#{projectService}")
	private ProjectService projectService;
	@ManagedProperty(value = "#{userBean}")
	UserBean userBean;

	@PostConstruct
	public void init() {
		projectDto = new ProjectDto();
		refresh();
		if (id != 0) {
			projectDto = projectService.findById(id);
		}
	}

	public void refresh() {
		this.projectDtoList = projectService.getAllProjects(userBean.getUserDto().getId());
	}

	public void addProject() {
		ProjectDto existProject = new ProjectDto();
		existProject = projectService.findByTema(projectDto.getTema());
		projectDto.setMenagerId(userBean.getUserDto().getId());
		if (projectService.existProject(projectDto.getTema()) && existProject == null) {
			if (projectService.add(projectDto)) {
				System.out.println("u shtua");
				// MessagesUtility.addMessage(MessagesUtility.bundle
				// .getString("USER_ADDED"));
				refresh();
				projectDto = new ProjectDto();
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Project u shtua"));
			} else {
				System.out.println("nuk u shtua");
				
				Message.addMessage(Message.bundle.getString("employee_notAdded"), "error");
			}
		} else if (existProject != null) {
			existProject.setDaysOfWork(projectDto.getDaysOfWork());
			existProject.setStart(projectDto.getStart());
			if (projectService.update(existProject)) {
				refresh();
				System.out.println("u shtua");
				refresh();
				projectDto = new ProjectDto();
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Project u shtua"));
			}
		} else {
			System.out.println("ky project ekziston");
			Message.addMessage(Message.bundle.getString("tema_exist"), "error");
		}
	}

	public void deleteProject(int projectId) {

		if (projectService.remove(projectId)) {
			refresh();
			// MessagesUtility.addMessage(MessagesUtility.bundle
			// .getString("USER_DELETED"));
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Ky project u fshi"));
		} else {
			// MessagesUtility.addMessage(MessagesUtility.bundle
			// .getString("USER_NOT_DELETED"));
			Message.addMessage(Message.bundle.getString("employee_notDelete"), "error");
			
		}
	}

	public void saveProjectForEdit(int id) {
		projectDto = projectService.findById(id);
	}

	public void editProject() {
		ProjectDto existProject = new ProjectDto();
		existProject = projectService.findByTema(projectDto.getTema());
		ProjectDto project = projectService.findById(projectDto.getId());
		project.setDaysOfWork(projectDto.getDaysOfWork());
		project.setStart(projectDto.getStart());
		if ((project.getTema().equals(projectDto.getTema()) && !projectService.existProject(projectDto.getTema()))
				|| projectService.existProject(projectDto.getTema()) && existProject == null) {
			project.setTema(projectDto.getTema());
			if (projectService.update(project)) {
				refresh();
				System.out.println("u editua");
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Project Edited"));
			} else {
				Message.addMessage(Message.bundle.getString("project_notEdited"), "error");

			}
		} else {
			System.out.println("ky project ekziston");
			
			Message.addMessage(Message.bundle.getString("tema_exist"), "error");
		}
	}

	public void onRowEdit(int projectId) {
		ProjectDto editProject = projectService.findById(projectId);
		editProject.setStatus(projectDto.getStatus());
		if (projectService.update(editProject)) {
			refresh();
			System.out.println("u editua");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Project Edited"));
			// MessagesUtility.addMessage(MessagesUtility.bundle
			// .getString("USER_EDITED"));
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Ky project nuk u editua"));
			// MessagesUtility.addMessage(MessagesUtility.bundle
			// .getString("USER_NOT_EDITED"));
		}
		// FacesMessage msg = new FacesMessage("Task Edited");
		// FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edit Cancelled");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public ProjectDto getProjectDto() {
		return projectDto;
	}

	public void setProjectDto(ProjectDto projectDto) {
		this.projectDto = projectDto;
	}

	public ArrayList<ProjectDto> getProjectDtoList() {
		return projectDtoList;
	}

	public void setProjectDtoList(ArrayList<ProjectDto> projectDtoList) {
		this.projectDtoList = projectDtoList;
	}

	public ProjectDto getSelectedProject() {
		return selectedProject;
	}

	public void setSelectedProject(ProjectDto selectedProject) {
		this.selectedProject = selectedProject;
	}

	public ProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getNow() {
		return now;
	}

	public ArrayList<ProjectDto> getFilteredProjects() {
		return filteredProjects;
	}

	public void setFilteredProjects(ArrayList<ProjectDto> filteredProjects) {
		this.filteredProjects = filteredProjects;
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

}

package com.project.bean;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;

import org.primefaces.event.RowEditEvent;

import com.project.dto.ProjectDto;
import com.project.service.ProjectService;
import com.project.utility.Message;

@ManagedBean(name = "projectBean")
@ViewScoped
public class ProjectManagementBean {

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
				refresh();
				projectDto = new ProjectDto();
				Message.addMessage(Message.bundle.getString("PROJECT_ADDED"), "info");

			} else {
				System.out.println("nuk u shtua");

				Message.addMessage(projectDto.getTema() + " :" + Message.bundle.getString("PROJECT_NOTADDED"), "warn");
			}
		} else if (existProject != null) {
			existProject.setDaysOfWork(projectDto.getDaysOfWork());
			existProject.setStart(projectDto.getStart());
			if (projectService.update(existProject)) {
				refresh();
				System.out.println("u shtua");
				refresh();
				projectDto = new ProjectDto();
				Message.addMessage(Message.bundle.getString("PROJECT_ADDED"), "info");
			}
		} else {
			System.out.println("ky project ekziston");
			Message.addMessage(Message.bundle.getString("TEMA_EXIST"), "warn");
		}
	}

	public void deleteProject(int projectId) {

		if (projectService.remove(projectId)) {
			refresh();
			Message.addMessage(Message.bundle.getString("PROJECT_DELETED"), "info");
		} else {
			Message.addMessage(Message.bundle.getString("PROJECT_NOTDELETED"), "warn");

		}
	}

	public void saveProjectForEdit(int id) {
		projectDto = projectService.findById(id);
	}

	public void editProject() {
		ProjectDto existProject = new ProjectDto();
		existProject = projectService.findByTema(projectDto.getTema());
		ProjectDto project = projectService.findById(projectDto.getId());

		if (!project.equals(projectDto)) {
			project.setDaysOfWork(projectDto.getDaysOfWork());
			project.setStart(projectDto.getStart());
			if ((project.getTema().equals(projectDto.getTema()) && !projectService.existProject(projectDto.getTema()))
					|| projectService.existProject(projectDto.getTema()) && existProject == null) {
				project.setTema(projectDto.getTema());
				if (projectService.update(project)) {
					refresh();
					System.out.println("u editua");
					Message.addMessage(project.getTema() + " :" + Message.bundle.getString("PROJECT_EDITED"), "info");

				} else {
					Message.addMessage(Message.bundle.getString("PROJECT_NOTEDITED"), "warn");

				}
			} else {
				System.out.println("ky project ekziston");

				Message.addMessage(project.getTema() + " :" + Message.bundle.getString("TEMA_EXIST"), "warn");
			}
		} else {
			Message.addMessage(Message.bundle.getString("NO_CHANGES"), "warn");

		}
	}

	public void onRowEdit(int projectId) {
		ProjectDto editProject = projectService.findById(projectId);
		if (editProject.getStatus() != projectDto.getStatus()) {
			editProject.setStatus(projectDto.getStatus());
			if (projectService.update(editProject)) {
				refresh();
				System.out.println("u editua");
				Message.addMessage(editProject.getTema() + " :" + Message.bundle.getString("PROJECT_EDITED"), "info");
			} else {
				Message.addMessage(Message.bundle.getString("PROJECT_NOTEDITED"), "warn");

			}
		} else {
			Message.addMessage(Message.bundle.getString("NO_CHANGES"), "warn");

		}
	}

	public void onRowCancel(RowEditEvent event) {
		Message.addMessage(Message.bundle.getString("CANCELED"), "info");
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

package com.project.converters;

import java.util.ArrayList;
import java.util.List;

import com.project.dto.ProjectDto;
import com.project.entity.Project;
import com.project.entity.Status;
import com.project.entity.User;

public class ProjectConverter {
	private ProjectConverter() {
	}

	public static Project toProject(ProjectDto projectDto) {
		if (projectDto != null) {
			Project project = new Project();
			project.setTema(projectDto.getTema());
			project.setStart(projectDto.getStart());
			project.setDaysOfWork(projectDto.getDaysOfWork());
			Status status = new Status();
			status.setId(1);
			project.setStatus(status);
			project.setActive(false);
			User manager = new User();
			manager.setId(projectDto.getMenagerId());
			project.setManager(manager);
			return project;
		} else {
			return null;
		}
	}

	public static Project toEditProject(ProjectDto projectDto) {
		if (projectDto != null) {
			Project project = new Project();
			project.setId(projectDto.getId());
			project.setTema(projectDto.getTema());
			project.setStart(projectDto.getStart());
			project.setDaysOfWork(projectDto.getDaysOfWork());
			Status status = new Status();
			status.setId(projectDto.getStatus());
			project.setStatus(status);
			User manager = new User();
			manager.setId(projectDto.getMenagerId());
			project.setManager(manager);
			return project;
		} else {
			return null;
		}
	}

	public static ProjectDto toProjectDto(Project project) {
		if (project != null) {
			ProjectDto projectDto = new ProjectDto();
			projectDto.setId(project.getId());
			projectDto.setTema(project.getTema());
			projectDto.setStart(project.getStart());
			projectDto.setDaysOfWork(project.getDaysOfWork());
			projectDto.setStatus(project.getStatus().getId());
			projectDto.setMenagerId(project.getManager().getId());
			projectDto.setStatusName(project.getStatus().getName());
			projectDto.setActive(project.isActive());
			projectDto.setStatusColor(project.getStatus().getColor());
			return projectDto;
		} else {
			return null;
		}

	}

	public static List<ProjectDto> toProjectListDto(List<Project> list) {
		ArrayList<ProjectDto> projectDto = new ArrayList<>();
		if (list != null) {
			for (Project project : list) {
				projectDto.add(toProjectDto(project));
			}
			return projectDto;
		} else {
			return projectDto;
		}
	}

}

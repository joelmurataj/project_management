package com.project.converters;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.project.dto.ProjectDto;
import com.project.entity.Project;
import com.project.entity.Status;
import com.project.entity.User;

public class ProjectConverter {

	public static Project toProject(ProjectDto projectDto) {
		try {
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
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Project toEditProject(ProjectDto projectDto) {
		try {
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
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static ProjectDto toProjectDto(Project project) {
		try {
			if (project != null) {
				ProjectDto projectDto = new ProjectDto();
				projectDto.setId(project.getId());
				projectDto.setTema(project.getTema());
				projectDto.setStart(project.getStart());
				projectDto.setDaysOfWork(project.getDaysOfWork());
				projectDto.setStatus(project.getStatus().getId());
				projectDto.setMenagerId(project.getManager().getId());
				projectDto.setStatusName(project.getStatus().getName());
				return projectDto;
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	private static java.util.Date toUtilDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");

		try {
			Date dateFromString = format.parse(date);
			return new java.util.Date(dateFromString.getTime());
		} catch (Exception e) {
			return null;
		}

	}

	private static String toDate(java.sql.Timestamp date) {
		SimpleDateFormat dateformat2 = new SimpleDateFormat("M/dd/yyyy hh:mm:ss");

		try {

			return dateformat2.format(date);

		} catch (Exception e) {

			return null;

		}

	}
}

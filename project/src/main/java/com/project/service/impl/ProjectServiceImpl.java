package com.project.service.impl;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.converters.ProjectConverter;
import com.project.dao.ProjectDao;
import com.project.dto.ProjectDto;
import com.project.entity.Project;
import com.project.service.ProjectService;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectDao projectDao;

	@Override
	@Transactional
	public boolean add(ProjectDto projectDto) {
		return projectDao.add(ProjectConverter.toProject(projectDto));
	}

	@Override
	@Transactional
	public boolean remove(int projectId) {
		if (projectDao.taskOfProject(projectId)) {
			return projectDao.remove(projectId);
		} else
			return false;
	}

	@Override
	@Transactional
	public boolean update(ProjectDto projectDto) {
		return projectDao.update(ProjectConverter.toEditProject(projectDto));
	}

	@Override
	public boolean existProject(String tema) {
		return projectDao.existProject(tema);
	}

	@Override
	public ProjectDto findById(int id) {
		return ProjectConverter.toProjectDto(projectDao.findById(id));
	}

	@Override
	public ArrayList<ProjectDto> getAllProjects(int idMenager) {
		ArrayList<ProjectDto> projectDtoList = new ArrayList<ProjectDto>();
		ArrayList<Project> projectList = projectDao.getAllProjects(idMenager);
		for (int i = 0; i < projectList.size(); i++) {
			projectDtoList.add(ProjectConverter.toProjectDto(projectList.get(i)));
		}

		return projectDtoList;
	}

	@Override
	public ProjectDto findByTema(String tema) {
		return ProjectConverter.toProjectDto(projectDao.findByTema(tema));
	}

}

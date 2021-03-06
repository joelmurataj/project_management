package com.project.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.converters.ProjectConverter;
import com.project.dao.ProjectDao;
import com.project.dto.ProjectDto;
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
		return projectDao.remove(projectId);
	}

	@Override
	@Transactional
	public boolean update(ProjectDto projectDto) {
		return projectDao.update(ProjectConverter.toEditProject(projectDto));
	}

	@Override
	public ProjectDto existProject(String tema) {
		return ProjectConverter.toProjectDto(projectDao.existProject(tema));
	}

	@Override
	public ProjectDto findById(int id) {
		return ProjectConverter.toProjectDto(projectDao.findById(id));
	}

	@Override
	public List<ProjectDto> getAllProjects(int idManager) {
		return ProjectConverter.toProjectListDto(projectDao.getAllProjects(idManager));

	}

}

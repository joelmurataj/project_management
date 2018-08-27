package com.project.service;

import java.util.List;

import com.project.dto.ProjectDto;


public interface ProjectService {

	public boolean add(ProjectDto project);
	public boolean remove(int projectId);
	public boolean update(ProjectDto project);
	public ProjectDto existProject( String tema);
	
	public ProjectDto findById(int id);
	public List<ProjectDto> getAllProjects(int idMenager);
}

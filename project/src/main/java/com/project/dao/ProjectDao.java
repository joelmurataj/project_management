package com.project.dao;

import java.util.List;

import com.project.entity.Project;

public interface ProjectDao {

	public boolean add(Project project);
	public boolean remove(int projectId);
	public boolean update(Project project);
	public Project existProject(String tema);
	
	public Project findById(int id);
	public List<Project> getAllProjects(int menagerId);
}

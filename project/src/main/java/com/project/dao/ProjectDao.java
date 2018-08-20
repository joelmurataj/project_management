package com.project.dao;

import java.util.ArrayList;

import com.project.entity.Project;

public interface ProjectDao {

	public boolean add(Project project);
	public boolean remove(int projectId);
	public boolean update(Project project);
	public boolean existProject(String tema);
	public Project findByTema(String tema);
	
	public Project findById(int id);
	public ArrayList<Project> getAllProjects(int menagerId);
}

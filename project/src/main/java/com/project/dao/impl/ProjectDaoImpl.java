package com.project.dao.impl;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.project.dao.ProjectDao;
import com.project.entity.Project;
import com.project.entity.Task;

@Repository(value = "projectDao")
public class ProjectDaoImpl implements ProjectDao {

	private static final Logger logger = LogManager.getLogger(ProjectDaoImpl.class.getName());

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public boolean add(Project project) {
		try {
			logger.debug("manager {} adding project {}.", project.getManager().getUsername(), project.getTema());
			entityManager.persist(project);
			logger.debug("project added succesfuly");
			return true;
		} catch (Exception e) {
			logger.error("Error adding project:" + e.getMessage());
			return false;
		}
	}

	@Override
	public boolean remove(int projectId) {
		try {
			Project project = entityManager.find(Project.class, projectId);
			logger.debug("manager {} deleting project {}", project.getManager().getUsername(), project.getTema());
			if (taskOfProject(projectId)) {
				entityManager.createQuery("update Project project set project.active=1 where project.id=:id")
						.setParameter("id", projectId).executeUpdate();
				logger.debug("project deleted succesfuly");
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error("Error deleting project:" + e.getMessage());
			return false;
		}
	}

	public boolean taskOfProject(int projectId) {
		try {
			Project project = entityManager.find(Project.class, projectId);
			logger.debug("finding tasks of project {}", project.getTema());
			ArrayList<Task> tasks = (ArrayList<Task>) entityManager
					.createQuery("select task from Task task where task.project.id=:id and task.active=0", Task.class)
					.setParameter("id", projectId).getResultList();
			if (tasks.isEmpty()) {
				logger.debug("project has no task");

				return true;
			}
			logger.debug("tasks of project retrieved" + tasks);

			return false;

		} catch (Exception e) {
			logger.error("error finding tasks of project: " + e.getMessage());
			return false;
		}
	}

	public boolean conflicts(Project project) {
		try {
			logger.debug("finding task with conflict date with project {}", project.getTema());
			ArrayList<Task> task = (ArrayList<Task>) entityManager.createQuery(
					"select task from Task task where task.project.id=:projectId and (ADDDATE(:start,:daysOfWork)<ADDDATE(task.start,task.daysOfWork) or :start>task.start) and task.active=0",
					Task.class).setParameter("projectId", project.getId()).setParameter("start", project.getStart())
					.setParameter("daysOfWork", project.getDaysOfWork()).getResultList();
			if (task.isEmpty()) {
				logger.debug("project {} dont conflict", project.getTema());
				return false;
			} else {
				logger.debug("project {} conflict with his tasks", project.getTema());
				return true;
			}
		} catch (Exception e) {
			logger.error("error finding conflicts: " + e.getMessage());
			return true;

		}
	}

	@Override
	public boolean update(Project project) {
		try {
			logger.debug("manager{} editing project {}", project.getManager().getUsername(), project.getTema());
			if (!conflicts(project)) {
				entityManager.merge(project);
				logger.debug("project edited succesfuly");
				return true;
			} else
				return false;
		} catch (Exception e) {
			logger.error("error editing project{}", project.getTema() + ": " + e.getMessage());
			return false;

		}
	}

	@Override
	public Project existProject(String tema) {
		try {
			logger.debug("finding project with tema {}", tema);
			Project project = (Project) entityManager
					.createQuery("Select project From Project project Where project.tema=:tema",
							Project.class)
					.setParameter("tema", tema).getSingleResult();

			logger.debug("this tema{} exist", tema);
			return project;

		} catch (Exception e) {
			logger.error("error finding project with tema {}", tema + ": " + e.getMessage());
			return null;

		}
	}

	@Override
	public Project findById(int id) {
		try {
			logger.debug("finding project");
			Project project = entityManager.find(Project.class, id);
			logger.debug("manager {} found project{}", project.getManager().getUsername(), project.getTema());
			return project;
		} catch (Exception e) {
			logger.error("error finding project" + e.getMessage());
			return null;
		}
	}

	@Override
	public ArrayList<Project> getAllProjects(int menagerId) {
		try {
			logger.debug("finding all projects for menager");
			ArrayList<Project> projects = (ArrayList<Project>) entityManager
					.createQuery("select project from Project project where project.manager.id=:menagerId and active=0",
							Project.class)
					.setParameter("menagerId", menagerId).getResultList();
			if (projects.isEmpty()) {
				logger.debug("not any project for manager");
				return null;
			}
			logger.debug("projects of manager retrieved: " + projects);
			return projects;

		} catch (Exception e) {
			logger.error("error finding projects of menager: " + e.getMessage());
			return null;
		}
	}



}

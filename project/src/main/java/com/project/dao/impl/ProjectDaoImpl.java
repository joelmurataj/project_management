package com.project.dao.impl;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.project.dao.ProjectDao;
import com.project.entity.Project;
import com.project.entity.Task;

@Repository(value = "projectDao")
@Scope("singleton")
@Component
public class ProjectDaoImpl implements ProjectDao {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public boolean add(Project project) {
		try {
			entityManager.persist(project);
			System.out.println("User was added!");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean remove(int projectId) {
		try {
			entityManager.createQuery("update Project project set project.active=1 where project.id=:id")
					.setParameter("id", projectId).executeUpdate();
			System.out.println("User was removed!");
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean taskOfProject(int projectId) {
		try {
			ArrayList<Task> tasks = (ArrayList<Task>) entityManager
					.createQuery("select task from Task task where task.project.id=:id", Task.class).setParameter("id", projectId)
					.getResultList();
			System.out.println("User was removed!");
			if (tasks.isEmpty()) {
				return true;
			}
			return false;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Project project) {
		try {
			ArrayList<Task> task = (ArrayList<Task>) entityManager.createQuery(
					"select task from Task task where task.project.id=:projectId and (ADDDATE(:start,:daysOfWork)<ADDDATE(task.start,task.daysOfWork) or :start>task.start) and task.active=0",
					Task.class).setParameter("projectId", project.getId()).setParameter("start", project.getStart())
					.setParameter("daysOfWork", project.getDaysOfWork()).getResultList();
			// log.info("Editing the user!");
			if (task.isEmpty()) {
				System.out.println("bosh");
				entityManager.merge(project);
				// log.info("User edited!");
				return true;
			} else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("User failed to add! Message " + e.getMessage());
			return false;

		}
	}

	@Override
	public boolean existProject(String tema) {
		try {

			ArrayList<Project> projects = (ArrayList<Project>) entityManager
					.createQuery("Select project From Project project Where project.tema=:tema AND active=0",
							Project.class)
					.setParameter("tema", tema).getResultList();

			if (projects.isEmpty()) {
				return true;
			} else
				System.out.println("Kjo teme ekziston");
			return false;

		} catch (Exception e) {
			return false;

		}
	}

	@Override
	public Project findById(int id) {
		try {
			Project project = entityManager.find(Project.class, id);
			return project;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ArrayList<Project> getAllProjects(int menagerId) {

		try {

			ArrayList<Project> projects = (ArrayList<Project>) entityManager
					.createQuery("select project from Project project where project.manager.id=:menagerId and active=0",
							Project.class)
					.setParameter("menagerId", menagerId).getResultList();
			if (projects.isEmpty()) {
				System.out.println("bosh");
			}
			return projects;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Project findByTema(String tema) {
		try {
			Project project = (Project) entityManager
					.createQuery("Select project From Project project Where project.tema=:tema and active=1",
							Project.class)
					.setParameter("tema", tema).getSingleResult();
			project.setActive(false);
			return project;
		} catch (Exception e) {
			return null;
		}
	}

}

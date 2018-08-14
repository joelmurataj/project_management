package com.project.dao.impl;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.project.dao.TaskDao;
import com.project.entity.Project;
import com.project.entity.Task;

@Repository(value = "taskDao")
@Scope("singleton")
@Component
public class TaskDaoImpl implements TaskDao {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public boolean add(Task task) {
		try {
			ArrayList<Project> project = (ArrayList<Project>) entityManager.createQuery(
					"select project from Project project where project.id=:projectId and ADDDATE(:start,:daysOfWork)>ADDDATE(project.start,project.daysOfWork) and project.active=0",
					Project.class).setParameter("projectId", task.getProject().getId())
					.setParameter("start", task.getStart()).setParameter("daysOfWork", task.getDaysOfWork())
					.getResultList();
			if (project.isEmpty()) {
				entityManager.persist(task);
				System.out.println("Task was added!");
				return true;
			}
			System.out.println("tasku nuk duhet te mbaroje para dates se mbarimit te projectit");
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean remove(int taskId) {
		try {
			entityManager.createQuery("update Task task set task.active=1 where task.id=:id").setParameter("id", taskId)
					.executeUpdate();
			System.out.println("Task was removed!");
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Task task) {
		try {
			// log.info("Editing the user!");
			ArrayList<Project> project = (ArrayList<Project>) entityManager.createQuery(
					"select project from Project project where project.id=:projectId and (ADDDATE(:start,:daysOfWork)>ADDDATE(project.start,project.daysOfWork) or :start<project.start)",
					Project.class).setParameter("projectId", task.getProject().getId())
					.setParameter("start", task.getStart()).setParameter("daysOfWork", task.getDaysOfWork())
					.getResultList();
			if (project.isEmpty()) {
				entityManager.merge(task);
				// log.info("User edited!");
				return true;
			}
			return false;
		} catch (Exception e) {
			// log.error("User failed to add! Message " + e.getMessage());
			return false;

		}
	}

	@Override
	public boolean existTask(String tema) {
		try {

			ArrayList<Task> task = (ArrayList<Task>) entityManager
					.createQuery("Select task From Task task Where task.tema=:tema and task.active=0", Task.class)
					.setParameter("tema", tema).getResultList();

			if (task.isEmpty()) {
				return true;
			} else
				System.out.println("Kjo teme ekziston");
			return false;

		} catch (Exception e) {
			return false;

		}
	}

	@Override
	public Task findByTema(String tema) {
		try {
			Task task = (Task) entityManager
					.createQuery("Select task From Task task Where task.tema=:tema and active=1", Task.class)
					.setParameter("tema", tema).getSingleResult();
			task.setActive(false);
			return task;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Task findById(int id) {
		try {
			Task task = entityManager.find(Task.class, id);
			return task;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ArrayList<Task> getAllTasks(int idMenager) {
		try {
			ArrayList<Task> tasks = (ArrayList<Task>) entityManager
					.createQuery("Select tasks From Task tasks Where tasks.project.manager.id=:Manager and active=0",
							Task.class)
					.setParameter("Manager", idMenager).getResultList();

			return tasks;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public ArrayList<Task> getAllTasksForEmployee(int idEmployee) {
		try {
			ArrayList<Task> tasks = (ArrayList<Task>) entityManager
					.createQuery("select task from Task task where task.employee.id=:idEmployee and active=0")
					.setParameter("idEmployee", idEmployee).getResultList();

			return tasks;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public ArrayList<Task> getAllTasksFromProject(int idProject) {
		try {
			ArrayList<Task> tasks = (ArrayList<Task>) entityManager
					.createQuery("select task from Task task where task.project.id=:idProject and active=0")
					.setParameter("idProject", idProject).getResultList();

			return tasks;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public ArrayList<Task> filter(String employeeUsername, int managerId, String projectTema) {
		try {
			// log.info("Filtering the tasks!");
			ArrayList<Task> resultList = (ArrayList<Task>) entityManager
					.createQuery("select task from Task task " + "where task.project.tema LIKE :tema "
							+ "AND task.employee.username LIKE :employeeUsername "
							+ "and task.project.manager.id=:userId " + "and task.active=0")
					.setParameter("employeeUsername", "%" + employeeUsername + "%")
					.setParameter("tema", "%" + projectTema + "%").setParameter("userId", managerId).getResultList();
			if (resultList.isEmpty()) {
				return null;
			}
			return resultList;
		} catch (Exception e) {
			// log.error("Filtering the tasks failed! Message " + e.getMessage());
			return null;
		}
	}

	@Override
	public ArrayList<Task> filterForEmployee(String projectTema, int employeeId) {
		try {
			// log.info("Filtering the tasks!");
			ArrayList<Task> resultList = (ArrayList<Task>) entityManager
					.createQuery("select task from Task task " + "where task.tema LIKE :tema "
							+ "and task.employee.id=:userId " + "and task.active=0")
					.setParameter("tema", "%" + projectTema + "%").setParameter("userId", employeeId).getResultList();
			if (resultList.isEmpty()) {
				return null;
			}
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Filtering the tasks failed! Message " + e.getMessage());
			return null;
		}
	}
}

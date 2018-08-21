package com.project.dao.impl;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

	private static final Logger logger = LogManager.getLogger(TaskDaoImpl.class.getName());

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public boolean add(Task task) {
		try {
			logger.debug("adding task{}", task.getTema());

			if (!conflicts(task)) {
				entityManager.persist(task);
				logger.debug("task was added");
				return true;
			}
			logger.debug("task was not added because there are conflicts");
			return false;
		} catch (Exception e) {
			logger.error("error adding task:" + e.getMessage());
			return false;
		}
	}

	@Override
	public boolean remove(int taskId) {
		try {
			Task task = findById(taskId);
			logger.debug("manager{} deleting task{}", task.getEmployee().getManagedBy().getUsername(), task.getTema());
			entityManager.createQuery("update Task task set task.active=1 where task.id=:id").setParameter("id", taskId)
					.executeUpdate();
			logger.debug("task{} was deleted", task.getTema());
			return true;

		} catch (Exception e) {
			logger.error("error deleting task" + e.getMessage());
			return false;
		}
	}

	@Override
	public boolean update(Task task) {
		try {
			logger.debug("editing task{}", task.getTema());
			if (!conflicts(task)) {
				entityManager.merge(task);
				logger.debug("task{} was edited", task.getTema());
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error editing task:" + e.getMessage());
			return false;

		}
	}

	@Override
	public Task existTask(String tema) {
		try {
			logger.debug("finding task not deleted by tema");
			Task task = (Task) entityManager
					.createQuery("Select task From Task task Where task.tema=:tema", Task.class)
					.setParameter("tema", tema).getSingleResult();

			logger.debug("task found");
			return task;

		} catch (Exception e) {
			logger.error("error finding task:" + e.getMessage());
			return null;

		}
	}



	@Override
	public Task findById(int id) {
		try {
			logger.debug("finding task by id");
			Task task = entityManager.find(Task.class, id);
			logger.debug("task was found");
			return task;
		} catch (Exception e) {
			logger.error("error finding task:" + e.getMessage());
			return null;
		}
	}

	@Override
	public ArrayList<Task> getAllTasks(int idMenager) {
		try {
			logger.debug("finding tasks of the manager");
			ArrayList<Task> tasks = (ArrayList<Task>) entityManager
					.createQuery("Select tasks From Task tasks Where tasks.project.manager.id=:Manager and active=0",
							Task.class)
					.setParameter("Manager", idMenager).getResultList();
			if (!tasks.isEmpty()) {
				logger.debug("tasks of manager retrieved:" + tasks);
				return tasks;
			} else {
				logger.debug("any task found");
				return null;
			}
		} catch (Exception e) {
			logger.error("error finding tasks of manager:" + e.getMessage());
			return null;
		}

	}

	@Override
	public ArrayList<Task> getAllTasksForEmployee(int idEmployee) {
		try {
			logger.debug("finding tasks for employee");
			ArrayList<Task> tasks = (ArrayList<Task>) entityManager
					.createQuery("select task from Task task where task.employee.id=:idEmployee and active=0")
					.setParameter("idEmployee", idEmployee).getResultList();
			if (!tasks.isEmpty()) {
				logger.debug("tasks retrieved:" + tasks);
				return tasks;
			} else {
				logger.debug("tasks not found");
				return null;
			}

		} catch (Exception e) {
			logger.error("error finding tasks of employee" + e.getMessage());
			return null;
		}

	}

	@Override
	public ArrayList<Task> filter(String employeeUsername, int managerId, String projectTema) {
		try {
			logger.debug("filter list of task for menager");
			ArrayList<Task> resultList = (ArrayList<Task>) entityManager
					.createQuery("select task from Task task " + "where task.project.tema LIKE :tema "
							+ "AND task.employee.username LIKE :employeeUsername "
							+ "and task.project.manager.id=:userId " + "and task.active=0")
					.setParameter("employeeUsername", "%" + employeeUsername + "%")
					.setParameter("tema", "%" + projectTema + "%").setParameter("userId", managerId).getResultList();
			if (resultList.isEmpty()) {
				logger.debug("any task found");
				return null;
			} else {
				logger.debug("tasks retrieved:" + resultList);
				return resultList;
			}
		} catch (Exception e) {
			logger.error("error finding tasks:" + e.getMessage());
			return null;
		}
	}

	@Override
	public ArrayList<Task> filterForEmployee(String taskTema, int employeeId) {
		try {
			logger.debug("filter list of tasks for employee");
			ArrayList<Task> resultList = (ArrayList<Task>) entityManager
					.createQuery("select task from Task task " + "where task.tema LIKE :tema "
							+ "and task.employee.id=:userId " + "and task.active=0")
					.setParameter("tema", "%" + taskTema + "%").setParameter("userId", employeeId).getResultList();
			if (resultList.isEmpty()) {
				logger.debug("not any task found");
				return null;
			} else {
				logger.debug("tasks retrieved:" + resultList);
				return resultList;
			}
		} catch (Exception e) {
			logger.error("error finding tasks:" + e.getMessage());
			return null;
		}
	}

	public boolean conflicts(Task task) {
		try {
			logger.debug("finding conflicts of task{} with project{} date" + task.getTema(),
					task.getProject().getTema());
			ArrayList<Project> project = (ArrayList<Project>) entityManager.createQuery(
					"select project from Project project where project.id=:projectId and (ADDDATE(:start,:daysOfWork)>ADDDATE(project.start,project.daysOfWork)or :start<project.start) and project.active=0",
					Project.class).setParameter("projectId", task.getProject().getId())
					.setParameter("start", task.getStart()).setParameter("daysOfWork", task.getDaysOfWork())
					.getResultList();
			if (project.isEmpty()) {
				logger.debug("no conflicts");
				return false;
			} else {
				logger.debug("conflicts");
				return true;
			}
		} catch (Exception e) {
			logger.error("error finding conflicts:" + e.getMessage());
			return false;
		}
	}

}

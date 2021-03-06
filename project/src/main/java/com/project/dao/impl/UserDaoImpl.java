package com.project.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.stereotype.Repository;

import com.project.dao.UserDao;
import com.project.entity.Task;
import com.project.entity.User;

@Repository(value = "userDao")
public class UserDaoImpl implements UserDao {

	private static final Logger logger = LogManager.getLogger(UserDaoImpl.class.getName());

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public boolean add(User newUser) {
		try {
			logger.debug("manager {} adding user {}.", newUser.getManagedBy().getUsername(), newUser.getUsername());
			entityManager.persist(newUser);
			logger.debug("user added succesfuly");
			return true;
		} catch (RuntimeException e) {
			logger.error("Error adding user:" + e.getMessage());
			return false;
		}
	}

	@Override
	public boolean remove(int userId) {
		try {
			User user = entityManager.find(User.class, userId);
			logger.debug("manager {} deleting user {}", user.getManagedBy().getUsername(), user.getUsername());
			if (taskOfUser(userId)) {
				entityManager.createQuery("update User user set user.active=1 where user.id=:id")
						.setParameter("id", userId).executeUpdate();
				logger.debug("user deleted succesfuly");
				return true;
			} else {
				return false;
			}

		} catch (RuntimeException e) {
			logger.error("Error deleting user:" + e.getMessage());
			return false;
		}
	}

	public boolean taskOfUser(int userId) {
		try {
			User user = entityManager.find(User.class, userId);
			logger.debug("finding stak of user {}", user.getUsername());
			ArrayList<Task> tasks = (ArrayList<Task>) entityManager.createQuery(
					"select task from Task task where task.employee.id=:id and task.status.id=1 and task.active=0",
					Task.class).setParameter("id", userId).getResultList();

			if (tasks.isEmpty()) {
				logger.debug("user has no task");
				return true;
			}
			logger.debug("tasks of user retrieved" + tasks);
			return false;

		} catch (Exception e) {
			logger.error("error getting tasks of user, " + e.getMessage());
			return true;
		}
	}

	@Override
	public boolean update(User user) {
		try {
			logger.debug("updating user{} ", user.getUsername());
			entityManager.merge(user);
			logger.debug("user not updated");
			return true;
		} catch (RuntimeException e) {
			logger.error("error updating user, " + e.getMessage());
			return false;

		}
	}

	@Override
	public User findById(int id) {
		try {
			logger.debug("finding user");
			User user = entityManager.find(User.class, id);
			logger.debug("user {} found", user.getUsername());
			return user;
		} catch (Exception e) {
			logger.error("error finding user:" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<User> getAll(int id) {
		try {
			logger.debug("finding all users for manager");
			ArrayList<User> resultList = (ArrayList<User>) entityManager
					.createQuery("select user from User user where user.managedBy=:managedBy AND active=0")
					.setParameter("managedBy", findById(id)).getResultList();
			if (resultList.isEmpty()) {
				logger.debug("no user found");
				return null;
			} else {
				logger.debug("all users retrieved" + resultList);
				return resultList;
			}

		} catch (RuntimeException e) {
			logger.error("Error getting users:" + e.getMessage());
			return null;
		}
	}

	@Override
	public User exist(String username, String password) {
		try {
			logger.debug("findind if user exist");
			User user = entityManager
					.createQuery("Select user From User user Where user.username=:username and user.active=0",
							User.class)
					.setParameter("username", username).getSingleResult();
			BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
			if (encryptor.checkPassword(password, user.getPassword())) {
				logger.debug("user exist");
				return user;
			} else {
				logger.debug("user dont exist");
				return null;
			}
		} catch (Exception e) {
			logger.error("error finding user:" + e.getMessage());
			return null;

		}
	}

	@Override
	public User existUsername(String username) {
		try {
			logger.debug("finding user by username");
			User user = entityManager
					.createQuery("Select user From User user Where user.username=:username", User.class)
					.setParameter("username", username).getSingleResult();
			logger.debug("user found");
			return user;
		} catch (Exception e) {
			logger.error("error finding user " + e.getMessage());
			return null;

		}
	}

}

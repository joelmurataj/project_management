package com.project.dao.impl;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.project.dao.UserDao;
import com.project.entity.Role;
import com.project.entity.Task;
import com.project.entity.User;

@Repository(value = "userDao")
@Scope("singleton")
@Component
public class UserDaoImpl implements UserDao {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public boolean add(User newUser) {
		try {
			entityManager.persist(newUser);
			System.out.println("User was added!");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean remove(int userId) {
		try {
			entityManager.createQuery("update User user set user.active=1 where user.id=:id").setParameter("id", userId)
					.executeUpdate();
			System.out.println("User was removed!");
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean taskOfUser(int userId) {
		try {
			ArrayList<Task> tasks = (ArrayList<Task>) entityManager
					.createQuery("select task from Task task where task.employee.id=:id",Task.class).setParameter("id", userId)
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
	public boolean update(User user) {
		try {
			// log.info("Editing the user!");
			entityManager.merge(user);
			// log.info("User edited!");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("User failed to add! Message " + e.getMessage());
			return false;

		}
	}

	@Override
	public User findById(int id) {
		try {
			User user = entityManager.find(User.class, id);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ArrayList<User> getAll(int id) {
		try {
			/*
			 * ArrayList<User> users = (ArrayList<User>) entityManager
			 * .createQuery("Select user from User user Where user.managedBy=:id And active=0"
			 * ) .setParameter("id", id).getResultList();
			 */
			return (ArrayList<User>) entityManager
					.createQuery("select user from User user where user.managedBy=:managedBy AND active=0")
					.setParameter("managedBy", findById(id)).getResultList();
			/*
			 * if(users.isEmpty()) { System.out.println("bosh"); return null; } return
			 * users;
			 */

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public ArrayList<User> getAllUsers(int id) {
		try {
			/*
			 * ArrayList<User> users = (ArrayList<User>) entityManager
			 * .createQuery("Select user from User user Where user.managedBy=:id And active=0"
			 * ) .setParameter("id", id).getResultList();
			 */
			return (ArrayList<User>) entityManager
					.createQuery("select user from User user where user.id=:id AND active=0").setParameter("id", id)
					.getResultList();
			/*
			 * if(users.isEmpty()) { System.out.println("bosh"); return null; } return
			 * users;
			 */

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public User exist(String username, String password) {
		try {
			User user = (User) entityManager
					.createQuery("Select user From User user Where user.username=:username", User.class)
					.setParameter("username", username).getSingleResult();
			BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
			if (encryptor.checkPassword(password, user.getPassword())) {
				System.out.println("success");
				return user;
			} else
				System.out.println("Keni gabim passowrdin");
			return null;

		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}

	@Override
	public boolean existUser(String username) {
		try {

			ArrayList<User> users = (ArrayList<User>) entityManager
					.createQuery("Select user From User user Where user.username=:username AND user.active=0",
							User.class)
					.setParameter("username", username).getResultList();
			if (users.isEmpty()) {
				System.out.println("jo");
				return true;
			}
			System.out.println("po");
			return false;

		} catch (Exception e) {
			return false;

		}
	}

	@Override
	public User getLoggedUser(String username) {
		try {
			// log.info("Getting the user from email!");
			return (User) entityManager
					.createQuery("select user from User user where user.username=:username  and active=0")
					.setParameter("username", username).getSingleResult();
		} catch (Exception e) {
			// log.error("User failed to retrieve from email! Message "
			// + e.getMessage());
			return null;
		}

	}

	
	@Override
	public User findByUsername(String username) {
		try {
			User user = (User) entityManager
					.createQuery("Select user From User user Where user.username=:tema and active=1", User.class)
					.setParameter("tema", username).getSingleResult();
			user.setActive(false);
			return user;

		} catch (Exception e) {
			return null;
		}
	}

}

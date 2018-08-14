package com.project.dao;

import java.util.ArrayList;

import com.project.entity.User;

public interface UserDao {

	public boolean add(User user);
	public boolean remove(int userId);
	public boolean update(User user);
	public boolean existUser( String username);
	public boolean taskOfUser(int userId);
	public User findByUsername(String username);
	
	public User findById(int id);
	public ArrayList<User> getAll(int id);
	public User exist(String username, String password);
	public User getLoggedUser(String username);
	public ArrayList<User> getAllUsers(int id);
	
}
package com.project.dao;

import java.util.List;

import com.project.entity.User;

public interface UserDao {

	public boolean add(User user);
	public boolean remove(int userId);
	public boolean update(User user);
	public User existUsername( String username);
	
	public User findById(int id);
	public List<User> getAll(int id);
	public User exist(String username, String password);
	
}

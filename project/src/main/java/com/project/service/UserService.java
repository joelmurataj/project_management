package com.project.service;

import java.util.ArrayList;

import com.project.dto.UserDto;

public interface UserService {

	public boolean add(UserDto user);
	public boolean remove(int userId);
	public boolean update(UserDto user);
	public boolean existUsername( String username);
	public UserDto findByUsername(String username);
	
	public UserDto findById(int id);
	public ArrayList<UserDto> getAll(int id);
	public UserDto exist(String username, String password);
}

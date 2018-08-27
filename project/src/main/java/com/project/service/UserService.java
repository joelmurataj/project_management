package com.project.service;

import java.util.List;

import com.project.dto.UserDto;

public interface UserService {

	public boolean add(UserDto user);
	public boolean remove(int userId);
	public boolean update(UserDto user);
	public UserDto existUsername( String username);
	
	public UserDto findById(int id);
	public List<UserDto> getAll(int id);
	public UserDto exist(String username, String password);
}

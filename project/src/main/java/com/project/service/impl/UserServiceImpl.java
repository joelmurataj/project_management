package com.project.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.converters.UserConverter;
import com.project.dao.UserDao;
import com.project.dto.UserDto;
import com.project.entity.User;
import com.project.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	@Transactional
	public boolean add(UserDto userDto) {
		return userDao.add(UserConverter.toUser(userDto));
	}

	@Override
	@Transactional
	public boolean remove(int userId) {
		if (userDao.taskOfUser(userId)) {
			return userDao.remove(userId);
		} else
			return false;
	}

	@Override
	@Transactional
	public boolean update(UserDto user) {
		return userDao.update(UserConverter.toEditUser(user));
	}

	@Override
	public UserDto findById(int id) {
		return UserConverter.toUserDto(userDao.findById(id));
	}

	@Override
	@Transactional
	public ArrayList<UserDto> getAll(int id) {
		ArrayList<UserDto> userDtoList = new ArrayList<>();
		ArrayList<User> userList = userDao.getAll(id);
		for (int i = 0; i < userList.size(); i++) {
			userDtoList.add(UserConverter.toUserDto(userList.get(i)));
		}

		return userDtoList;
	}

	@Override
	public UserDto exist(String username, String password) {
		User user = userDao.exist(username, password);
		if (user != null) {

			return UserConverter.toUserDto(user);
		} else {
			return null;
		}

	}
	@Override
	public boolean existUser(String username) {
		return userDao.existUser(username);
	}

	@Override
	public UserDto getLoggedUser(String username) {
		User user = userDao.getLoggedUser(username);
		if (user != null) {
			return UserConverter.toUserDto(user);
		} else {
			return null;
		}
	}


	@Override
	public ArrayList<UserDto> getAllUsers(int id) {
		ArrayList<UserDto> userDtoList = new ArrayList<>();
		ArrayList<User> userList = userDao.getAllUsers(id);
		for (int i = 0; i < userList.size(); i++) {
			userDtoList.add(UserConverter.toUserDto(userList.get(i)));
		}

		return userDtoList;
	}

	@Override
	public UserDto findByUsername(String username) {
		return UserConverter.toUserDto(userDao.findByUsername(username));
	}
}

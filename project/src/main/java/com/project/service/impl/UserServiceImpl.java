package com.project.service.impl;

import java.util.List;

import javax.inject.Inject;

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

	@Inject
	private UserDao userDao;

	@Override
	@Transactional
	public boolean add(UserDto userDto) {
		return userDao.add(UserConverter.toUser(userDto));
	}

	@Override
	@Transactional
	public boolean remove(int userId) {
		return userDao.remove(userId);
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
	public List<UserDto> getAll(int id) {
		return UserConverter.toUserListDto(userDao.getAll(id));

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
	public UserDto existUsername(String username) {
		return UserConverter.toUserDto(userDao.existUsername(username));
	}

}

package com.project.converters;

import java.util.ArrayList;

import org.jasypt.util.password.BasicPasswordEncryptor;

import com.project.dto.UserDto;
import com.project.entity.Role;
import com.project.entity.User;

public class UserConverter {

	public static User toUser(UserDto userDto) {
		try {
			if (userDto != null) {
				User user = new User();
				user.setFirstName(userDto.getFirstName());
				user.setLastName(userDto.getLastName());
				user.setUsername(userDto.getUsername());
				BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
				user.setPassword(encryptor.encryptPassword(userDto.getPassword()));
				user.setActive(false);
				Role roli = new Role();
				roli.setId(2);
				user.setRole(roli);
				User manager = new User();
				manager.setId(userDto.getManagedBy());
				user.setManagedBy(manager);
				return user;
			} else
				return null;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static User toEditUser(UserDto userDto) {
		try {
			if (userDto != null) {
				User user = new User();
				user.setId(userDto.getId());
				user.setFirstName(userDto.getFirstName());
				user.setLastName(userDto.getLastName());
				user.setUsername(userDto.getUsername());
				BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
				user.setPassword(encryptor.encryptPassword(userDto.getPassword()));
				user.setActive(false);
				Role roli = new Role();
				roli.setId(userDto.getRoliId());
				user.setRole(roli);
				if(userDto.getManagedBy()!=0) {
				User manager = new User();
				manager.setId(userDto.getManagedBy());
				user.setManagedBy(manager);
				}
				return user;
			} else
				return null;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static UserDto toUserDto(User user) {
		try {
			if (user != null) {
				UserDto userDto = new UserDto();
				userDto.setId(user.getId());
				userDto.setFirstName(user.getFirstName());
				userDto.setLastName(user.getLastName());
				userDto.setUsername(user.getUsername());
				userDto.setPassword(user.getPassword());
				userDto.setActive(user.isActive());
				userDto.setRoliId(user.getRole().getId());
				if (user.getManagedBy() != null) {
					userDto.setManagedBy(user.getManagedBy().getId());
				}
				return userDto;
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public static ArrayList<UserDto> toUserListDto(ArrayList<User> users) {
		ArrayList<UserDto> userDto = new ArrayList<UserDto>();
		if (users != null) {
			for (User user : users) {
				userDto.add(toUserDto(user));
			}
			return userDto;
		} else
			return null;
	}
	
}

package com.project.converters;

import com.project.dto.UserDto;
import com.project.entity.Role;
import com.project.entity.User;

public class UserConverter {

	public static User toUser(UserDto userDto) {
		try {
			if (userDto != null) {
				User user = new User();
				user.setEmer(userDto.getEmer());
				user.setMbiemer(userDto.getMbiemer());
				user.setUsername(userDto.getUsername());
				user.setPassword(userDto.getPassword());
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
				user.setEmer(userDto.getEmer());
				user.setMbiemer(userDto.getMbiemer());
				user.setUsername(userDto.getUsername());
				user.setPassword(userDto.getPassword());
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
				userDto.setEmer(user.getEmer());
				userDto.setMbiemer(user.getMbiemer());
				userDto.setUsername(user.getUsername());
				userDto.setPassword(user.getPassword());
				userDto.setRoliId(user.getRole().getId());
				if (user.getManagedBy() != null) {
					userDto.setManagedBy(user.getManagedBy().getId());
				}
				return userDto;
			} else
				System.out.println("null");
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}

package com.project.bean;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;

import com.project.dto.UserDto;
import com.project.service.UserService;
import com.project.utility.Message;

@ManagedBean(name = "userCrudBean")
@ViewScoped
public class UserManagementBean {

	private UserDto userDto;
	private ArrayList<UserDto> userDtoList;
	private UserDto selectedUser;
	private int id;

	@ManagedProperty(value = "#{userService}")
	private UserService userService;

	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;

	@PostConstruct
	public void init() {
		userDto = new UserDto();
		refresh();

	}

	public void refresh() {
		if (id != 0) {
			userDto = userService.findById(id);
		} else {
			this.userDtoList = userService.getAll(userBean.getUserDto().getId());
		}
	}

	public void addUser() {
		UserDto existUser = userService.findByUsername(userDto.getUsername());
		if (userDto.getConfirmPassword().equals(userDto.getPassword())) {
			if (userService.existUsername(userDto.getUsername()) && existUser == null) {
				userDto.setManagedBy(userBean.getUserDto().getId());
				userDto.setRoliId(2);
				if (userService.add(userDto)) {
					refresh();
					userDto = new UserDto();
					Message.addMessage(Message.bundle.getString("EMPLOYEE_ADDED"), "info");
				} else {
					Message.addMessage(Message.bundle.getString("EMPLOYEE_NOTADDED"), "warn");
				}
			} else if (existUser != null) {
				Message.addMessage(Message.bundle.getString("EMPLOYEE_EXIST_DELETED"), "warn");
			} else {
				Message.addMessage(Message.bundle.getString("EMPLOYEE_EXIST"), "warn");
			}
		}
	}

	public String deleteUser(int userId) {

		if (userService.remove(userId)) {
			refresh();
			userDto=new UserDto();
			Message.addMessage(Message.bundle.getString("EMPLOYEE_DELETE"), "info");
		} else {
			Message.addMessage(Message.bundle.getString("TASK_EXIST"), "warn");
		}
		return null;
	}

	public String editUser() {
		UserDto user = new UserDto();
		if (userDto.getId() != 0) {
			user = userService.findById(userDto.getId());
		}
		if (id != 0) {
			user = userService.findById(id);
		}
		if (user != null) {
			if (user.getRoliId() == 2) {
				if (!userDto.equals(user)) {
					user.setEmer(userDto.getEmer());
					user.setMbiemer(userDto.getMbiemer());
					if (userService.update(user)) {
						refresh();
						Message.addFlushMessage(Message.bundle.getString("EMPLOYEE_EDIT"), "info");
						return "userManagement?faces-redirect=true";

					} else {
						Message.addFlushMessage(Message.bundle.getString("EMPLOYEE_NOTEDITED"), "warn");
						return "userManagement?faces-redirect=true";

					}

				} else {
					Message.addFlushMessage(Message.bundle.getString("NO_CHANGES"), "warn");
					return "userManagement?faces-redirect=true";
				}
			} else {
				Message.addFlushMessage(Message.bundle.getString("EMPLOYEE_NOTEDITED1"), "warn");
				return "userManagement?faces-redirect=true";
			}

		} else {
			Message.addFlushMessage(Message.bundle.getString("EMPLOYEE_NOTEXIST"), "warn");
			return "userManagement?faces-redirect=true";
		}
	}

	public void changePassword() {
		if (userDto.getConfirmPassword().equals(userDto.getPassword())) {
			userBean.getUserDto().setPassword(userDto.getPassword());
			if (userService.update(userBean.getUserDto())) {
				Message.addMessage(Message.bundle.getString("EMPLOYEE_EDIT"), "info");

			} else
				Message.addMessage(Message.bundle.getString("EMPLOYEE_NOTEDITED"), "warn");

		} else
		Message.addMessage(Message.bundle.getString("PASSWORD_NOTEQUAL"), "warn");

	}
	// GETTERS AND SETTERS

	public UserService getUserService() {
		return userService;
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	public ArrayList<UserDto> getUserDtoList() {
		return userDtoList;
	}

	public void setUserDtoList(ArrayList<UserDto> userDtoList) {
		this.userDtoList = userDtoList;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public UserDto getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(UserDto selectedUser) {
		this.selectedUser = selectedUser;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}

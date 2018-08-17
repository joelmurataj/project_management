package com.project.bean;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;

import org.jasypt.util.password.BasicPasswordEncryptor;

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

	public String addUser() {
		UserDto existUser = userService.findByUsername(userDto.getUsername());
		if (userDto.getConfirmPassword().equals(userDto.getPassword())) {
			if (userService.existUsername(userDto.getUsername()) && existUser == null) {
				userDto.setManagedBy(userBean.getUserDto().getId());
				userDto.setRoliId(2);
				BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
				userDto.setPassword(encryptor.encryptPassword(userDto.getPassword()));
				if (userService.add(userDto)) {
					refresh();
					userDto = new UserDto();
					Message.addMessage(Message.bundle.getString("EMPLOYEE_ADDED"), "info");
				} else {
					System.out.println("nuk u shtua");
					Message.addMessage(Message.bundle.getString("EMPLOYEE_NOTADDED"), "warn");
				}
			} else if (existUser != null) {
				userDeleted(existUser);
			} else {
				Message.addMessage(Message.bundle.getString("EMPLOYEE_EXIST"), "warn");
			}
		}
		return "";
	}

	public String deleteUser(int userId) {

		if (userService.remove(userId)) {
			refresh();
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
			System.out.println(selectedUser);
			System.out.println(user);
			System.out.println(userDto);
			if (user.getRoliId() == 2) {
				if (!userDto.equals(user)) {
					user.setEmer(userDto.getEmer());
					user.setMbiemer(userDto.getMbiemer());
					if (userService.update(user)) {
						refresh();
						System.out.println("u editua");
						Message.addFlushMessage(Message.bundle.getString("EMPLOYEE_EDIT"), "info");
						return "userManagement?faces-redirect=true";

					} else {
						Message.addMessage(Message.bundle.getString("EMPLOYEE_NOTEDITED"), "warn");
					}

				} else {
					System.out.println("jan te njejta");
					Message.addFlushMessage(Message.bundle.getString("NO_CHANGES"), "warn");
					return "userManagement?faces-redirect=true";
				}
			} else {
				System.out.println("nuk mund ta editosh ket user");
				Message.addFlushMessage(Message.bundle.getString("EMPLOYEE_NOTEDITED1"), "warn");
				return "userManagement?faces-redirect=true";
			}

		} else {
			System.out.println("ky user nuk ekziston");
			Message.addFlushMessage(Message.bundle.getString("EMPLOYEE_NOTEXIST"), "warn");
			return "userManagement?faces-redirect=true";
		}
		return "";
	}

	public void userDeleted(UserDto existUser) {

		existUser.setEmer(userDto.getEmer());
		existUser.setMbiemer(userDto.getMbiemer());
		if (userService.update(existUser)) {
			refresh();
			System.out.println("u shtua");
			refresh();
			userDto = new UserDto();
			Message.addMessage(Message.bundle.getString("EMPLOYEE_ADDED"), "info");

		} else
			Message.addMessage(Message.bundle.getString("EMPLOYEE_NOTADDED"), "warn");
	}

	public void changePassword() {
		if (userDto.getConfirmPassword().equals(userDto.getPassword())) {
			BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
			userBean.getUserDto().setPassword(encryptor.encryptPassword(userDto.getPassword()));
			if (userService.update(userBean.getUserDto())) {
				System.out.println("user updated");
				Message.addMessage(Message.bundle.getString("EMPLOYEE_EDIT"), "info");

			} else
				Message.addMessage(Message.bundle.getString("EMPLOYEE_NOTEDITED"), "warn");

		} else
			System.out.println("duhet qe confirmpassword te jete njesoj me password");
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

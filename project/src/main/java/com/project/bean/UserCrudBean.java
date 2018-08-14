package com.project.bean;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;

import org.jasypt.util.password.BasicPasswordEncryptor;

import com.project.dto.UserDto;
import com.project.entity.User;
import com.project.service.UserService;
import com.project.utility.Message;

@ManagedBean(name = "userCrudBean")
@ViewScoped
public class UserCrudBean {

	private UserDto userDto;
	private ArrayList<UserDto> userDtoList;
	private ArrayList<UserDto> filteredUsers;
	private UserDto selectedUser;
	private String name;
	private User userEditable;
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
			if (userService.existUser(userDto.getUsername()) && existUser == null) {
				userDto.setManagedBy(userBean.getUserDto().getId());
				userDto.setRoliId(2);
				BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
				userDto.setPassword(encryptor.encryptPassword(userDto.getPassword()));
				if (userService.add(userDto)) {
					System.out.println("u shtua");
					refresh();
					userDto = new UserDto();
					Message.addMessage(Message.bundle.getString("EMPLOYEE_ADDED"), "info");
				} else {
					System.out.println("nuk u shtua");
					Message.addMessage(Message.bundle.getString("EMPLOYEE_NOTADDED"), "info");
				}
			} else if (existUser != null) {
				userDeleted(existUser);
			} else {
				System.out.println("ky user ekziston");
				Message.addMessage(Message.bundle.getString("EMPLOYEE_EXIST"), "info");
			}
		}
	}

	public String deleteUser(int userId) {

		if (userService.remove(userId)) {
			refresh();
			Message.addMessage(Message.bundle.getString("EMPLOYEE_DELETE"), "info");
		} else {
			Message.addMessage(Message.bundle.getString("EMPLOYEE_NOTDELETE"), "error");
		}
		return null;
	}

	public String editUser() {
		UserDto existUser = new UserDto();
		existUser = userService.findByUsername(userDto.getUsername());
		UserDto user = new UserDto();
		if (userDto.getId() != 0) {
			user = userService.findById(userDto.getId());
		}
		if (id != 0) {
			user = userService.findById(id);
		}
		if (user != null) {
			if (user.getRoliId() == 2) {
				user.setEmer(userDto.getEmer());
				user.setMbiemer(userDto.getMbiemer());
				user.setManagedBy(userBean.getUserDto().getId());
				if ((user.getUsername().equals(userDto.getUsername()) && !userService.existUser(userDto.getUsername()))
						|| userService.existUser(userDto.getUsername()) && existUser == null) {
					user.setUsername(userDto.getUsername());
					if (userService.update(user)) {
						refresh();
						System.out.println("u editua");
						Message.addMessage(Message.bundle.getString("EMPLOYEE_EDIT"), "info");
					} else {
						Message.addMessage(Message.bundle.getString("EMPLOYEE_NOTEDITED"), "info");
					}
				} else {
					System.out.println("ky username ekziston");
					Message.addMessage(Message.bundle.getString("EMPLOYEE_EXIST"), "error");
				}
			} else {
				System.out.println("nuk mund ta editosh ket user");
				Message.addMessage(Message.bundle.getString("EMPLOYEE_NOTEDITED1"), "info");
			}
		} else {
			System.out.println("ky user nuk ekziston");
			Message.addMessage(Message.bundle.getString("EMPLOYEE_NOTEXIST"), "info");
		}

		return null;

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
			Message.addMessage(Message.bundle.getString("EMPLOYEE_NOTADDED"), "info");
	}

	public void changePassword() {
		if (userDto.getConfirmPassword().equals(userDto.getPassword())) {
			BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
			userBean.getUserDto().setPassword(encryptor.encryptPassword(userDto.getPassword()));
			if (userService.update(userBean.getUserDto())) {
				System.out.println("user updated");
				Message.addMessage(Message.bundle.getString("EMPLOYEE_EDIT"), "info");

			} else
				Message.addMessage(Message.bundle.getString("EMPLOYEE_NOTEDITED"), "info");

		} else
			System.out.println("duhet qe confirmpassword te jete njesoj me password");
		Message.addMessage(Message.bundle.getString("PASSWORD_NOTEQUAL"), "info");

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserDto getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(UserDto selectedUser) {
		this.selectedUser = selectedUser;
	}

	public ArrayList<UserDto> getFilteredUsers() {
		return filteredUsers;
	}

	public void setFilteredUsers(ArrayList<UserDto> filteredUsers) {
		this.filteredUsers = filteredUsers;
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

	public User getUserEditable() {
		return userEditable;
	}

	public void setUserEditable(User userEditable) {
		this.userEditable = userEditable;
	}

}

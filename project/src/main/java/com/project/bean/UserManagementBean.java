package com.project.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;

import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.util.password.BasicPasswordEncryptor;

import com.project.dto.UserDto;
import com.project.service.UserService;
import com.project.utility.Message;

@ManagedBean(name = "userCrudBean")
@ViewScoped
public class UserManagementBean {

	private UserDto userDto;
	private List<UserDto> userDtoList;
	private UserDto selectedUser;
	private String id;
	private String hide;

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
		this.userDtoList = userService.getAll(userBean.getUserDto().getId());
	}

	public void loadUser() {
		try {
			if (id != null) {
				userDto = userService.findById(Integer.parseInt(id));
				if (userDto != null) {
					if (userDto.getRoliId() == 1 || userDto.isActive()) {
						userDto = null;
					} else {
						// do nothing
					}
				}
			}
		} catch (NumberFormatException e) {
			userDto = null;
		}

	}

	public void addUser() {
		if (userDto.getConfirmPassword().equals(userDto.getPassword())) {
			if (userService.existUsername(userDto.getUsername()) == null) {
				userDto.setManagedBy(userBean.getUserDto().getId());
				userDto.setRoliId(2);
				if (userService.add(userDto)) {
					refresh();
					userDto = new UserDto();
					Message.addMessage(Message.bundle.getString("EMPLOYEE_ADDED"), "info");
				} else {
					Message.addMessage(Message.bundle.getString("EMPLOYEE_NOTADDED"), "warn");
				}
			} else if (!userService.existUsername(userDto.getUsername()).isActive()) {
				Message.addMessage(Message.bundle.getString("EMPLOYEE_EXIST"), "warn");
			} else {
				Message.addMessage(Message.bundle.getString("EMPLOYEE_EXIST_DELETED"), "warn");

			}
		}
	}

	public String deleteUser(int userId) {

		if (userService.remove(userId)) {
			refresh();
			userDto = new UserDto();
			Message.addMessage(Message.bundle.getString("EMPLOYEE_DELETE"), "info");
		} else {
			Message.addMessage(Message.bundle.getString("TASK_EXIST"), "warn");
		}
		return null;
	}

	public String editUser() {
		UserDto user;
		if (id != null) {
			user = userService.findById(Integer.parseInt(id));
		} else {
			user = userService.findById(userDto.getId());
		}
		if (!userDto.equals(user)) {
			user.setFirstName(userDto.getFirstName());
			user.setLastName(userDto.getLastName());
			if (userService.update(user)) {
				refresh();
				Message.addFlushMessage(Message.bundle.getString("EMPLOYEE_EDIT"), "info");
				return "userManagement?faces-redirect=true";

			} else {
				Message.addMessage(Message.bundle.getString("EMPLOYEE_NOTEDITED"), "warn");
			}
		} else {
			Message.addFlushMessage(Message.bundle.getString("NO_CHANGES"), "warn");
			return "userManagement?faces-redirect=true";
		}

		return "";
	}

	public String changePassword() {
		try {
			BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
			if (userDto.getConfirmPassword().equals(userDto.getPassword())) {
				if (encryptor.checkPassword(userDto.getOldPassword(), userBean.getUserDto().getPassword())) {
					if (!userDto.getOldPassword().equals(userDto.getPassword())) {
						UserDto user=userService.findById(userBean.getUserDto().getId());
						user.setPassword(userDto.getPassword());
						if (userService.update(user)) {
							userBean.getUserDto().setPassword(userDto.getPassword());
							Message.addMessage(Message.bundle.getString("PASSWORD_CHANGED"), "info");
							userDto=new UserDto();
						} else {
							Message.addMessage(Message.bundle.getString("PASSWORD_NOTCHANGED"), "warn");
						}
					} else {
						Message.addMessage(Message.bundle.getString("NO_CHANGES"), "warn");

					}
				} else {
					Message.addMessage(Message.bundle.getString("PASSWORD_NOTEQUAL1"), "error");
				}

			} else {
				Message.addMessage(Message.bundle.getString("PASSWORD_NOTEQUAL"), "error");
			}

			return "";
		} catch (EncryptionOperationNotPossibleException e) {
			return "";
		}

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

	public List<UserDto> getUserDtoList() {
		return userDtoList;
	}

	public void setUserDtoList(List<UserDto> userDtoList) {
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHide() {
		return hide;
	}

	public void setHide(String hide) {
		this.hide = hide;
	}
	
}

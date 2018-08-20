package com.project.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.project.dto.UserDto;
import com.project.service.UserService;
import com.project.utility.Message;

@ManagedBean(name = "loginBean")
@RequestScoped
public class LoginBean {
	private String emer;
	private String mbiemer;
	private String username;
	private String password;
	private boolean validCredentials;

	@ManagedProperty(value = "#{userService}")
	UserService userService;

	@ManagedProperty(value = "#{userBean}")
	UserBean userBean;

	public String getEmer() {
		return emer;
	}

	public void setEmer(String emer) {
		this.emer = emer;
	}

	public String getMbiemer() {
		return mbiemer;
	}

	public void setMbiemer(String mbiemer) {
		this.mbiemer = mbiemer;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isValidCredentials() {
		return validCredentials;
	}

	public void setValidCredentials(boolean validCredentials) {
		this.validCredentials = validCredentials;
	}

	public UserService getUserService() {
		return userService;
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

	public LoginBean() {

	}

	public String logIn() {
		if (username != null && password != null) {
			UserDto user = userService.exist(username, password);
			if (user != null) {
				if (user.getRoliId() == 1) {
					userBean.setUserDto(user);
					return "home?faces-redirect=true";
				} else if (user.getRoliId() == 2) {
					userBean.setUserDto(user);
					return "home?faces-redirect=true";
				}
			}else {
				System.out.println("User failed to log in! (User is null)");
				Message.addMessage(Message.bundle.getString("USER_NOTLOGGED"), "error");
				return "";
			}
		} 
		return"";
	}

	public String logOut() {
		userBean.logOut();
		return "/login.xhtml?faces-redirect=true";
	}

}

package com.project.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import org.primefaces.event.RowEditEvent;

import com.project.dto.ProjectDto;
import com.project.dto.TaskDto;
import com.project.service.ProjectService;
import com.project.service.TaskService;
import com.project.utility.Message;

@ManagedBean(name = "taskBean")
@ViewScoped
public class TaskBean {

	private TaskDto taskDto;
	private TaskDto selectedTask;
	private ArrayList<TaskDto> taskDtoList;
	private ProjectDto projectDto;
	private Date now;
	private Date finishDate;
	private int projectId;
	private String projectTema;
	private String employeeName;

	@ManagedProperty(value = "#{taskService}")
	private TaskService taskService;
	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;
	@ManagedProperty(value = "#{projectService}")
	private ProjectService projectService;

	@PostConstruct
	public void init() {
		taskDto = new TaskDto();
		refresh();
	}

	public void filterByName() {
		if (userBean.getUserDto().getRoliId() == 1) {
			this.taskDtoList = taskService.filter(employeeName, userBean.getUserDto().getId(), projectTema);
		} else {
			this.taskDtoList = taskService.filterForEmployee(projectTema, userBean.getUserDto().getId());
		}
	}

	public void refresh() {
		if (projectId != 0) {
			this.taskDtoList = taskService.getAllTaskDtoFromProject(projectId);
		} else
			this.taskDtoList = taskService.getAllTasks(userBean.getUserDto());
	}

	public void addTask() {
		TaskDto existTask = new TaskDto();
		existTask = taskService.findByTema(taskDto.getTema());
		if (taskService.existTask(taskDto.getTema()) && existTask == null) {
			if (taskService.add(taskDto)) {
				System.out.println("u shtua");
				refresh();
				taskDto = new TaskDto();
				Message.addMessage(Message.bundle.getString("task_added"), "info");

			} else {
				System.out.println("nuk u shtua sepse ka date me vone se data e projektit");
				Message.addMessage(Message.bundle.getString("task_notAdded"), "error");

			}
		} else if (existTask != null) {
			existTask.setDaysOfWork(taskDto.getDaysOfWork());
			existTask.setStart(taskDto.getStart());
			if (taskService.update(existTask)) {
				refresh();
				System.out.println("u shtua");
				refresh();
				taskDto = new TaskDto();
				Message.addMessage(Message.bundle.getString("task_added"), "info");

			} else {
				System.out.println("nuk u shtua sepse ka date me vone se data e projektit");
				Message.addMessage(Message.bundle.getString("task_notAdded"), "error");

			}
		} else {
			System.out.println("ky task ekziston");
			Message.addMessage(Message.bundle.getString("tema_exist"), "error");

		}
	}

	public void deleteTask(int taskId) {

		if (taskService.remove(taskId)) {
			refresh();
			Message.addMessage(Message.bundle.getString("task_deleted"), "info");

		} else {
			Message.addMessage(Message.bundle.getString("task_notDeleted"), "error");

		}
	}

	public void saveTaskForEdit(int id) {
		taskDto = taskService.findById(id);
	}

	public void editTask() {
		TaskDto existTask = new TaskDto();
		existTask = taskService.findByTema(taskDto.getTema());
		TaskDto task = taskService.findById(taskDto.getId());
		taskDto.setId(task.getId());
		taskDto.setStatus(task.getStatus());
		if ((task.getTema().equals(taskDto.getTema()) && !taskService.existTask(taskDto.getTema()))
				|| taskService.existTask(taskDto.getTema()) && existTask == null) {
			if (taskService.update(taskDto)) {
				refresh();
				System.out.println("u editua");
				Message.addMessage(Message.bundle.getString("task_edited"), "info");

			} else {
				Message.addMessage(Message.bundle.getString("task_notEdited"), "error");

			}
		} else {
			System.out.println("ky task ekziston");
			Message.addMessage(Message.bundle.getString("tema_exist"), "error");

		}
	}

	public void onProjectChange(int id) {
		if (id != 0) {
			projectDto = projectService.findById(id);
			now = projectDto.getStart();
			Calendar c = Calendar.getInstance();
			c.setTime(now);
			c.add(Calendar.DATE, projectDto.getDaysOfWork()); // number of days to add
			finishDate = c.getTime();
		}
	}

	public void onRowEdit(int taskId) {
		TaskDto taskEdit = taskService.findById(taskId);
		taskEdit.setStatus(taskDto.getStatus());
		if (taskService.update(taskEdit)) {
			refresh();
			System.out.println("u editua");
			Message.addMessage(Message.bundle.getString("task_edited"), "info");

		} else {
			Message.addMessage(Message.bundle.getString("task_notEdited"), "error");

		}
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edit Cancelled");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public TaskDto getTaskDto() {
		return taskDto;
	}

	public void setTaskDto(TaskDto taskDto) {
		this.taskDto = taskDto;
	}

	public TaskDto getSelectedTask() {
		return selectedTask;
	}

	public void setSelectedTask(TaskDto selectedTask) {
		this.selectedTask = selectedTask;
	}

	public ArrayList<TaskDto> getTaskDtoList() {
		return taskDtoList;
	}

	public void setTaskDtoList(ArrayList<TaskDto> taskDtoList) {
		this.taskDtoList = taskDtoList;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public ProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public ProjectDto getProjectDto() {
		return projectDto;
	}

	public void setProjectDto(ProjectDto projectDto) {
		this.projectDto = projectDto;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getProjectTema() {
		return projectTema;
	}

	public void setProjectTema(String projectTema) {
		this.projectTema = projectTema;
	}

}

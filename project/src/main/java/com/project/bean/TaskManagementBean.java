package com.project.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;

import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.project.dto.ProjectDto;
import com.project.dto.TaskDto;
import com.project.service.ProjectService;
import com.project.service.TaskService;
import com.project.utility.Message;

@ManagedBean(name = "taskBean")
@ViewScoped
public class TaskManagementBean {

	private TaskDto taskDto;
	private TaskDto selectedTask;
	private ArrayList<TaskDto> taskDtoList;
	private ProjectDto projectDto;
	private ScheduleModel calendar;
	private ScheduleEvent event = new DefaultScheduleEvent();
	private Date finishDate;

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

	public void onEventSelect(SelectEvent selectEvent) {
		event = (ScheduleEvent) selectEvent.getObject();

	}

	public void filterByName() {
		if (userBean.getUserDto().getRoliId() == 1) {
			this.taskDtoList = taskService.filter(taskDto, userBean.getUserDto().getId());
		} else {
			this.taskDtoList = taskService.filterForEmployee(taskDto, userBean.getUserDto().getId());
		}
	}

	public void refresh() {
			this.taskDtoList = taskService.getAllTasks(userBean.getUserDto());
		if (taskDtoList != null) {
				calendar = new DefaultScheduleModel();

				for (TaskDto taskDto : taskDtoList) {
					Date start = taskDto.getStart();
					Calendar c = Calendar.getInstance();
					c.setTime(start);
					c.add(Calendar.DATE, taskDto.getDaysOfWork()); // number of days to add
					Date end = c.getTime();
					calendar.addEvent(new DefaultScheduleEvent(taskDto.getTema() + "(" + taskDto.getProjectName() + ")",
							start, end));
				}
			}

	}

	public void addTask() {
		if (taskService.existTask(taskDto.getTema()) == null) {
			if (taskService.add(taskDto)) {
				refresh();
				taskDto = new TaskDto();
				Message.addMessage(taskDto.getTema() + " :" + Message.bundle.getString("TASK_ADDED"), "info");

			} else {
				Message.addMessage(Message.bundle.getString("TASK_NOTADDED"), "warn");

			}
		} else if (taskService.existTask(taskDto.getTema()).isActive()) {
			Message.addMessage(taskDto.getTema() + " :" + Message.bundle.getString("TEMA_EXIST_DELETED"), "warn");
		} else {
			Message.addMessage(Message.bundle.getString("TEMA_EXIST"), "warn");

		}
	}

	public void deleteTask(int taskId) {

		if (taskService.remove(taskId)) {
			refresh();
			Message.addMessage(Message.bundle.getString("TASK_DELETED"), "info");

		} else {
			Message.addMessage(Message.bundle.getString("TASK_NOTDELETED"), "warn");

		}
	}

	public void editTask() {
		TaskDto task = taskService.findById(taskDto.getId());

		if (!taskDto.equals(task)) {
			taskDto.setId(task.getId());
			taskDto.setStatus(task.getStatus());
			if (task.getTema().equals(taskDto.getTema()) || taskService.existTask(taskDto.getTema()) == null) {
				if (taskService.update(taskDto)) {
					refresh();
					Message.addMessage(Message.bundle.getString("TASK_EDITED"), "info");

				} else {
					Message.addMessage(Message.bundle.getString("TASK_NOTEDITED"), "warn");

				}
			} else if (taskService.existTask(taskDto.getTema()).isActive()) {
				Message.addMessage(taskDto.getTema() + " :" + Message.bundle.getString("TEMA_EXIST_DELETED"), "warn");
			} else {
				Message.addMessage(Message.bundle.getString("TEMA_EXIST"), "warn");
			}
		} else {
			Message.addMessage(Message.bundle.getString("NO_CHANGES"), "info");

		}
	}

	public void onProjectChange(int id) {
		if (id != 0) {
			projectDto = projectService.findById(id);
			Calendar c = Calendar.getInstance();
			c.setTime(projectDto.getStart());
			c.add(Calendar.DATE, projectDto.getDaysOfWork()); // number of days to add
			finishDate = c.getTime();
		}
	}

	public void onRowEdit(int taskId) {
		TaskDto taskEdit = taskService.findById(taskId);
		if (taskEdit.getStatus() != taskDto.getStatus()) {
			taskEdit.setStatus(taskDto.getStatus());
			if (taskService.update(taskEdit)) {
				refresh();
				Message.addMessage(Message.bundle.getString("TASK_EDITED"), "info");

			} else {
				Message.addMessage(Message.bundle.getString("TASK_NOTEDITED"), "warn");

			}
		} else {
			Message.addMessage(Message.bundle.getString("NO_CHANGES"), "warn");

		}
	}

	public void onRowCancel(RowEditEvent event) {
		Message.addMessage(Message.bundle.getString("CANCELED"), "info");

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

	public ScheduleModel getCalendar() {
		return calendar;
	}

	public void setCalendar(ScheduleModel calendar) {
		this.calendar = calendar;
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

}

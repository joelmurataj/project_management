package com.project.bean;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;

import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
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
	private List<TaskDto> taskDtoList;
	private ProjectDto projectDto;
	private ScheduleModel calendar;
	private Date minDate;
	private Date maxDate;
	private String color;
	private String color1;
	private boolean done;

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
			this.taskDtoList = taskService.filter(taskDto, userBean.getUserDto().getId());
		} else {
			this.taskDtoList = taskService.filterForEmployee(taskDto, userBean.getUserDto().getId());
		}
	}

	public void refresh() {
		this.taskDtoList = taskService.getAllTasks(userBean.getUserDto());
		if (taskDtoList != null) {
			calendar = new DefaultScheduleModel();
			for (TaskDto task : taskDtoList) {
				Date start = task.getStart();
				Calendar c = Calendar.getInstance();
				c.setTime(start);
				c.add(Calendar.DATE, task.getDaysOfWork()); // number of days to add
				Date end = c.getTime();

				if (task.getStatusColor().equals("red")) {
					color = task.getStatusColor();
					calendar.addEvent(new DefaultScheduleEvent(task.getTema() + "(" + task.getProjectName() + ")",
							task.getStart(), end, "red-class"));
				} else {
					color1 = task.getStatusColor();
					calendar.addEvent(new DefaultScheduleEvent(task.getTema() + "(" + task.getProjectName() + ")",
							task.getStart(), end, "green-class"));
				}

			}
		}

	}

	public void addTask() {
		if (taskService.existTask(taskDto.getTema()) == null) {
			if (taskService.add(taskDto)) {
				done = true;
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
			taskDto.setProjectId(task.getProjectId());
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

	public void onRowCancel() {
		Message.addMessage(Message.bundle.getString("CANCELED"), "info");

	}
	public String onComplete() {
		System.out.println(done);
		if(done) {
			return "PF('dlg1').hide()";
		}
		return "";
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

	public List<TaskDto> getTaskDtoList() {
		return taskDtoList;
	}

	public void setTaskDtoList(List<TaskDto> taskDtoList) {
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

	public ScheduleModel getCalendar() {
		return calendar;
	}

	public void setCalendar(ScheduleModel calendar) {
		this.calendar = calendar;
	}

	public Date getMinDate() {
		if (taskDto.getProjectId() != 0) {
			projectDto = projectService.findById(taskDto.getProjectId());
			minDate = projectDto.getStart();
		}
		return minDate;
	}

	public Date getMaxDate() {
		if (taskDto.getProjectId() != 0) {
			projectDto = projectService.findById(taskDto.getProjectId());
			Calendar c = Calendar.getInstance();
			c.setTime(projectDto.getStart());
			c.add(Calendar.DATE, projectDto.getDaysOfWork()); // number of days to add
			maxDate = c.getTime();
		}
		return maxDate;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getColor1() {
		return color1;
	}

	public void setColor1(String color1) {
		this.color1 = color1;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}
	

}

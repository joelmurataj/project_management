package com.project.converters;

import java.util.ArrayList;
import java.util.List;

import com.project.dto.TaskDto;
import com.project.entity.Project;
import com.project.entity.Status;
import com.project.entity.Task;
import com.project.entity.User;

public class TaskConverter {

	private TaskConverter() {
	}

	public static Task toTask(TaskDto taskDto) {
		if (taskDto != null) {
			Task task = new Task();
			task.setTema(taskDto.getTema());
			task.setStart(taskDto.getStart());
			task.setDaysOfWork(taskDto.getDaysOfWork());
			Status status = new Status();
			status.setId(1);
			task.setStatus(status);
			task.setActive(false);
			User employee = new User();
			employee.setId(taskDto.getEmployeeId());
			task.setEmployee(employee);
			Project project = new Project();
			project.setId(taskDto.getProjectId());
			task.setProject(project);
			return task;
		} else {
			return null;
		}
	}

	public static Task toEditTask(TaskDto taskDto) {
		if (taskDto != null) {
			Task task = new Task();
			task.setId(taskDto.getId());
			task.setTema(taskDto.getTema());
			task.setStart(taskDto.getStart());
			task.setDaysOfWork(taskDto.getDaysOfWork());
			Status status = new Status();
			status.setId(taskDto.getStatus());
			task.setStatus(status);
			task.setActive(false);
			User employee = new User();
			employee.setId(taskDto.getEmployeeId());
			task.setEmployee(employee);
			Project project = new Project();
			project.setId(taskDto.getProjectId());
			task.setProject(project);
			return task;
		} else {
			return null;
		}
	}

	public static TaskDto toTaskDto(Task task) {
		if (task != null) {
			TaskDto taskDto = new TaskDto();
			taskDto.setStatusName(task.getStatus().getName());
			taskDto.setStatusColor(task.getStatus().getColor());
			taskDto.setProjectName(task.getProject().getTema());
			taskDto.setUsernameEmployee(task.getEmployee().getUsername());
			taskDto.setId(task.getId());
			taskDto.setTema(task.getTema());
			taskDto.setStart(task.getStart());
			taskDto.setDaysOfWork(task.getDaysOfWork());
			taskDto.setStatus(task.getStatus().getId());
			taskDto.setEmployeeId(task.getEmployee().getId());
			taskDto.setActive(task.isActive());
			taskDto.setProjectId(task.getProject().getId());
			return taskDto;
		} else {
			return null;
		}

	}

	public static List<TaskDto> toTaskListDto(List<Task> list) {
		ArrayList<TaskDto> taskDto = new ArrayList<>();
		if (list != null) {
			for (Task task : list) {
				taskDto.add(toTaskDto(task));
			}
			return taskDto;
		} else {
			return taskDto;
		}
	}

	public static Task toTaskSearch(TaskDto taskDto) {
			if (taskDto != null) {
				Task task = new Task();

				task.setActive(false);
				User employee = new User();
				employee.setUsername(taskDto.getUsernameEmployee());
				task.setEmployee(employee);
				Project project = new Project();
				project.setTema(taskDto.getProjectName());
				task.setProject(project);
				return task;
			} else {
				return null;
			}
	}

	public static Task toTaskSearchByTema(TaskDto taskDto) {
			if (taskDto != null) {
				Task task = new Task();
				task.setTema(taskDto.getTema());
				return task;
			} else {
				return null;
			}
	}
}

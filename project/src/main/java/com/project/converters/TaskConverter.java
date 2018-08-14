package com.project.converters;

import com.project.dto.TaskDto;
import com.project.entity.Project;
import com.project.entity.Status;
import com.project.entity.Task;
import com.project.entity.User;

public class TaskConverter {

	public static Task toTask(TaskDto taskDto) {
		try {
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
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Task toEditTask(TaskDto taskDto) {
		try {
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
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static TaskDto toTaskDto(Task task) {
		try {
			if (task != null) {
				TaskDto taskDto = new TaskDto();
				taskDto.setStatusName(task.getStatus().getName());
				taskDto.setProjectName(task.getProject().getTema());
				taskDto.setUsernameEmployee(task.getEmployee().getUsername());
				taskDto.setId(task.getId());
				taskDto.setTema(task.getTema());
				taskDto.setStart(task.getStart());
				taskDto.setDaysOfWork(task.getDaysOfWork());
				taskDto.setStatus(task.getStatus().getId());
				taskDto.setEmployeeId(task.getEmployee().getId());
				taskDto.setProjectId(task.getProject().getId());
				return taskDto;
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

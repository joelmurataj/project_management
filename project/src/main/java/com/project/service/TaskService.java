package com.project.service;

import java.util.List;

import com.project.dto.TaskDto;
import com.project.dto.UserDto;

public interface TaskService {

	public boolean add(TaskDto taskDto);
	public boolean remove(int taskId);
	public boolean update(TaskDto taskDto);
	public TaskDto existTask(String tema);
	public List<TaskDto> filter(TaskDto taskDto,int managerI);
	public List<TaskDto> filterForEmployee(TaskDto taskDto, int employeeId);
	
	public TaskDto findById(int id);
	public List<TaskDto> getAllTasks( UserDto userDto);
}

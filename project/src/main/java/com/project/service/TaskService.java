package com.project.service;

import java.util.ArrayList;

import com.project.dto.TaskDto;
import com.project.dto.UserDto;

public interface TaskService {

	public boolean add(TaskDto taskDto);
	public boolean remove(int taskId);
	public boolean update(TaskDto taskDto);
	public TaskDto existTask(String tema);
	public ArrayList<TaskDto> filter(TaskDto taskDto,int managerI);
	public ArrayList<TaskDto> filterForEmployee(TaskDto taskDto, int employeeId);
	
	public TaskDto findById(int id);
	public ArrayList<TaskDto> getAllTasks( UserDto userDto);
}

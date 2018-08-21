package com.project.service;

import java.util.ArrayList;

import com.project.dto.TaskDto;
import com.project.dto.UserDto;

public interface TaskService {

	public boolean add(TaskDto taskDto);
	public boolean remove(int taskId);
	public boolean update(TaskDto taskDto);
	public TaskDto existTask(String tema);
	public ArrayList<TaskDto> filter(String employeeUsername,int managerId,String projectTema);
	public ArrayList<TaskDto> filterForEmployee(String employeeUsername, int employeeId);
	
	public TaskDto findById(int id);
	public ArrayList<TaskDto> getAllTasks( UserDto userDto);
}

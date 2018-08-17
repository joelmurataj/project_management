package com.project.service.impl;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.converters.TaskConverter;
import com.project.dao.TaskDao;
import com.project.dto.TaskDto;
import com.project.dto.UserDto;
import com.project.entity.Task;
import com.project.service.TaskService;

@Service("taskService")
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskDao taskDao;

	@Override
	@Transactional
	public boolean add(TaskDto taskDto) {
		return taskDao.add(TaskConverter.toTask(taskDto));
	}

	@Override
	@Transactional
	public boolean remove(int taskId) {
		return taskDao.remove(taskId);
	}

	@Override
	@Transactional
	public boolean update(TaskDto taskDto) {
		return taskDao.update(TaskConverter.toEditTask(taskDto));
	}

	@Override
	@Transactional
	public boolean existTask(String tema) {
		return taskDao.existTask(tema);
	}

	@Override
	public TaskDto findById(int id) {
		return TaskConverter.toTaskDto(taskDao.findById(id));
	}

	@Override
	@Transactional
	public TaskDto findByTema(String tema) {
		return TaskConverter.toTaskDto(taskDao.findByTema(tema));
	}

	@Override
	public ArrayList<TaskDto> getAllTasks(UserDto userDto) {
		ArrayList<TaskDto> taskDtoList = new ArrayList<TaskDto>();
		ArrayList<Task> taskList = new ArrayList<Task>();
		if (userDto.getRoliId() == 1) {
			taskList = taskDao.getAllTasks(userDto.getId());
		} else
			taskList = taskDao.getAllTasksForEmployee(userDto.getId());
		if (taskList != null) {
			for (int i = 0; i < taskList.size(); i++) {
				taskDtoList.add(TaskConverter.toTaskDto(taskList.get(i)));
			}

			return taskDtoList;
		} else
			return null;
	}


	@Override
	public ArrayList<TaskDto> filter(String employeeUsername, int managerId, String projectTema) {
		ArrayList<TaskDto> taskDtoList = new ArrayList<TaskDto>();
		ArrayList<Task> taskList = taskDao.filter(employeeUsername, managerId, projectTema);
		if (taskList != null) {
			for (int i = 0; i < taskList.size(); i++) {
				taskDtoList.add(TaskConverter.toTaskDto(taskList.get(i)));
			}

			return taskDtoList;
		} else
			return null;
	}

	@Override
	public ArrayList<TaskDto> filterForEmployee(String projectTema, int employeeId) {
		ArrayList<TaskDto> taskDtoList = new ArrayList<TaskDto>();
		ArrayList<Task> taskList = taskDao.filterForEmployee(projectTema, employeeId);
		if (taskList != null) {
			for (int i = 0; i < taskList.size(); i++) {
				taskDtoList.add(TaskConverter.toTaskDto(taskList.get(i)));
			}
			return taskDtoList;
		} else
			return null;
	}

}

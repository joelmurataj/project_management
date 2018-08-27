package com.project.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.converters.TaskConverter;
import com.project.dao.TaskDao;
import com.project.dto.TaskDto;
import com.project.dto.UserDto;
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
	public TaskDto existTask(String tema) {
		return TaskConverter.toTaskDto(taskDao.existTask(tema));
	}

	@Override
	public TaskDto findById(int id) {
		return TaskConverter.toTaskDto(taskDao.findById(id));
	}

	@Override
	public List<TaskDto> getAllTasks(UserDto userDto) {
		if (userDto.getRoliId() == 1) {
			return TaskConverter.toTaskListDto(taskDao.getAllTasks(userDto.getId()));
		} else
			return TaskConverter.toTaskListDto(taskDao.getAllTasksForEmployee(userDto.getId()));

	}

	@Override
	public List<TaskDto> filter(TaskDto taskDto, int managerId) {
		return TaskConverter.toTaskListDto(taskDao.filter(TaskConverter.toTaskSearch(taskDto), managerId));
	}

	@Override
	public List<TaskDto> filterForEmployee(TaskDto taskDto, int employeeId) {
		return TaskConverter.toTaskListDto(taskDao.filterForEmployee(TaskConverter.toTaskSearchByTema(taskDto), employeeId));
	}
}

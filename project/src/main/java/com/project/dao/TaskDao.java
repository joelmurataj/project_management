package com.project.dao;

import java.util.List;

import com.project.entity.Task;

public interface TaskDao {

	public boolean add(Task task);
	public boolean remove(int taskId);
	public boolean update(Task task);
	public Task existTask(String tema);
	public List<Task> filter(Task task,int managerId);
	public List<Task> filterForEmployee(Task task, int employeeId);
	
	public Task findById(int id);
	public List<Task> getAllTasks(int idMenager);
	public List<Task> getAllTasksForEmployee(int idEmployee);
}

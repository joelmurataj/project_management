package com.project.dao;

import java.util.ArrayList;

import com.project.entity.Task;

public interface TaskDao {

	public boolean add(Task task);
	public boolean remove(int taskId);
	public boolean update(Task task);
	public Task existTask(String tema);
	public ArrayList<Task> filter(Task task,int managerId);
	public ArrayList<Task> filterForEmployee(Task task, int employeeId);
	
	public Task findById(int id);
	public ArrayList<Task> getAllTasks(int idMenager);
	public ArrayList<Task> getAllTasksForEmployee(int idEmployee);
}

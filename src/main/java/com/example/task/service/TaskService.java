package com.example.task.service;

import com.example.task.model.Task;
import com.example.task.repository.TaskRepository;

import java.util.List;

public class TaskService {
    private final TaskRepository repository = new TaskRepository();

    public void addTask(Task task) {
        repository.save(task);
    }

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public boolean deleteTask(int id) {
        return repository.deleteById(id);
    }

    public boolean markTaskCompleted(int id) {
        return repository.markCompleted(id);
    }
}

package com.example.task.service;

import com.example.task.model.Task;
import com.example.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository repo;

    @Autowired
    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    public void addTask(Task task) {
        repo.save(task);
    }

    public List<Task> getAllTasks() {
        return repo.findAll();
    }

    public boolean deleteTask(int id) {
        return repo.deleteById(id);
    }

    public boolean markTaskCompleted(int id) {
        return repo.markCompleted(id);
    }
}

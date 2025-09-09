package com.example.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void addTask(String title, String description) {
        if (title == null || title.trim().isEmpty()) {
            System.out.println("Title cannot be empty.");
            return;
        }
        Task newTask = new Task(title, description);
        taskRepository.addTask(newTask);
        System.out.println("Task added successfully.");
    }

    public void viewAllTasks() {
        System.out.println("\n--- All Tasks ---");
        taskRepository.getAllTasks().forEach(System.out::println);
        System.out.println("-----------------");
    }

    public void updateTask(int id, String newTitle, String newDescription, boolean newStatus) {
        Task taskToUpdate = new Task(id, newTitle, newDescription, newStatus);
        if (taskRepository.updateTask(taskToUpdate)) {
            System.out.println("Task updated successfully.");
        } else {
            System.out.println("Failed to update task. Task with ID " + id + " not found.");
        }
    }

    public void deleteTask(int id) {
        if (taskRepository.deleteTask(id)) {
            System.out.println("Task deleted successfully.");
        } else {
            System.out.println("Failed to delete task. Task with ID " + id + " may not exist.");
        }
    }
}
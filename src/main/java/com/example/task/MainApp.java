package com.example.task;

import com.example.task.model.Task;
import com.example.task.service.TaskService;
import com.example.task.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class MainApp {

    private final TaskService taskService;
    private final AuthService authService;

    @Autowired
    public MainApp(TaskService taskService, AuthService authService) {
        this.taskService = taskService;
        this.authService = authService;
    }

    public void run() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Task Manager ===");
            System.out.println("1. Signup");
            System.out.println("2. Login");
            System.out.println("3. Add Task");
            System.out.println("4. View All Tasks");
            System.out.println("5. Delete Task");
            System.out.println("6. Mark Task Completed");
            System.out.println("7. Logout");
            System.out.println("8. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    handleSignup(sc);
                    break;
                case 2:
                    handleLogin(sc);
                    break;
                case 3:
                    handleAddTask(sc);
                    break;
                case 4:
                    handleViewTasks();
                    break;
                case 5:
                    handleDeleteTask(sc);
                    break;
                case 6:
                    handleMarkCompleted(sc);
                    break;
                case 7:
                    handleLogout();
                    break;
                case 8:
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private void handleSignup(Scanner sc) {
        System.out.print("Enter username: ");
        String suName = sc.nextLine();
        System.out.print("Enter password: ");
        String suPass = sc.nextLine();
        if (authService.signup(suName, suPass)) {
            System.out.println("Signup successful! You can now login.");
        } else {
            System.out.println("Signup failed! Username already exists.");
        }
    }

    private void handleLogin(Scanner sc) {
        if (authService.isLoggedIn()) {
            System.out.println("Already logged in as " + authService.getLoggedInUser().getUsername());
            return;
        }
        System.out.print("Enter username: ");
        String liName = sc.nextLine();
        System.out.print("Enter password: ");
        String liPass = sc.nextLine();
        if (authService.login(liName, liPass)) {
            System.out.println("Login successful! Welcome " + liName);
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private void handleAddTask(Scanner sc) {
        if (!authService.isLoggedIn()) {
            System.out.println("You must login first!");
            return;
        }
        System.out.print("Enter task title: ");
        String title = sc.nextLine();
        System.out.print("Enter description: ");
        String desc = sc.nextLine();
        Task task = new Task(title, desc.isEmpty() ? null : desc, false);
        taskService.addTask(task);
        System.out.println("Task added successfully!");
    }

    private void handleViewTasks() {
        if (!authService.isLoggedIn()) {
            System.out.println("You must login first!");
            return;
        }
        List<Task> tasks = taskService.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks found!");
        } else {
            tasks.forEach(System.out::println);
        }
    }

    private void handleDeleteTask(Scanner sc) {
        if (!authService.isLoggedIn()) {
            System.out.println("You must login first!");
            return;
        }
        System.out.print("Enter task ID to delete: ");
        int id = sc.nextInt();
        if (taskService.deleteTask(id)) {
            System.out.println("Task deleted successfully!");
        } else {
            System.out.println("Task not found.");
        }
    }

    private void handleMarkCompleted(Scanner sc) {
        if (!authService.isLoggedIn()) {
            System.out.println("You must login first!");
            return;
        }
        System.out.print("Enter task ID to mark as completed: ");
        int cid = sc.nextInt();
        if (taskService.markTaskCompleted(cid)) {
            System.out.println("Task marked as completed!");
        } else {
            System.out.println("Task not found.");
        }
    }

    private void handleLogout() {
        if (authService.isLoggedIn()) {
            System.out.println("Goodbye " + authService.getLoggedInUser().getUsername());
            authService.logout();
        } else {
            System.out.println("You are not logged in.");
        }
    }
}

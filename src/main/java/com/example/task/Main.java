package com.example.task;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Create the Spring IoC container from the configuration class
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

        // Get the TaskService bean from the container
        TaskService taskService = context.getBean(TaskService.class);
        Scanner scanner = new Scanner(System.in);
        int choice;

        System.out.println("Task Management Console Application");

        do {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Add a new task");
            System.out.println("2. View all tasks");
            System.out.println("3. Update a task");
            System.out.println("4. Delete a task");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter task title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter task description: ");
                        String description = scanner.nextLine();
                        taskService.addTask(title, description);
                        break;
                    case 2:
                        taskService.viewAllTasks();
                        break;
                    case 3:
                        System.out.print("Enter task ID to update: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter new title: ");
                        String newTitle = scanner.nextLine();
                        System.out.print("Enter new description: ");
                        String newDescription = scanner.nextLine();
                        System.out.print("Is the task completed? (true/false): ");
                        boolean newStatus = scanner.nextBoolean();
                        taskService.updateTask(updateId, newTitle, newDescription, newStatus);
                        break;
                    case 4:
                        System.out.print("Enter task ID to delete: ");
                        int deleteId = scanner.nextInt();
                        taskService.deleteTask(deleteId);
                        break;
                    case 0:
                        System.out.println("Exiting application.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input from the scanner
                choice = -1; // Reset choice to loop again
            }

        } while (choice != 0);

        scanner.close();
        ((AnnotationConfigApplicationContext) context).close();
    }
}
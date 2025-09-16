// package com.example.task;

// import com.example.task.model.Task;
// import com.example.task.service.TaskService;
// import com.example.task.service.AuthService;
// import com.example.task.config.SpringConfig;
// import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// import java.util.List;
// import java.util.Scanner;

// public class Main {
//     public static void main(String[] args) {
//         // 1. Create Spring context
//         AnnotationConfigApplicationContext context =
//                 new AnnotationConfigApplicationContext(SpringConfig.class);

//         // 2. Get beans from Spring
//         TaskService taskService = context.getBean(TaskService.class);
//         AuthService authService = context.getBean(AuthService.class); // <-- also managed by Spring

//         Scanner sc = new Scanner(System.in);

//         while (true) {
//             System.out.println("\n=== Task Manager ===");
//             System.out.println("1. Signup");
//             System.out.println("2. Login");
//             System.out.println("3. Add Task");
//             System.out.println("4. View All Tasks");
//             System.out.println("5. Delete Task");
//             System.out.println("6. Mark Task Completed");
//             System.out.println("7. Logout");
//             System.out.println("8. Exit");
//             System.out.print("Enter choice: ");

//             int choice = sc.nextInt();
//             sc.nextLine(); // consume newline

//             switch (choice) {
//                 case 1: // Signup
//                     System.out.print("Enter username: ");
//                     String suName = sc.nextLine();
//                     System.out.print("Enter password: ");
//                     String suPass = sc.nextLine();
//                     if (authService.signup(suName, suPass)) {
//                         System.out.println("Signup successful! You can now login.");
//                     } else {
//                         System.out.println("Signup failed! Username already exists.");
//                     }
//                     break;

//                 case 2: // Login
//                     if (authService.isLoggedIn()) {
//                         System.out.println("Already logged in as " + authService.getLoggedInUser().getUsername());
//                         break;
//                     }
//                     System.out.print("Enter username: ");
//                     String liName = sc.nextLine();
//                     System.out.print("Enter password: ");
//                     String liPass = sc.nextLine();
//                     if (authService.login(liName, liPass)) {
//                         System.out.println("Login successful! Welcome " + liName);
//                     } else {
//                         System.out.println("Invalid username or password.");
//                     }
//                     break;

//                 case 3: // Add Task
//                     if (!authService.isLoggedIn()) {
//                         System.out.println("You must login first!");
//                         break;
//                     }
//                     System.out.print("Enter task title: ");
//                     String title = sc.nextLine();
//                     System.out.print("Enter description: ");
//                     String desc = sc.nextLine();
//                     Task task = new Task(title, desc.isEmpty() ? null : desc, false);
//                     taskService.addTask(task);
//                     System.out.println("Task added successfully!");
//                     break;

//                 case 4: // View Tasks
//                     if (!authService.isLoggedIn()) {
//                         System.out.println("You must login first!");
//                         break;
//                     }
//                     List<Task> tasks = taskService.getAllTasks();
//                     if (tasks.isEmpty()) {
//                         System.out.println("No tasks found!");
//                     } else {
//                         tasks.forEach(System.out::println);
//                     }
//                     break;

//                 case 5: // Delete
//                     if (!authService.isLoggedIn()) {
//                         System.out.println("You must login first!");
//                         break;
//                     }
//                     System.out.print("Enter task ID to delete: ");
//                     int id = sc.nextInt();
//                     if (taskService.deleteTask(id)) {
//                         System.out.println("Task deleted successfully!");
//                     } else {
//                         System.out.println("Task not found.");
//                     }
//                     break;

//                 case 6: // Complete
//                     if (!authService.isLoggedIn()) {
//                         System.out.println("You must login first!");
//                         break;
//                     }
//                     System.out.print("Enter task ID to mark as completed: ");
//                     int cid = sc.nextInt();
//                     if (taskService.markTaskCompleted(cid)) {
//                         System.out.println("Task marked as completed!");
//                     } else {
//                         System.out.println("Task not found.");
//                     }
//                     break;

//                 case 7: // Logout
//                     if (authService.isLoggedIn()) {
//                         System.out.println("Goodbye " + authService.getLoggedInUser().getUsername());
//                         authService.logout();
//                     } else {
//                         System.out.println("You are not logged in.");
//                     }
//                     break;

//                 case 8: // Exit
//                     System.out.println("Exiting...");
//                     context.close(); // ✅ close Spring context
//                     sc.close();
//                     return;

//                 default:
//                     System.out.println("Invalid choice!");
//             }
//         }
//     }
// }
package com.example.task;

import com.example.task.config.SpringConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(SpringConfig.class)) {

            // Get MainApp bean and run it
            MainApp app = context.getBean(MainApp.class);
            app.run();
        }
    }
}

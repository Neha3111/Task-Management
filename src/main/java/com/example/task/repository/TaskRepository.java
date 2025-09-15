package com.example.task.repository;

import com.example.task.model.Task;
import com.example.task.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    public void save(Task task) {
        if (task == null || task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            System.out.println("Skipping insert: Task or title is empty.");
            return;
        }

        String sql = "INSERT INTO tasks (title, description, is_completed) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, task.getTitle());

            if (task.getDescription() == null) {
                stmt.setNull(2, Types.VARCHAR);
            } else {
                stmt.setString(2, task.getDescription());
            }

            stmt.setBoolean(3, task.isCompleted());
            int affected = stmt.executeUpdate();

            if (affected == 0) {
                throw new SQLException("Insert failed, no rows affected.");
            }

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    task.setId(keys.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.err.println("DB insert error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT id, title, description, is_completed FROM tasks ORDER BY id";

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                tasks.add(new Task(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getBoolean("is_completed")
                ));
            }

        } catch (SQLException e) {
            System.err.println("DB query error: " + e.getMessage());
            e.printStackTrace();
        }
        return tasks;
    }

    public boolean deleteById(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("DB delete error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean markCompleted(int id) {
        String sql = "UPDATE tasks SET is_completed = TRUE WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("DB update error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}

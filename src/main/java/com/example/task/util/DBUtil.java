package com.example.task.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;
    private static final String DRIVER;

    static {
        Properties props = new Properties();
        try (InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (in == null) {
                throw new IllegalStateException("application.properties not found in classpath. Put it in src/main/resources/");
            }
            props.load(in);
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Failed to load application.properties: " + e.getMessage());
        }

        URL = props.getProperty("db.url");
        USERNAME = props.getProperty("db.username");
        PASSWORD = props.getProperty("db.password");
        DRIVER = props.getProperty("db.driver", "com.mysql.cj.jdbc.Driver");

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Warning: JDBC driver class not found: " + DRIVER);
        }

        if (URL == null || USERNAME == null) {
            throw new ExceptionInInitializerError("db.url and db.username must be set in application.properties");
        }
    }

    /**
     * Returns a new Connection for each call. Caller should close it.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}

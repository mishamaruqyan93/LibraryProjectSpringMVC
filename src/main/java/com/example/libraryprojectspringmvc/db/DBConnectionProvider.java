package com.example.libraryprojectspringmvc.db;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class DBConnectionProvider {

    private volatile static DBConnectionProvider instance;
    public static Connection connection;

    private final String dbDriverName = "org.h2.Driver";
    private final String dbUrl = "jdbc:h2:mem:library";
    private final String username = "sa";
    private final String password = "sa";

    private DBConnectionProvider() {
        try {
            Class.forName(dbDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static DBConnectionProvider getInstance() {
        if (instance == null) {
            synchronized (DBConnectionProvider.class) {
                if (instance == null) {
                    instance = new DBConnectionProvider();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(dbUrl, username, password);
                createUsersTable();
                createBooksTable();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private void createUsersTable() {
        try {
            Statement statement = connection.createStatement();
            String createUsersTableQuery = "CREATE TABLE IF NOT EXISTS users (\n" +
                    "    id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                    "    name VARCHAR(50),\n" +
                    "    last_name VARCHAR(50),\n" +
                    "    email VARCHAR(50),\n" +
                    "    password VARCHAR(50),\n" +
                    "    user_role ENUM('ADMIN','USER')\n" +
                    ");";
            statement.execute(createUsersTableQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createBooksTable() {
        try {
            Statement statement = connection.createStatement();
            String createBooksTableQuery = "CREATE TABLE IF NOT EXISTS books (\n" +
                    "    id BIGINT auto_increment PRIMARY KEY,\n" +
                    "    book_name VARCHAR(50),\n" +
                    "    author_name VARCHAR(50),\n" +
                    "    user_id BIGINT,\n" +
                    "    FOREIGN KEY (user_id) REFERENCES users(id)\n" +
                    "    ON DELETE CASCADE " +
                    ");";
            statement.execute(createBooksTableQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

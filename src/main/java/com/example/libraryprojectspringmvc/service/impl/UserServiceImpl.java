package com.example.libraryprojectspringmvc.service.impl;

import com.example.libraryprojectspringmvc.db.DBConnectionProvider;
import com.example.libraryprojectspringmvc.model.User;
import com.example.libraryprojectspringmvc.model.UserRole;
import com.example.libraryprojectspringmvc.service.UserService;
import com.example.libraryprojectspringmvc.validator.LibraryProjectValidator;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final LibraryProjectValidator libraryProjectValidator;
    private Connection connection;

    public UserServiceImpl(LibraryProjectValidator libraryProjectValidator) {
        this.libraryProjectValidator = libraryProjectValidator;
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                users.add(getUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User findById(Long id) {
        libraryProjectValidator.validateId(id);
        connection = DBConnectionProvider.getInstance().getConnection();
        String query = "SELECT * FROM users WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(id);
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setUserRole(UserRole.valueOf(resultSet.getString("user_role")));
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public void save(User user) {
        libraryProjectValidator.validateObject(user);
        connection = DBConnectionProvider.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO users(name, last_name, email, password,user_role) VALUES(?,?,?,?,?)");

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getUserRole().name());
            int execute = preparedStatement.executeUpdate();
            System.out.println(execute);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        libraryProjectValidator.validateObject(user);
        connection = DBConnectionProvider.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET name=?, last_name=?, email=? where id=?;");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setLong(4, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getByEmailAndPassword(String email, String password) {
        libraryProjectValidator.validateField(email);
        libraryProjectValidator.validateField(password);
        connection = DBConnectionProvider.getInstance().getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users where email=? and password=?");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setUserRole(UserRole.valueOf(resultSet.getString("user_role")));
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(String.format("Given user with email: %s and password %s not found", email, password));
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        libraryProjectValidator.validateId(id);
        connection = DBConnectionProvider.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users where id=?;");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(Long.valueOf(resultSet.getString(1)));
        user.setName(resultSet.getString("name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setEmail(resultSet.getString("email"));
        user.setUserRole(UserRole.valueOf(resultSet.getString("user_role")));
        return user;
    }
}

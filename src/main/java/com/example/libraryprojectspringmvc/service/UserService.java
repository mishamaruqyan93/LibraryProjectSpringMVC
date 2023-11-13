package com.example.libraryprojectspringmvc.service;

import com.example.libraryprojectspringmvc.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User findById(Long id);

    User findByEmail(String email);

    void save(User user);

    void update(User user);

    User getByEmailAndPassword(String email, String password);

    void deleteById(Long id);
}

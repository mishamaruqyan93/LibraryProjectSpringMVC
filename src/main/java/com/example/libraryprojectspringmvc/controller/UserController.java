package com.example.libraryprojectspringmvc.controller;

import com.example.libraryprojectspringmvc.model.User;
import com.example.libraryprojectspringmvc.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAllUsers(ModelMap modelMap) {
        List<User> allUsers = userService.getAllUsers();
        modelMap.addAttribute("users", allUsers);
        return "usersPage";
    }

    @GetMapping("/userId")
    public String findUserById(@RequestParam("userId") Long userId, Model model) {
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        return "usersPage";
    }

    @GetMapping("/update")
    public String updateUserPage(@RequestParam("id") Long id, Model model) {
        User userById = userService.findById(id);
        model.addAttribute("userToEdit", userById);
        return "editUser";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute User user) {
        userService.update(user);
        return "redirect:/users";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        userService.deleteById(id);
        return "redirect:/users";
    }
}

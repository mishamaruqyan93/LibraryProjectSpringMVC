package com.example.libraryprojectspringmvc.controller;

import com.example.libraryprojectspringmvc.model.Book;
import com.example.libraryprojectspringmvc.model.User;
import com.example.libraryprojectspringmvc.service.BookService;
import com.example.libraryprojectspringmvc.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final BookService bookService;


    public AdminController(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
    }

    @GetMapping("/dashboard")
    public String displayAdminDashboard() {
        return "adminDashboard";
    }

    @GetMapping
    public String displayAdminPage(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);

        List<Book> unassignedBooks = bookService.getAllUnassignedBook();
        model.addAttribute("unassignedBooks", unassignedBooks);
        return "admin";
    }

    @PostMapping
    public String handleAdminForm(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);

        List<Book> unassignedBooks = bookService.getAllUnassignedBook();
        model.addAttribute("unassignedBooks", unassignedBooks);
        return "admin";
    }
}
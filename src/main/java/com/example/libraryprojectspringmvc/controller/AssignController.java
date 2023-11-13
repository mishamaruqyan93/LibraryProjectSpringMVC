package com.example.libraryprojectspringmvc.controller;

import com.example.libraryprojectspringmvc.model.Book;
import com.example.libraryprojectspringmvc.model.User;
import com.example.libraryprojectspringmvc.service.BookService;
import com.example.libraryprojectspringmvc.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/assign")
public class AssignController {

    private final UserService userService;
    private final BookService bookService;

    public AssignController(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
    }

    @GetMapping
    public String getAssignPage() {
        return "redirect:/admin";
    }

    @PostMapping
    public String assignBookToUser(String selectedUser, String selectedBook, Model model) {
        Long userId = Long.valueOf(selectedUser);
        Long bookId = Long.valueOf(selectedBook);

        User user = userService.findById(userId);
        if (user != null) {
            Book book = bookService.findById(bookId);
            if (book != null && book.getUserId() == 0) {
                book.setUserId(userId);
                bookService.update(book);
                model.addAttribute("successAssign", "Successfully assigned!");
            }
        }
        return "assignBook";
    }

    @GetMapping("show-assign-books")
    public String getAllAssignedBooks(ModelMap modelMap) {
        List<Book> allAssignedBooks = bookService.getAllAssignedBooks();
        modelMap.addAttribute("assignBooks", allAssignedBooks);
        return "assignedBooks";
    }
}

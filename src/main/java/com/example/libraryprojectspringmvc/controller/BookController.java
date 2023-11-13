package com.example.libraryprojectspringmvc.controller;

import com.example.libraryprojectspringmvc.model.Book;
import com.example.libraryprojectspringmvc.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String getAllBooks(ModelMap modelMap) {
        List<Book> allBooks = bookService.getAllBooks();
        modelMap.addAttribute("books", allBooks);
        return "booksPage";
    }

    @GetMapping("/bookId")
    public String getBookById(@RequestParam("bookId") Long bookId, Model model) {
        Book bookById = bookService.findById(bookId);
        model.addAttribute("book", bookById);
        return "booksPage";
    }

    @GetMapping("/add")
    public String save() {
        return "addBook";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute Book book) {
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        bookService.deleteById(id);
        return "redirect:/books";
    }
}

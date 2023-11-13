package com.example.libraryprojectspringmvc.service;


import com.example.libraryprojectspringmvc.model.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    Book findById(Long id);

    void save(Book book);

    void update(Book book);

    void deleteById(Long id);

    List<Book> getAllUnassignedBook();

    List<Book> getAllAssignedBooks();
}

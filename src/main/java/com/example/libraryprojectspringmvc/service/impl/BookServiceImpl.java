package com.example.libraryprojectspringmvc.service.impl;

import com.example.libraryprojectspringmvc.db.DBConnectionProvider;
import com.example.libraryprojectspringmvc.model.Book;
import com.example.libraryprojectspringmvc.service.BookService;
import com.example.libraryprojectspringmvc.validator.LibraryProjectValidator;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final LibraryProjectValidator libraryProjectValidator;
    private Connection connection;

    public BookServiceImpl(LibraryProjectValidator libraryProjectValidator) {
        this.libraryProjectValidator = libraryProjectValidator;
    }

    @Override
    public List<Book> getAllBooks() {
        String query = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try {
            connection = DBConnectionProvider.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                books.add(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    @Override
    public Book findById(Long id) {
        libraryProjectValidator.validateId(id);
        connection = DBConnectionProvider.getInstance().getConnection();
        String query = "SELECT * FROM books WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Book book = new Book();
                book.setBookName(resultSet.getString("book_name"));
                book.setAuthorName(resultSet.getString("author_name"));
                book.setUserId(resultSet.getLong("user_id"));
                book.setId(id);
                return book;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void save(Book book) {
        libraryProjectValidator.validateObject(book);
        connection = DBConnectionProvider.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO books(book_name, author_name, user_id) VALUES(?,?,?)");
            preparedStatement.setString(1, book.getBookName());
            preparedStatement.setString(2, book.getAuthorName());
            preparedStatement.setObject(3, null);
            int execute = preparedStatement.executeUpdate();
            System.out.println(execute);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Book book) {
        libraryProjectValidator.validateObject(book);
        connection = DBConnectionProvider.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE books SET book_name=?, author_name=?, user_id=? WHERE id=?");
            preparedStatement.setString(1, book.getBookName());
            preparedStatement.setString(2, book.getAuthorName());
            preparedStatement.setLong(3, book.getUserId());
            preparedStatement.setLong(4, book.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        connection = DBConnectionProvider.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM books where id=?;");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> getAllUnassignedBook() {
        connection = DBConnectionProvider.getInstance().getConnection();
        List<Book> books = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM books where user_id is null");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setBookName(resultSet.getString("book_name"));
                book.setAuthorName(resultSet.getString("author_name"));
                book.setUserId(resultSet.getLong("user_id"));

                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> getAllAssignedBooks() {
        connection = DBConnectionProvider.getInstance().getConnection();
        List<Book> books = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM books WHERE user_id IS NOT NULL");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setBookName(resultSet.getString("book_name"));
                book.setAuthorName(resultSet.getString("author_name"));
                book.setUserId(resultSet.getLong("user_id"));

                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(Long.valueOf(resultSet.getString(1)));
        book.setBookName(resultSet.getString("book_name"));
        book.setAuthorName(resultSet.getString("author_name"));
        book.setUserId(resultSet.getLong("user_id"));
        return book;
    }
}

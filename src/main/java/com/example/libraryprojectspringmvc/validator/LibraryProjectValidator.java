package com.example.libraryprojectspringmvc.validator;

import com.example.libraryprojectspringmvc.exception.ExceptionFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class LibraryProjectValidator {

    private final ExceptionFactory exceptionFactory;

    public LibraryProjectValidator(ExceptionFactory exceptionFactory) {
        this.exceptionFactory = exceptionFactory;
    }

    public void validateField(String credential) {
        if (credential == null || credential.isEmpty() || credential.equals(" ")) {
            throw exceptionFactory.invalidCredential(credential);
        }
    }

    public <T> void validateObject(T object) {
        if (Objects.isNull(object)) {
            throw exceptionFactory.invalidObject();
        }
    }

    public void validateId(Long id) {
        if (id <= 0) {
            throw exceptionFactory.invalidId(id);
        }
    }
}

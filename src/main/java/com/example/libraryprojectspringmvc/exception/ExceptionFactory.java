package com.example.libraryprojectspringmvc.exception;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ExceptionFactory {

    public ResourceInValidException invalidCredential(String credential) {
        System.err.println("Credential can't be is empty or null" + new Date());
        return new ResourceInValidException("Credential can't be is empty or null" + credential);
    }

    public EntityNotFoundException invalidObject() {
        System.err.println("Object was passed null" + new Date());
        return new EntityNotFoundException("Object was passed null");
    }

    public InternalError invalidId(Long id) {
        System.err.println("Thee id can not be 0 or less than 0`" + id + new Date());
        return new InternalError("Thee id can not be 0 or less than 0`" + id);
    }
}

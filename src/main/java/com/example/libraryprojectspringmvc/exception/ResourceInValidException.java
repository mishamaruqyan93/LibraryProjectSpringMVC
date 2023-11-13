package com.example.libraryprojectspringmvc.exception;

public class ResourceInValidException extends RuntimeException{

    public ResourceInValidException(String msg) {
        super(msg);
    }
}

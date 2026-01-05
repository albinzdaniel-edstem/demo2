package com.example.demo.exception;

public class BookAlreadyExistsException extends RuntimeException {
    public BookAlreadyExistsException(String isbnCode) {
        super("Book already present with ISBN code:" + isbnCode);
    }
}

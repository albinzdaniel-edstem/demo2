package com.example.demo.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String isbnCode) {
        super("Book not found with ISBN code:" + isbnCode);
    }
}

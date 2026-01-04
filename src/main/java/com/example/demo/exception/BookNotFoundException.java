package com.example.demo.exception;

public class BookNotFoundException extends Throwable {
    public BookNotFoundException(String isbnCode) {
        super("Book not found with ISBN code:" + isbnCode);
    }
}

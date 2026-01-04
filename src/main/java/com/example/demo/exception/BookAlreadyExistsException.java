package com.example.demo.exception;

public class BookAlreadyExistsException extends Throwable {
    public BookAlreadyExistsException(String isbnCode) {
        super("Book already present with ISBN code:" + isbnCode);
    }
}

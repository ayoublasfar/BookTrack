package com.mycompany.booktrack;

public class BookUnavailableException extends Exception {
    public BookUnavailableException(String message) {
        super(message);
    }
}

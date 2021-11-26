package com.adobe.bookstore.exceptions;

import com.adobe.bookstore.dto.BookOrderDTO;

/**
 * Thrown when there isn't enough stock for a book
 */
public class NoStockException extends RuntimeException {
    public static final String formattedMessage = "There is no stock for your request: %s";

    /**
     * Constructor
     *
     * @param bookOrder Books request
     */
    public NoStockException(BookOrderDTO bookOrder) {
        super(String.format(formattedMessage, bookOrder));
    }
}

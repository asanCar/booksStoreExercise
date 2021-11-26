package com.adobe.bookstore.exceptions;

/**
 * Thrown when a book does not exist in stock
 */
public class InvalidBookNameException extends RuntimeException {
    public static final String formattedMessage = "There is no book '%s' available";

    /**
     * Constructor
     *
     * @param bookName Book name
     */
    public InvalidBookNameException(String bookName) {
        super(String.format(formattedMessage, bookName));
    }
}

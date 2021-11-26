package com.adobe.bookstore.exceptions;

/**
 * Thrown when a book is duplicated in an order
 */
public class DuplicatedBookException extends RuntimeException {
    public static final String formattedMessage = "The book %s is duplicated. If this is not an error, please consider to create two different orders for this book.";

    /**
     * Constructor
     *
     * @param bookName Book name
     */
    public DuplicatedBookException(String bookName) {
        super(String.format(formattedMessage, bookName));
    }
}

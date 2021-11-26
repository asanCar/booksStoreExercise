package com.adobe.bookstore.exceptions;

/**
 * Thrown when the requested format is not supported
 */
public class UnsupportedFormatException extends RuntimeException {
    public static final String formattedMessage = "Format '%s' is not supported";

    /**
     * Constructor
     *
     * @param format Book name
     */
    public UnsupportedFormatException(String format) {
        super(String.format(formattedMessage, format));
    }
}

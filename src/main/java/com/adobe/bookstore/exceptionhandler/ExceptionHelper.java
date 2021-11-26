package com.adobe.bookstore.exceptionhandler;

import com.adobe.bookstore.exceptions.InvalidBookNameException;
import com.adobe.bookstore.exceptions.NoStockException;
import com.adobe.bookstore.exceptions.UnsupportedFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Exceptions Handler class
 */
@ControllerAdvice
public class ExceptionHelper extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidBookNameException.class)
    public ResponseEntity<?> handleInvalidBookNameException(InvalidBookNameException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoStockException.class)
    public ResponseEntity<?> handleNoStockException(NoStockException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnsupportedFormatException.class)
    public ResponseEntity<?> handleUnsupportedFormatException(UnsupportedFormatException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

package com.adobe.bookstore.service;

import com.adobe.bookstore.domain.BookStock;
import com.adobe.bookstore.dto.BookOrderDTO;
import com.adobe.bookstore.exceptions.InvalidBookNameException;
import com.adobe.bookstore.exceptions.NoStockException;

import java.util.List;

/**
 * Service used to manage books stock
 */
public interface StockService {
    /**
     * Checks if an order can be processed with the current books stock.
     *
     * @param bookOrderList List of ordered books.
     * @throws InvalidBookNameException Thrown when a book does not exist in stock.
     * @throws NoStockException         Thrown when there isn't enough stock for a book.
     */
    void checkStock(List<BookOrderDTO> bookOrderList) throws InvalidBookNameException, NoStockException;

    /**
     * Removes the ordered books from the books stock.
     *
     * @param bookOrderList A list of ordered books.
     */
    void updateStock(List<BookOrderDTO> bookOrderList);

    /**
     * Updates de books stock.
     *
     * @param bookStock List of books stock.
     */
    void save(List<BookStock> bookStock);
}

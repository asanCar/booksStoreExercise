package com.adobe.bookstore.service;

import com.adobe.bookstore.dto.BookOrderDTO;
import com.adobe.bookstore.dto.CreateOrderResponse;
import com.adobe.bookstore.exceptions.DuplicatedBookException;
import com.adobe.bookstore.exceptions.InvalidBookNameException;
import com.adobe.bookstore.exceptions.NoStockException;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Service responsible to create new orders and retrieve a list of the already created ones
 */
public interface OrderService {

    /**
     * Checks the current books stock, creates a new Order and update the books stock.
     *
     * @param bookOrderList List of ordered books.
     * @return The ID of the created Order.
     * @throws InvalidBookNameException Thrown when a book does not exist in stock.
     * @throws NoStockException         Thrown when there isn't enough stock for a book.
     */
    ResponseEntity<CreateOrderResponse> processOrder(
            List<BookOrderDTO> bookOrderList) throws InvalidBookNameException, NoStockException, DuplicatedBookException;

    /**
     * Retrieves a list of the created orders in JSON or CSV format
     *
     * @param format Indicates the response format. JSON or CSV.
     * @return A list of Orders in JSON or CSV format.
     */
    ResponseEntity<byte[]> getOrdersList(String format);
}

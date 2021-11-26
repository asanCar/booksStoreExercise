package com.adobe.bookstore.service;

import com.adobe.bookstore.domain.BookStock;
import com.adobe.bookstore.dto.BookOrderDTO;
import com.adobe.bookstore.exceptions.InvalidBookNameException;
import com.adobe.bookstore.exceptions.NoStockException;
import com.adobe.bookstore.repository.BookStockRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class StockServiceImpl implements StockService {

    @Autowired
    private BookStockRepository bookStockRepository;

    @Override
    public void checkStock(List<BookOrderDTO> bookOrderList) {
        for (BookOrderDTO bookOrder : bookOrderList) {
            final BookStock bookStock = getBookStock(bookOrder);
        }
    }

    @Async
    @Override
    public void updateStock(List<BookOrderDTO> bookOrderList) {
        for (BookOrderDTO bookOrder : bookOrderList) {
            final BookStock bookStock = getBookStock(bookOrder);
            bookStock.setQuantity(bookStock.getQuantity() - bookOrder.getQuantity());
            bookStockRepository.save(bookStock);
            log.info(String.format("Book stock with ID '%s' has been updated", bookStock.getId()));
        }
    }

    /**
     * Retrieve a BookStock with the specified book name (if it exists),
     * and validates if there is enough stock to process the Order.
     *
     * @param bookOrder BookOrder object
     * @return A BookStock object
     */
    private BookStock getBookStock(BookOrderDTO bookOrder) {
        final String bookName = bookOrder.getName();
        final BookStock bookStock = bookStockRepository.findByName(bookName)
                .orElseThrow(() -> new InvalidBookNameException(bookName));
        log.debug(String.format("Book: %s, Order quantity: %s, Stock quantity: %s",
                bookOrder.getName(),
                bookOrder.getQuantity(),
                bookStock.getQuantity()));
        if (bookStock.getQuantity() < bookOrder.getQuantity()) {
            log.error(String.format("Order cannot be processed due to stock shortage:\n\tOrder: %s\n\tCurrent Stock: %s", bookOrder, bookStock));
            throw new NoStockException(bookOrder);
        }
        return bookStock;
    }

    @Override
    public void save(List<BookStock> bookStock) {
        bookStockRepository.saveAll(bookStock);
    }
}

package com.adobe.bookstore.service;

import com.adobe.bookstore.domain.BookStock;
import com.adobe.bookstore.dto.BookOrderDTO;
import com.adobe.bookstore.exceptions.InvalidBookNameException;
import com.adobe.bookstore.exceptions.NoStockException;
import com.adobe.bookstore.repository.BookStockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.adobe.bookstore.TestHarness.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockServiceImplTest {

    @Mock
    private BookStockRepository bookStockRepository;

    @InjectMocks
    private StockServiceImpl sut;

    @Test
    void checkStock_returnNothing_whenBooksInStock() {
        // given
        final var input = createExampleBookOrderDTOList();
        final BookStock bookStock = createExampleBookStock();
        for (BookOrderDTO bookOrder : input) {
            when(bookStockRepository.findByName(bookOrder.getName())).thenReturn(Optional.of(bookStock));
        }

        // when
        sut.checkStock(input);

        // then
        for (BookOrderDTO bookOrder : input) {
            verify(bookStockRepository).findByName(bookOrder.getName());
        }
    }

    @Test
    void checkStock_throwNoStockException_whenNotEnoughItemsInStock() {
        // given
        final var input = createExampleBookOrderDTOList();
        final BookStock bookStock = createBookStockWithoutStock();
        for (BookOrderDTO bookOrder : input) {
            when(bookStockRepository.findByName(bookOrder.getName())).thenReturn(Optional.of(bookStock));
        }

        // when
        final Exception exception = assertThrows(NoStockException.class, () -> sut.checkStock(input));

        // then
        for (BookOrderDTO bookOrder : input) {
            final String expectedMessage = String.format(NoStockException.formattedMessage, bookOrder);
            final String actualMessage = exception.getMessage();
            assertEquals(expectedMessage, actualMessage);
        }
    }

    @Test
    void checkStock_throwInvalidItemException_whenBookDoesNotExists() {
        // given
        final var input = createExampleBookOrderDTOList();

        // when
        final Exception exception = assertThrows(InvalidBookNameException.class, () -> sut.checkStock(input));

        // then
        for (BookOrderDTO bookOrder : input) {
            final String expectedMessage = String.format(InvalidBookNameException.formattedMessage, bookOrder.getName());
            final String actualMessage = exception.getMessage();
            assertEquals(expectedMessage, actualMessage);
        }
    }

    @Test
    void updateStock_returnNothing_whenStockIsUpdated() {
        // given
        final var input = createExampleBookOrderDTOList();
        final BookStock bookStock = createExampleBookStock();
        for (BookOrderDTO bookOrder : input) {
            when(bookStockRepository.findByName(anyString())).thenReturn(Optional.of(bookStock));
        }

        // when
        sut.updateStock(input);

        // then
        verify(bookStockRepository).findByName(anyString());
        verify(bookStockRepository).save(any(BookStock.class));
    }

    @Test
    void updateStock_throwNoStockException_whenNotEnoughItemsInStock() {
        // given
        final var input = createExampleBookOrderDTOList();
        final BookStock bookStock = createBookStockWithoutStock();
        for (BookOrderDTO bookOrder : input) {
            when(bookStockRepository.findByName(bookOrder.getName())).thenReturn(Optional.of(bookStock));
        }

        // when
        final Exception exception = assertThrows(NoStockException.class, () -> sut.updateStock(input));

        // then
        for (BookOrderDTO bookOrder : input) {
            final String expectedMessage = String.format(NoStockException.formattedMessage, bookOrder);
            final String actualMessage = exception.getMessage();
            assertEquals(expectedMessage, actualMessage);
        }
    }

    @Test
    void updateStock_throwInvalidItemException_whenBookDoesNotExists() {
        // given
        final var input = createExampleBookOrderDTOList();

        // when
        final Exception exception = assertThrows(InvalidBookNameException.class, () -> sut.updateStock(input));

        // then
        for (BookOrderDTO bookOrder : input) {
            final String expectedMessage = String.format(InvalidBookNameException.formattedMessage, bookOrder.getName());
            final String actualMessage = exception.getMessage();
            assertEquals(expectedMessage, actualMessage);
        }
    }

    @Test
    void save_returnNothing_whenBookStockListIsProvided() {
        // given
        final var input = createExampleBookStockList();

        // when
        sut.save(input);

        // then
        verify(bookStockRepository).saveAll(anyList());
    }
}
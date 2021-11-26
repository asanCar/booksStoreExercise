package com.adobe.bookstore.service;

import com.adobe.bookstore.domain.Order;
import com.adobe.bookstore.exceptions.DuplicatedBookException;
import com.adobe.bookstore.exceptions.UnsupportedFormatException;
import com.adobe.bookstore.repository.BookOrderRepository;
import com.adobe.bookstore.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Objects;

import static com.adobe.bookstore.TestHarness.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private StockService stockService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private BookOrderRepository bookOrderRepository;

    @InjectMocks
    private OrderServiceImpl sut;

    @Test
    void processOrder_createOrder_whenOrderIsValid() {
        // given
        final var input = createExampleBookOrderDTOList();
        final var exampleOrder = createExampleOrder();
        when(orderRepository.save(any(Order.class))).thenReturn(exampleOrder);

        // when
        final var outcome = sut.processOrder(input);

        // then
        final var outcome_id = Objects.requireNonNull(outcome.getBody()).getId();
        verify(stockService).checkStock(input);
        verify(stockService).updateStock(input);
        verify(bookOrderRepository).saveAll(any());
        verify(orderRepository).save(any());
        assertEquals(exampleOrder.getId(), outcome_id);
        assertEquals(HttpStatus.CREATED, outcome.getStatusCode());
    }

    @Test
    void processOrder_throwDuplicatedBookException_whenOrderHasDuplicatedBooks() {
        // given
        final var input = createDuplicatedBookOrderDTOList();

        // when
        final Exception exception = assertThrows(DuplicatedBookException.class, () -> sut.processOrder(input));

        // then
        final String expectedMessage = String.format(DuplicatedBookException.formattedMessage, ANY_NAME);
        final String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
        verify(stockService, never()).checkStock(input);
        verify(stockService, never()).updateStock(input);
        verify(bookOrderRepository, never()).saveAll(any());
        verify(orderRepository, never()).save(any());
    }

    @Test
    void getOrdersList_returnCsvFile_whenFormatIsCsv() {
        // given
        var input = "csv";
        final var exampleOrderList = createExampleOrderList();
        when(orderRepository.findAll()).thenReturn(exampleOrderList);

        // when
        final var outcome = sut.getOrdersList(input);

        // then
        final var outcomeHeaders = outcome.getHeaders();
        final var outcomeContentType = Objects.requireNonNull(outcomeHeaders.getContentType()).toString();
        final var outcomeContentDisposition = outcomeHeaders.getContentDisposition();
        final var outcomeBody = Objects.requireNonNull(outcome.getBody());
        verify(orderRepository).findAll();
        assertEquals(HttpStatus.OK, outcome.getStatusCode());
        assertEquals("text/csv", outcomeContentType);
        assertEquals("orders.csv", outcomeContentDisposition.getFilename());
        assertTrue(outcomeContentDisposition.isAttachment());
        assertTrue(outcomeBody.length > 0);
    }

    @Test
    void getOrdersList_returnJsonFile_whenFormatIsJson() {
        // given
        var input = "json";
        final var exampleOrderList = createExampleOrderList();
        when(orderRepository.findAll()).thenReturn(exampleOrderList);

        // when
        final var outcome = sut.getOrdersList(input);

        // then
        final var outcomeHeaders = outcome.getHeaders();
        final var outcomeContentType = Objects.requireNonNull(outcomeHeaders.getContentType()).toString();
        final var outcomeContentDisposition = outcomeHeaders.getContentDisposition();
        final var outcomeBody = Objects.requireNonNull(outcome.getBody());
        verify(orderRepository).findAll();
        assertEquals(HttpStatus.OK, outcome.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, outcomeContentType);
        assertEquals("orders.json", outcomeContentDisposition.getFilename());
        assertTrue(outcomeContentDisposition.isAttachment());
        assertTrue(outcomeBody.length > 0);
    }

    @Test
    void getOrdersList_returnFile_whenFormatHasCapitalLetters() {
        // given
        var input = "JsOn";
        final var exampleOrderList = createExampleOrderList();
        when(orderRepository.findAll()).thenReturn(exampleOrderList);

        // when
        final var outcome = sut.getOrdersList(input);

        // then
        final var outcomeHeaders = outcome.getHeaders();
        final var outcomeContentType = Objects.requireNonNull(outcomeHeaders.getContentType()).toString();
        final var outcomeContentDisposition = outcomeHeaders.getContentDisposition();
        final var outcomeBody = Objects.requireNonNull(outcome.getBody());
        verify(orderRepository).findAll();
        assertEquals(HttpStatus.OK, outcome.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, outcomeContentType);
        assertEquals("orders.json", outcomeContentDisposition.getFilename());
        assertTrue(outcomeContentDisposition.isAttachment());
        assertTrue(outcomeBody.length > 0);
    }

    @Test
    void getOrdersList_throwUnsupportedFormatException_whenFormatNotSupported() {
        // given
        var input = "xml";

        // when
        final Exception exception = assertThrows(UnsupportedFormatException.class, () -> sut.getOrdersList(input));

        // then
        final String expectedMessage = String.format(UnsupportedFormatException.formattedMessage, input);
        final String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
        verify(orderRepository, never()).findAll();
    }
}
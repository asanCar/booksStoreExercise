package com.adobe.bookstore.service;

import com.adobe.bookstore.domain.BookOrder;
import com.adobe.bookstore.domain.Order;
import com.adobe.bookstore.dto.BookOrderDTO;
import com.adobe.bookstore.dto.CreateOrderResponse;
import com.adobe.bookstore.dto.FlattenedOrder;
import com.adobe.bookstore.exceptions.DuplicatedBookException;
import com.adobe.bookstore.exceptions.InvalidBookNameException;
import com.adobe.bookstore.exceptions.NoStockException;
import com.adobe.bookstore.exceptions.UnsupportedFormatException;
import com.adobe.bookstore.repository.BookOrderRepository;
import com.adobe.bookstore.repository.OrderRepository;
import com.adobe.bookstore.utils.OrderUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    @Autowired
    private StockService stockService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BookOrderRepository bookOrderRepository;

    @Override
    public ResponseEntity<CreateOrderResponse> processOrder(
            List<BookOrderDTO> bookOrderList) throws InvalidBookNameException, NoStockException, DuplicatedBookException {

        validateOrder(bookOrderList);
        stockService.checkStock(bookOrderList);
        final var orderId = createOrder(bookOrderList);
        log.info("A new order has been created with ID:" + orderId);
        stockService.updateStock(bookOrderList);
        return new ResponseEntity<>(new CreateOrderResponse(orderId), HttpStatus.CREATED);
    }

    /**
     * Validates Order input.
     *
     * @param bookOrderList List of ordered books.
     */
    private void validateOrder(List<BookOrderDTO> bookOrderList) {
        var uniqueSet = new HashSet<BookOrderDTO>();
        for (BookOrderDTO bookOrder : bookOrderList) {
            if (!uniqueSet.add(bookOrder)) {
                final var bookName = bookOrder.getName();
                log.error(String.format("Invalid order: Duplicated book '%s'", bookName));
                throw new DuplicatedBookException(bookName);
            }
        }
    }

    /**
     * Creates a new Order.
     *
     * @param bookOrderDTOList List of ordered books.
     * @return The ID of the created Order.
     */
    private UUID createOrder(List<BookOrderDTO> bookOrderDTOList) {
        final var bookOrderList = bookOrderDTOList.stream()
                .map(BookOrder::new)
                .collect(Collectors.toList());
        final var bookOrders = bookOrderRepository.saveAll(bookOrderList);
        final var order = orderRepository.save(Order.builder().bookOrderList(bookOrders).build());
        return order.getId();
    }

    @Override
    public ResponseEntity<byte[]> getOrdersList(String format) {
        final List<Order> orders;
        String fileName;
        byte[] ordersBytes;
        String contentType;
        if (format.equalsIgnoreCase("csv")) {
            fileName = "orders.csv";
            contentType = "text/csv";
            orders = orderRepository.findAll();
            ordersBytes = getOrdersCsv(orders);
        } else if (format.equalsIgnoreCase("json")) {
            fileName = "orders.json";
            contentType = MediaType.APPLICATION_JSON_VALUE;
            orders = orderRepository.findAll();
            ordersBytes = getOrdersJson(orders);
        } else{
            throw new UnsupportedFormatException(format);
        }
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .contentLength(ordersBytes.length)
                .body(ordersBytes);
    }

    /**
     * Return a list of Order object in format of byte[].
     *
     * @param orders List of Order object.
     * @return Byte array with a list of orders.
     */
    private byte[] getOrdersJson(List<Order> orders) {
        try {
            return new ObjectMapper().writeValueAsBytes(orders);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new HttpMessageNotWritableException(e.getMessage(), e);
        }
    }

    /**
     * Flatten a list of Order object and return them in format of byte[].
     *
     * @param orders List of Order object.
     * @return Byte array with the flattened Order object.
     */
    private byte[] getOrdersCsv(List<Order> orders) {
        final var builder = CsvSchema.builder();
        Arrays.stream(FlattenedOrder.class.getDeclaredFields())
                .forEach(field -> builder.addColumn(field.getName()));
        final var csvSchema = builder.build().withHeader();
        final var csvMapper = new CsvMapper();
        final var flattenedOrders = OrderUtils.flatten(orders);
        try {
            return csvMapper.writer(csvSchema).writeValueAsBytes(flattenedOrders);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new HttpMessageNotWritableException(e.getMessage(), e);
        }
    }
}

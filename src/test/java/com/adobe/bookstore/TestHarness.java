package com.adobe.bookstore;

import com.adobe.bookstore.domain.BookOrder;
import com.adobe.bookstore.domain.BookStock;
import com.adobe.bookstore.domain.Order;
import com.adobe.bookstore.dto.BookOrderDTO;
import com.adobe.bookstore.dto.CreateOrderRequest;
import com.adobe.bookstore.dto.CreateOrderResponse;
import com.adobe.bookstore.dto.FlattenedOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestHarness {

    public static final UUID ANY_ID = UUID.randomUUID();

    public static final UUID ANY_OTHER_ID = UUID.randomUUID();

    public static final String ANY_NAME = "dolore aliqua sint ipsum laboris";

    public static final String ANY_OTHER_NAME = "excepteur eiusmod cupidatat in amet";

    public static final Integer ANY_QUANTITY = 1;

    public static final Integer ANY_OTHER_QUANTITY = 2;

    public static BookOrder createExampleBookOrder() {
        return BookOrder.builder().id(ANY_ID).bookName(ANY_NAME).quantity(ANY_QUANTITY).build();
    }

    public static BookOrderDTO createExampleBookOrderDTO() {
        return BookOrderDTO.builder().name(ANY_NAME).quantity(ANY_QUANTITY).build();
    }

    public static BookOrderDTO createExampleBookOrderDTOWithWrongName() {
        return BookOrderDTO.builder().name("Test Book").quantity(ANY_QUANTITY).build();
    }

    public static BookOrderDTO createExampleBookOrderDTOWithoutStock() {
        return BookOrderDTO.builder().name(ANY_OTHER_NAME).quantity(ANY_QUANTITY).build();
    }

    public static BookOrderDTO createExampleBookOrderDTOWithNegative() {
        return BookOrderDTO.builder().name(ANY_OTHER_NAME).quantity(-1).build();
    }

    public static List<BookOrderDTO> createExampleBookOrderDTOList() {
        var ordersList = new ArrayList<BookOrderDTO>();
        ordersList.add(createExampleBookOrderDTO());
        return ordersList;
    }

    public static List<BookOrderDTO> createExampleBookOrderDTOWithWrongNameList() {
        var ordersList = new ArrayList<BookOrderDTO>();
        ordersList.add(createExampleBookOrderDTOWithWrongName());
        return ordersList;
    }

    public static List<BookOrderDTO> createExampleBookOrderDTOWithoutStockList() {
        var ordersList = new ArrayList<BookOrderDTO>();
        ordersList.add(createExampleBookOrderDTOWithoutStock());
        return ordersList;
    }

    public static List<BookOrderDTO> createExampleBookOrderDTOWithNegativeList() {
        var ordersList = new ArrayList<BookOrderDTO>();
        ordersList.add(createExampleBookOrderDTOWithNegative());
        return ordersList;
    }

    public static List<BookOrderDTO> createDuplicatedBookOrderDTOList() {
        var ordersList = new ArrayList<BookOrderDTO>();
        ordersList.add(createExampleBookOrderDTO());
        ordersList.add(createExampleBookOrderDTO());
        return ordersList;
    }

    public static BookStock createExampleBookStock() {
        return BookStock.builder().id(ANY_ID).name(ANY_NAME).quantity(ANY_QUANTITY).build();
    }

    public static List<BookStock> createExampleBookStockList() {
        var stockList = new ArrayList<BookStock>();
        stockList.add(createExampleBookStock());
        return stockList;
    }

    public static BookStock createBookStockWithoutStock() {
        return BookStock.builder().id(ANY_ID).name(ANY_NAME).quantity(0).build();
    }

    public static Order createExampleOrder() {
        var bookOrderList = new ArrayList<BookOrder>();
        bookOrderList.add(createExampleBookOrder());
        return Order.builder().id(ANY_ID).bookOrderList(bookOrderList). build();
    }

    public static List<Order> createExampleOrderList() {
        var orderList = new ArrayList<Order>();
        orderList.add(createExampleOrder());
        return orderList;
    }

    public static List<Order> createExampleUnflattenedOrderList() {
        var orderList = new ArrayList<Order>();
        var bookOrderList1 = new ArrayList<BookOrder>();
        bookOrderList1.add(BookOrder.builder().id(ANY_ID).bookName(ANY_NAME).quantity(ANY_QUANTITY).build());
        bookOrderList1.add(BookOrder.builder().id(ANY_ID).bookName(ANY_OTHER_NAME).quantity(ANY_OTHER_QUANTITY).build());
        var bookOrderList2 = new ArrayList<BookOrder>();
        bookOrderList2.add(BookOrder.builder().id(ANY_ID).bookName(ANY_NAME).quantity(ANY_OTHER_QUANTITY).build());
        orderList.add(Order.builder().id(ANY_ID).bookOrderList(bookOrderList1).build());
        orderList.add(Order.builder().id(ANY_OTHER_ID).bookOrderList(bookOrderList2).build());
        return orderList;

    }

    public static List<FlattenedOrder> createExampleFlattenedOrderList() {
        var orderList = new ArrayList<FlattenedOrder>();
        orderList.add(FlattenedOrder.builder().orderId(ANY_ID).bookName(ANY_NAME).quantity(ANY_QUANTITY).build());
        orderList.add(FlattenedOrder.builder().orderId(ANY_ID).bookName(ANY_OTHER_NAME).quantity(ANY_OTHER_QUANTITY).build());
        orderList.add(FlattenedOrder.builder().orderId(ANY_OTHER_ID).bookName(ANY_NAME).quantity(ANY_OTHER_QUANTITY).build());
        return orderList;

    }

    public static CreateOrderRequest createExampleCreateOrderRequest() {
        return CreateOrderRequest.builder().bookOrderList(createExampleBookOrderDTOList()).build();
    }

    public static CreateOrderRequest createExampleCreateOrderRequestWithWrongName() {
        return CreateOrderRequest.builder().bookOrderList(createExampleBookOrderDTOWithWrongNameList()).build();
    }

    public static CreateOrderRequest createExampleCreateOrderRequestWithoutStock() {
        return CreateOrderRequest.builder().bookOrderList(createExampleBookOrderDTOWithoutStockList()).build();
    }

    public static CreateOrderRequest createExampleCreateOrderRequestWithNegative() {
        return CreateOrderRequest.builder().bookOrderList(createExampleBookOrderDTOWithNegativeList()).build();
    }

    public static CreateOrderResponse createExampleCreateOrderResponse() {
        return CreateOrderResponse.builder().id(ANY_ID).build();
    }
}

package com.adobe.bookstore.utils;

import com.adobe.bookstore.domain.BookOrder;
import com.adobe.bookstore.domain.Order;
import com.adobe.bookstore.dto.FlattenedOrder;

import java.util.ArrayList;
import java.util.List;

public class OrderUtils {

    /**
     * Flatten a list of Order object
     *
     * @param unflattenedOrders List of Order object.
     * @return List of FlattenedOrder object
     */
    public static List<FlattenedOrder> flatten(List<Order> unflattenedOrders) {
        var flattenedOrderList = new ArrayList<FlattenedOrder>();
        for (Order order : unflattenedOrders) {
            var orderId = order.getId();
            for (BookOrder bookOrder : order.getBookOrderList()) {
                flattenedOrderList.add(new FlattenedOrder(orderId, bookOrder.getBookName(), bookOrder.getQuantity()));
            }
        }
        return flattenedOrderList;
    }

}

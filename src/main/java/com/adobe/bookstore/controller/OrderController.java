package com.adobe.bookstore.controller;

import com.adobe.bookstore.dto.CreateOrderRequest;
import com.adobe.bookstore.dto.CreateOrderResponse;
import com.adobe.bookstore.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Orders Controller
 */
@RestController
@Api(value = "order", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Creates a new Order.
     *
     * @param order Create Order Request.
     * @return The ID of the created Order.
     */
    @RequestMapping(value = "/order",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateOrderResponse> createOrder(
            @ApiParam(value = "New book order request", required = true) @Valid @RequestBody
                    CreateOrderRequest order) {
        return orderService.processOrder(order.getBookOrderList());
    }

    /**
     * @param format Indicates the response format. JSON (default) or CSV.
     * @return A list of Orders in JSON or CSV format.
     */
    @RequestMapping(value = "/order",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, "text/csv"})
    public ResponseEntity<byte[]> getOrders(@RequestParam String format) {
        return orderService.getOrdersList(format);
    }
}





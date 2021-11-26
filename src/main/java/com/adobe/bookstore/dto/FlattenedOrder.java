package com.adobe.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Flattened version of an Order object
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlattenedOrder {
    private UUID orderId;

    private String bookName;

    private Integer quantity;
}

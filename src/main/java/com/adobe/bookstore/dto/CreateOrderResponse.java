package com.adobe.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Response for the createOrder endpoint
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderResponse {
    private UUID id;

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                '}';
    }
}

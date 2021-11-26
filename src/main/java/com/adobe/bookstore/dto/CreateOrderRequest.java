package com.adobe.bookstore.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Request Body for the createOrder endpoint
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderRequest {
    @NotNull
    @NotEmpty
    @Valid
    private List<BookOrderDTO> bookOrderList;

    @Override
    public String toString() {
        return "{" +
                "bookOrderList=" + bookOrderList +
                '}';
    }
}

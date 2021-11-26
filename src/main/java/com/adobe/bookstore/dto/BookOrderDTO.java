package com.adobe.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Book Order DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookOrderDTO {
    @NotBlank
    private String name;

    @NotNull
    @Min(1)
    private Integer quantity;

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}

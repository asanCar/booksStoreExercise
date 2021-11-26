package com.adobe.bookstore.domain;

import com.adobe.bookstore.dto.BookOrderDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

/**
 * BookOrder Entity
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private UUID id;

    private String bookName;

    private Integer quantity;

    public BookOrder(BookOrderDTO bookOrderDTO) {
        bookName = bookOrderDTO.getName();
        quantity = bookOrderDTO.getQuantity();
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

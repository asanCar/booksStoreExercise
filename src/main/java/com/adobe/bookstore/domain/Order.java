package com.adobe.bookstore.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Order Entity
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orders")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private UUID id;

    @ElementCollection
    private List<BookOrder> bookOrderList;

    public UUID getId() {
        return id;
    }

    public List<BookOrder> getBookOrderList() {
        return bookOrderList;
    }

    public void setBookOrderList(List<BookOrder> bookOrderList) {
        this.bookOrderList = bookOrderList;
    }
}

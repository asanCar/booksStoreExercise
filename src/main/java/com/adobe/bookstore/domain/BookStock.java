package com.adobe.bookstore.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

/**
 * BookStock Entity
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookStock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private Integer quantity;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "BookStock{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}

package com.adobe.bookstore.repository;

import com.adobe.bookstore.domain.BookOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repository for BookOrder Entities
 */
public interface BookOrderRepository extends JpaRepository<BookOrder, UUID> {
}
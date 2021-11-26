package com.adobe.bookstore.repository;

import com.adobe.bookstore.domain.BookStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for BookStock Entities
 */
public interface BookStockRepository extends JpaRepository<BookStock, UUID> {
    Optional<BookStock> findByName(String bookName);
}
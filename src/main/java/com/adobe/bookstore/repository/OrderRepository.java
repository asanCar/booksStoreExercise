package com.adobe.bookstore.repository;

import com.adobe.bookstore.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repository for Order Entities
 */
public interface OrderRepository extends JpaRepository<Order, UUID> {
}
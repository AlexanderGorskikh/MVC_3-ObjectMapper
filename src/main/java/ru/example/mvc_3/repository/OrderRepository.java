package ru.example.mvc_3.repository;

import ru.example.mvc_3.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {
}

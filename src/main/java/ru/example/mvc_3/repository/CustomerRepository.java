package ru.example.mvc_3.repository;

import ru.example.mvc_3.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

package ru.example.mvc_3.service;

import jakarta.transaction.Transactional;
import ru.example.mvc_3.entity.Order;
import ru.example.mvc_3.entity.Product;
import ru.example.mvc_3.exception.ResourceNotFoundException;
import ru.example.mvc_3.repository.OrderRepository;
import ru.example.mvc_3.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public Order createOrder(Order order) {
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (Product product : order.getProducts()) {
            Product lockedProduct = productRepository.findProductForUpdate(product.getProductId());
            BigDecimal productTotal = lockedProduct.getPrice().multiply(BigDecimal.valueOf(lockedProduct.getQuantityInStock()));
            totalPrice = totalPrice.add(productTotal);
        }
        order.setTotalPrice(totalPrice);
        return orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    public void deleteOrder(Long id) {
        Order order = getOrderById(id);
        orderRepository.delete(order);
    }
}

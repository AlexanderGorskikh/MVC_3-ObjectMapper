package ru.example.mvc_3.controller;

import lombok.RequiredArgsConstructor;
import ru.example.mvc_3.entity.Order;
import ru.example.mvc_3.service.OrderService;
import ru.example.mvc_3.util.OrderJsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<String>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<String> jsonOrders = orders.stream()
                .map(OrderJsonUtil::toJson)
                .collect(Collectors.toList());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonOrders);
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody String orderJson) {
        Order order = OrderJsonUtil.fromJson(orderJson);
        Order savedOrder = orderService.createOrder(order);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(OrderJsonUtil.toJson(savedOrder));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(OrderJsonUtil.toJson(order));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }
}

package com.example.project.controller;

import com.example.project.entity.Order;
import com.example.project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<?> getOrderByOrderId(@RequestParam("orderId") String orderId) {
        Optional<Order> order = orderService.findByOrderId(orderId);
        if (order.isPresent()) {
            return ResponseEntity.ok(order.get());
        }
        else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order with orderId " + orderId + " not found");
        }
    }

    @GetMapping("/customerId")
    public ResponseEntity<?> getOrderByCustomerId(@RequestParam("customerId") String customerId) {
        List<Order> orders = orderService.findByCustomerId(customerId);
        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Orders belongs to customer with customerId " + customerId + " not found");
        }
        else
        {
            return ResponseEntity.ok(orders);
        }
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        Optional<Order> savedOrder = orderService.findByOrderId(order.getOrderId());
        if (savedOrder.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Order with orderId " + order.getOrderId() + " already exists");
        }
        else
        {
            Map<String,String> info = new HashMap<>();
            orderService.save(order);
            info.put("orderId",order.getOrderId());
            info.put("message","Order created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(info);
        }

    }

    @PutMapping
    public ResponseEntity<?> updateOrder(@RequestBody Order order) {
        Map<String,String> info = new HashMap<>();
        Optional<Order> oldOrder = orderService.findByOrderId(order.getOrderId());
        if (oldOrder.isPresent()) {
            orderService.save(order);
            info.put("orderId",order.getOrderId());
            info.put("message","Order updated successfully");
            return ResponseEntity.ok(info);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order with orderId " + order.getOrderId() + " not found");
    }

    @DeleteMapping
    public ResponseEntity<Map<String,String>> deleteOrder(@RequestParam("orderId") String orderId) {
        Map<String,String> info = new HashMap<>();
        orderService.deleteByOrderId(orderId);
        info.put("orderId",orderId);
        info.put("message","Order deleted successfully");
        return ResponseEntity.ok(info);
    }

}

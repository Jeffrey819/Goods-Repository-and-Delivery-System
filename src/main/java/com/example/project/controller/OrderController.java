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
    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<Order> getOrderByOrderId(@RequestParam("orderId") String orderId) {
        Optional<Order> order = orderService.findByOrderId(orderId);
        if (order.isPresent()) {
            return ResponseEntity.ok(order.get());
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/customerId")
    public ResponseEntity<List<Order>> getOrderByCustomerId(@RequestParam("customerId") String customerId) {
        Optional<List<Order>> orders = orderService.findByCustomerId(customerId);
        if (orders.isPresent()) {
            return ResponseEntity.ok(orders.get());
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Map<String,String>> createOrder(@RequestBody Order order) {
        Map<String,String> info = new HashMap<>();
        orderService.save(order);
        info.put("orderId",order.getOrderId());
        info.put("message","Order created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(info);

    }

    @PutMapping
    public ResponseEntity<Map<String,String>> updateOrder(@RequestBody Order order) {
        Map<String,String> info = new HashMap<>();
        Optional<Order> oldOrder = orderService.findByOrderId(order.getOrderId());
        if (oldOrder.isPresent()) {
            orderService.save(order);
            info.put("orderId",order.getOrderId());
            info.put("message","Order updated successfully");
            return ResponseEntity.ok(info);
        }
        return ResponseEntity.notFound().build();
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

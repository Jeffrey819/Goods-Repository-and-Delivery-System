package com.example.project.controller;

import com.example.project.entity.Customer;
import com.example.project.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<?> getCustomerById(@RequestParam String customerId) {
        Optional<Customer> customer = customerService.findByCustomerId(customerId);
        if(customer.isPresent())
            return ResponseEntity.ok(customer.get());
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer with customer id " + customerId + " not found");
    }

    @PostMapping
    public ResponseEntity<Map<String,String>> createCustomer(@RequestBody Customer customer) {
         Optional<Customer> savedCustomer= customerService.findByCustomerId(customer.getCustomerId());
         if(savedCustomer.isPresent())
         {
             Map<String,String> info = new HashMap<>();
             info.put("customerId", customer.getCustomerId());
             info.put("message","CustomerId already exists");
             return ResponseEntity.status(HttpStatus.CONFLICT).body(info);
         }
         else
         {
             customerService.save(customer);
             Map<String,String> info = new HashMap<>();
             info.put("customerId", customer.getCustomerId());
             info.put("message", "Customer created successfully");
             return ResponseEntity.status(HttpStatus.CREATED).body(info);
         }

    }

    @PutMapping
    public ResponseEntity<?> updateCustomer(@RequestBody Customer updatedCustomer) {
        Optional<Customer> customer = customerService.findByCustomerId(updatedCustomer.getCustomerId());
        if(customer.isPresent()) {
            customerService.save(updatedCustomer);
            Map<String,String> info = new HashMap<>();
            info.put("customerId", updatedCustomer.getCustomerId());
            info.put("message", "Customer updated successfully");
            return ResponseEntity.ok(info);
        }
        else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with customer id " + updatedCustomer.getCustomerId() + " not found");
        }
    }

    @DeleteMapping
    public ResponseEntity<Map<String,String>> deleteCustomer(@RequestParam String customerId) {
        customerService.deleteByCustomerId(customerId);
        Map<String,String> info = new HashMap<>();
        info.put("customerId", customerId);
        info.put("message", "Customer deleted successfully");
        return ResponseEntity.ok(info);
    }


}

package com.example.project.controller;

import com.example.project.entity.Customer;
import com.example.project.repository.CustomerRepository;
import com.example.project.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<Customer> getCustomerById(@RequestParam String customerId) {
        Optional<Customer> customer = customerService.findByCustomerId(customerId);
        if(customer.isPresent())
            return ResponseEntity.ok(customer.get());
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
         Customer savedCustomer = customerService.save(customer);
         return ResponseEntity.ok().body(savedCustomer);
    }//Should send String message at the same time? Or should send back the saved customer or not?

    @DeleteMapping
    public ResponseEntity<String> deleteCustomer(@RequestParam String customerId) {
        customerService.deleteByCustomerId(customerId);
        return ResponseEntity.ok().body("Customer deleted successfully");
    }


}

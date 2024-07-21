package com.example.project.service;

import com.example.project.entity.Customer;
import com.example.project.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    public Optional<Customer> findByCustomerId(String customerId) {
        return customerRepository.findById(customerId);

    }

    public void deleteByCustomerId(String customerId) {
        customerRepository.deleteById(customerId);
    }

}

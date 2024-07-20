package com.example.project.service;

import com.example.project.entity.Customer;
import com.example.project.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer save(Customer customer) {
        //Check the validity of customerId
        if (customer.getCustomerId().length() <= 36)
        {
            return customerRepository.save(customer);
        }
        else
            return null;
    }

    public List<Customer> findByName(String name) {
        return customerRepository.findByName(name);
    }

    public Customer findByCustomerId(String customerId) {
        return customerRepository.findByCustomerId(customerId);
    }

}

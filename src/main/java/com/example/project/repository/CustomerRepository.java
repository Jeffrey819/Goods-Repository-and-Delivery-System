package com.example.project.repository;

import com.example.project.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    List<Customer> findByName(String name);

    Customer findByCustomerId(String customerId);


}

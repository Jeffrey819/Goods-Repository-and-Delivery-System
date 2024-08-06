package com.example.project.repository;

import com.example.project.entity.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, String> {
    List<ServiceRequest> findByCustomerId(String customerId);

}

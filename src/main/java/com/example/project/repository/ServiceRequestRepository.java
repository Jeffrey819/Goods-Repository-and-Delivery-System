package com.example.project.repository;

import com.example.project.entity.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, String> {
    Optional<List<ServiceRequest>> findByCustomerId(String customerId);

}

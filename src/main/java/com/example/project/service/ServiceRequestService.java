package com.example.project.service;

import com.example.project.entity.ServiceRequest;
import com.example.project.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceRequestService {
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    public void save(ServiceRequest serviceRequest) {
        serviceRequestRepository.save(serviceRequest);
    }

    public Optional<ServiceRequest> findByRequestId(String requestId) {
        Optional<ServiceRequest> request = serviceRequestRepository.findById(requestId);
        return request;
    }

    public Optional<List<ServiceRequest>> findByCustomerId(String customerId) {
        Optional<List<ServiceRequest>> requests = serviceRequestRepository.findByCustomerId(customerId);
        return requests;
    }

    public void deleteByRequestId(String requestId) {
        serviceRequestRepository.deleteById(requestId);
    }


}

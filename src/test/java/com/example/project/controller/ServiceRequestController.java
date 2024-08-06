package com.example.project.controller;

import com.example.project.entity.ServiceRequest;
import com.example.project.service.ServiceRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("servicerequests")
public class ServiceRequestController {
    private final ServiceRequestService serviceRequestService;

    public ServiceRequestController(ServiceRequestService serviceRequestService) {
        this.serviceRequestService = serviceRequestService;
    }

    @GetMapping
    public ResponseEntity<?> getByRequestId(@RequestParam("requestId") String requestId) {
        Optional<ServiceRequest> request = serviceRequestService.findByRequestId(requestId);
        if (request.isPresent()) {
            return ResponseEntity.ok(request.get());
        }
        else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Service request with requestId " + requestId + " not found");
        }
    }

    @GetMapping("customerId")
    public ResponseEntity<?> getByCustomerId(@RequestParam("customerId") String customerId) {
        List<ServiceRequest> requests = serviceRequestService.findByCustomerId(customerId);
        if (requests.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Service request of Customer " + customerId + " not found");
        }
        else
        {
            return ResponseEntity.ok(requests);
        }
    }

    @PostMapping
    public ResponseEntity<?> createRequest(@RequestBody ServiceRequest newRequest) {
        Optional<ServiceRequest> request = serviceRequestService.findByRequestId(newRequest.getRequestId());
        if (request.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Service request with requestId " + newRequest.getRequestId() + " already exists");
        }
        else
        {
            serviceRequestService.save(newRequest);
            Map<String, String> info = new HashMap<>();
            info.put("requestId", newRequest.getRequestId());
            info.put("message","request created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(info);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateRequest(@RequestBody ServiceRequest newRequest) {
        Optional<ServiceRequest> request = serviceRequestService.findByRequestId(newRequest.getRequestId());
        if (request.isPresent()) {
            serviceRequestService.save(newRequest);
            Map<String, String> info = new HashMap<>();
            info.put("requestId", newRequest.getRequestId());
            info.put("message","request updated successfully");
            return ResponseEntity.status(HttpStatus.OK).body(info);
        }
        else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Service request with requestId " + newRequest.getRequestId() + " not found");
        }
    }

    @DeleteMapping
    public ResponseEntity<Map<String,String>> deleteRequest(@RequestParam("requestId") String requestId) {
        serviceRequestService.deleteByRequestId(requestId);
        Map<String, String> info = new HashMap<>();
        info.put("requestId", requestId);
        info.put("message","request deleted successfully");
        return ResponseEntity.status(HttpStatus.OK).body(info);
    }

}

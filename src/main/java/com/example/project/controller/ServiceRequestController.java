package com.example.project.controller;

import com.example.project.entity.ServiceRequest;
import com.example.project.service.ServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("servicerequest")
public class ServiceRequestController {
    @Autowired
    private ServiceRequestService serviceRequestService;

    @GetMapping("/requestId")
    public ResponseEntity<ServiceRequest> getByRequestId(@RequestParam("requestId") String requestId) {
        Optional<ServiceRequest> request = serviceRequestService.findByRequestId(requestId);
        if (request.isPresent()) {
            return ResponseEntity.ok(request.get());
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("customerId")
    public ResponseEntity<List<ServiceRequest>> getByCustomerId(@RequestParam("customerId") String customerId) {
        Optional<List<ServiceRequest>> requests = serviceRequestService.findByCustomerId(customerId);
        if (requests.isPresent()) {
            return ResponseEntity.ok(requests.get());
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Map<String,String>> createRequest(@RequestBody ServiceRequest newRequest) {
        Optional<ServiceRequest> request = serviceRequestService.findByRequestId(newRequest.getRequestId());
        if (request.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
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
    public ResponseEntity<Map<String,String>> updateRequest(@RequestBody ServiceRequest newRequest) {
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
            return ResponseEntity.notFound().build();
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

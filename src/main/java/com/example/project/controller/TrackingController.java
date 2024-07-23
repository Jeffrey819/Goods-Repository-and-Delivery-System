package com.example.project.controller;

import com.example.project.entity.Tracking;
import com.example.project.service.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("tracking")
public class TrackingController {
    private final TrackingService trackingService;

    public TrackingController(TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    @GetMapping
    public ResponseEntity<List<Tracking>> findByOrderId(@RequestParam("orderId") String orderId) {
        Optional<List<Tracking>> trackingList = trackingService.findByOrderId(orderId);
        if (trackingList.isPresent()) {
            return ResponseEntity.ok(trackingList.get());
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Map<String,String>> createTracking(@RequestBody Tracking newTracking){
        trackingService.save(newTracking);
        Map<String,String> info = new HashMap<>();
        info.put("trackingId", newTracking.getTrackingId());
        info.put("orderId", newTracking.getOrderId());
        info.put("message","tracking created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(info);
    }

    @DeleteMapping
    public ResponseEntity<Map<String,String>> deleteTracking(@RequestParam("orderId") String orderId){
        trackingService.deleteByOrderId(orderId);
        Map<String,String> info = new HashMap<>();
        info.put("orderId",orderId);
        info.put("message","trackingList deleted successfully");
        return ResponseEntity.ok(info);
    }
}

package com.example.project.service;

import com.example.project.entity.Tracking;
import com.example.project.repository.TrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrackingService {
    private final TrackingRepository trackingRepository;

    public TrackingService(TrackingRepository trackingRepository) {
        this.trackingRepository = trackingRepository;
    }

    public Optional<List<Tracking>> findByOrderId(String orderId)
    {
        Optional<List<Tracking>> trackingList = trackingRepository.findByOrderId(orderId);
        return trackingList;
    }

    public void save(Tracking tracking)
    {
        trackingRepository.save(tracking);
    }

    public void deleteByOrderId(String orderId)
    {
        trackingRepository.deleteByOrderId(orderId);
    }


}

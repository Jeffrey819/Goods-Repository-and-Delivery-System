package com.example.project.repository;

import com.example.project.entity.Tracking;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackingRepository extends JpaRepository<Tracking, String> {
    List<Tracking> findByOrderId(String orderId);

    @Transactional
    void deleteByOrderId(String orderId);
}

package com.example.project.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "Tracking")
public class Tracking {
    @Id
    @Column(name = "TrackingID",nullable = false,length = 36)
    private String trackingId;

    @Column(name = "OrderID",nullable = false,length = 36)
    private String orderId;

    @Column(name = "Location",nullable = false,length = 255)
    private String location;

    @Column(name = "Timestamp",nullable = false,columnDefinition = "DATETIME")
    private LocalDateTime timestamp;

    //Constructors
    public Tracking(){}

    public Tracking(String trackingId, String orderId, String location, LocalDateTime timestamp) {
        this.trackingId = trackingId;
        this.orderId = orderId;
        this.location = location;
        this.timestamp = timestamp;
    }

    //Getters and Setters
    public String getTrackingId() {
        return trackingId;
    }
    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }


}

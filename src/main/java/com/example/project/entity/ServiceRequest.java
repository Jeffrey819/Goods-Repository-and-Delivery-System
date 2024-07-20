package com.example.project.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "ServiceRequests")
public class ServiceRequest {
    @Id
    @Column(name = "RequestID",nullable = false,length = 36)
    private String requestId;

    @Column(name = "CustomerID",nullable = false,length = 36)
    private String customerId;

    @Column(name = "Description",columnDefinition = "TEXT")
    private String description;

    @Column(name = "Status",nullable = false,length = 20)
    private String status;

    //Constructors
    public ServiceRequest() {}

    public ServiceRequest(String requestId, String customerId, String description, String status) {
        this.requestId = requestId;
        this.customerId = customerId;
        this.description = description;
        this.status = status;
    }

    //Getters and Setters
    public String getRequestId() {
        return requestId;
    }
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

}

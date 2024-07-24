package com.example.project.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @Column(name = "OrderID",nullable = false,length = 36)
    private String orderId;

    @Column(name = "CustomerID",nullable = false,length = 36)
    private String customerId;

    @Column(name = "Orderdate",nullable = false,columnDefinition = "DATETIME")
    private LocalDateTime orderDate;

    @Column(name = "Status",nullable = false,length = 20)
    private String status;

    @Column(name = "Totalamount",nullable = false,precision = 10,scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "Paymentmethod",nullable = false,length = 50)
    private String paymentMethod;

    @Column(name = "Deliverymethod",nullable = false,length = 50)
    private String deliveryMethod;

    @Column(name = "Remarks",nullable = false,columnDefinition = "TEXT")
    private String remarks;

    //Constructors
    public Order(){}

    public Order(String orderId,String customerId,LocalDateTime orderDate,String status,BigDecimal totalAmount,String paymentMethod,String deliveryMethod,String remarks ){
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.deliveryMethod = deliveryMethod;
        this.remarks = remarks;
    }

    //Getters and Setters
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }
    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}

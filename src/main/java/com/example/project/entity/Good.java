package com.example.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Goods")
public class Good {
    @Id
    @Column(name = "GoodID", nullable = false, length = 36)
    private String goodId;

    @Column(name = "OrderID")
    private String orderId;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "Quantity")
    private int quantity;

    //Constructors
    public Good(){}

    public Good(String goodId, String orderId, String description, int quantity) {
        this.goodId = goodId;
        this.orderId = orderId;
        this.description = description;
        this.quantity = quantity;
    }

    //Getters and Setters
    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}

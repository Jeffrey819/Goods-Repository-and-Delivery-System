package com.example.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Customers")
public class Customer {
    //Primary key: CustomerID
    @Id
    @Column(name = "CustomerID", nullable = false, length = 36)
    private String customerId;

    @Column(name = "Name", length = 100)
    private String name;

    @Column(name = "Email", length = 100)
    private String email;

    @Column(name = "Phone", length = 20)
    private String phone;

    public Customer() {
    }

    public Customer(String customerId,String name,String email,String phone){
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
    //Getters and Setters
    public String getCustomerId(){
        return customerId;
    }
    public void setCustomerId(String customerId){
        this.customerId = customerId;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getPhone(){
        return phone;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }



}

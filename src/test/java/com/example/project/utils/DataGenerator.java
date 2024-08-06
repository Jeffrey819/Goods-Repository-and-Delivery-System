package com.example.project.utils;
import com.example.project.entity.*;
import org.glassfish.jaxb.runtime.v2.schemagen.xmlschema.LocalAttribute;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DataGenerator {
    public static List<Customer> generateRandomCustomers(int count) {
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Customer customer = new Customer();
            customer.setCustomerId("customerId" + i);
            customer.setEmail("email"+i+"@gmail.com");
            customer.setName("name"+i);
            customer.setPhone("phone"+i);
            customers.add(customer);
        }
        return customers;
    }
    
    public static List<Good> generateRandomGoods(int count){
        List<Good> goods = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Good good = new Good();
            good.setGoodId("goodId"+i);
            good.setDescription("description"+i);
            good.setQuantity(i);
            good.setOrderId("orderId"+i);
            goods.add(good);
        }
        return goods;
    }

    public static List<Order> generateRandomOrders(int count){
        List<Order> orders = new ArrayList<>();
        LocalDateTime t = LocalDateTime.now();
        for (int i = 0; i < count; i++) {
            Order order = new Order();
            order.setOrderId("orderId"+i);
            order.setCustomerId("customerId"+i);
            order.setOrderDate(t);
            order.setRemarks("remarks"+i);
            order.setStatus("status"+i);
            order.setDeliveryMethod("deliveryMethod"+i);
            order.setPaymentMethod("paymentMethod"+i);
            order.setTotalAmount(BigDecimal.valueOf(i));
            orders.add(order);
        }
        return orders;
    }

    public static List<Report> generateRandomReports(int count){
        List<Report> reports = new ArrayList<>();
        LocalDateTime t = LocalDateTime.now();
        for (int i = 0; i < count; i++) {
            Report report = new Report();
            report.setReportId("reportId"+i);
            report.setUserId("userId"+i);
            report.setParameters("parameters"+i);
            report.setGeneratedDate(t);
            reports.add(report);
        }
        return reports;
    }

    public static List<ServiceRequest> generateRandomServiceRequests(int count){
        List<ServiceRequest> serviceRequests = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ServiceRequest serviceRequest = new ServiceRequest();
            serviceRequest.setRequestId("requestId"+i);
            serviceRequest.setCustomerId("customerId"+i);
            serviceRequest.setDescription("description"+i);
            serviceRequest.setStatus("status"+i);
            serviceRequests.add(serviceRequest);
        }
        return serviceRequests;
    }

    public static List<Tracking> generateRandomTrackings(int count){
        List<Tracking> trackings = new ArrayList<>();
        LocalDateTime t = LocalDateTime.now();
        for (int i = 0; i < count; i++) {
            Tracking tracking = new Tracking();
            tracking.setTrackingId("trackingId"+i);
            tracking.setLocation("location"+i);
            tracking.setOrderId("orderId"+i);
            tracking.setTimestamp(t);
            trackings.add(tracking);
        }
        return trackings;
    }

    public static List<User> generateRandomUsers(int count){
        List<User> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setUserId("userId"+i);
            user.setPassword("password"+i);
            user.setRole("role"+i);
            user.setUsername("username"+i);
            users.add(user);
        }
        return users;
    }


}

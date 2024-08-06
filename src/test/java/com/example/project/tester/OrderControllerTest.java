package com.example.project.tester;

import com.example.project.entity.Good;
import com.example.project.entity.Order;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import com.example.project.utils.DataGenerator;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateAndFetchOrders() throws Exception {
        List<Order> orders = DataGenerator.generateRandomOrders(20000);
        //Testing create an order
        for (Order order : orders) {
            mockMvc.perform(post("/orders")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"orderId\":\"" + order.getOrderId() + "\", \"customerId\":\"" + order.getCustomerId() + "\", \"orderDate\":\"" + order.getOrderDate() + "\",\"status\": \""+order.getStatus()+"\",\"totalAmount\": \""+order.getTotalAmount()+"\",\"paymentMethod\": \""+order.getPaymentMethod()+"\",\"deliveryMethod\": \""+order.getDeliveryMethod()+"\",\"remarks\": \""+order.getRemarks()+"\"}"))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.orderId").value(order.getOrderId()))
                    .andExpect(jsonPath("$.message").value("Order created successfully"));
        }
        System.out.println("Complete testing create order");

        //Testing get a order by orderId
        for (Order order : orders) {
            mockMvc.perform(get("/orders").param("orderId", "" + order.getOrderId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.orderId").value(order.getOrderId()))
                    .andExpect(jsonPath("$.customerId").value(order.getCustomerId()))
                    .andExpect(jsonPath("$.status").value(order.getStatus()))
                    .andExpect(jsonPath("$.paymentMethod").value(order.getPaymentMethod()))
                    .andExpect(jsonPath("$.deliveryMethod").value(order.getDeliveryMethod()))
                    .andExpect(jsonPath("$.remarks").value(order.getRemarks()));
        }
        System.out.println("Complete testing get a order");

        //Testing get orders for a customer
        for (Order order : orders) {
            mockMvc.perform(get("/orders/customerId")
                    .param("customerId", "" + order.getCustomerId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].orderId").value(order.getOrderId()))
                    .andExpect(jsonPath("$[0].customerId").value(order.getCustomerId()))
                    .andExpect(jsonPath("$[0].status").value(order.getStatus()))
                    .andExpect(jsonPath("$[0].paymentMethod").value(order.getPaymentMethod()))
                    .andExpect(jsonPath("$[0].deliveryMethod").value(order.getDeliveryMethod()))
                    .andExpect(jsonPath("$[0].remarks").value(order.getRemarks()));
        }
        System.out.println("Complete testing get orders for a customer");

        //Testing update an order
        for (Order order : orders) {
            mockMvc.perform(put("/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"orderId\":\"" + order.getOrderId() + "\", \"customerId\":\"" + order.getCustomerId() + "\", \"orderDate\":\"" + order.getOrderDate() + "\",\"status\": \""+order.getStatus()+"updated"+"\",\"totalAmount\": \""+order.getTotalAmount()+"\",\"paymentMethod\": \""+order.getPaymentMethod()+"\",\"deliveryMethod\": \""+order.getDeliveryMethod()+"\",\"remarks\": \""+order.getRemarks()+"\"}"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("Order updated successfully"));
        }
        System.out.println("Complete testing update an order");

    }
}
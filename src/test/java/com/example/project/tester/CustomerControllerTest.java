package com.example.project.tester;

import com.example.project.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import com.example.project.utils.DataGenerator;
import org.springframework.http.MediaType;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    public CustomerControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }
    @Test
    public void testCreateAndFetchCustomers() throws Exception {
        List<Customer> customers = DataGenerator.generateRandomCustomers(20000);
        //Testing create customer
        for (Customer customer : customers) {
            mockMvc.perform(post("/customers")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"customerId\":\"" + customer.getCustomerId() + "\", \"name\":\"" + customer.getName() + "\", \"email\":\"" + customer.getEmail() + "\",\"phone\": \""+customer.getPhone()+"\"}"))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.customerId").value(customer.getCustomerId()))
                    .andExpect(jsonPath("$.message").value("Customer created successfully"));
        }
        System.out.println("Complete testing create customer");

        //Testing interface for get a customer by customerId
        for (Customer customer : customers) {
            mockMvc.perform(get("/customers")
                            .param("customerId", customer.getCustomerId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.customerId").value(customer.getCustomerId()))
                    .andExpect(jsonPath("$.name").value(customer.getName()))
                    .andExpect(jsonPath("$.email").value(customer.getEmail()))
                    .andExpect(jsonPath("$.phone").value(customer.getPhone()));
        }
        System.out.println("Complete testing for getting customers");
        //Testing interface for update user
        for (Customer customer : customers) {
            mockMvc.perform(put("/customers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"customerId\":\"" + customer.getCustomerId() + "\", \"name\":\"" + customer.getName()+"updated" + "\", \"email\":\"" + customer.getEmail() + "\",\"phone\": \""+customer.getPhone()+"\"}"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.customerId").value(customer.getCustomerId()))
                    .andExpect(jsonPath("$.message").value("Customer updated successfully"));
        }
        System.out.println("Complete testing for updating customers");

    }
}
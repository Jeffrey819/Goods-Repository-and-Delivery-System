package com.example.project.tester;

import com.example.project.entity.ServiceRequest;
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
public class ServiceRequestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateAndFetchServiceRequests() throws Exception {
        List<ServiceRequest> requests = DataGenerator.generateRandomServiceRequests(20000);
        //Testing create a serviceRequest
        for (ServiceRequest request : requests) {
            mockMvc.perform(post("/servicerequests")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"requestId\":\"" + request.getRequestId() + "\", \"customerId\":\"" + request.getCustomerId() + "\", \"description\":\"" + request.getDescription() + "\",\"status\": \""+request.getStatus()+"\"}"))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.requestId").value(request.getRequestId()))
                    .andExpect(jsonPath("$.message").value("request created successfully"));
        }
        System.out.println("Complete testing create service requests");
        //Testing interface for get a request by requestId

        for (ServiceRequest request : requests) {
            mockMvc.perform(get("/servicerequests")
                            .param("requestId", request.getRequestId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.requestId").value(request.getRequestId()))
                    .andExpect(jsonPath("$.customerId").value(request.getCustomerId()))
                    .andExpect(jsonPath("$.description").value(request.getDescription()))
                    .andExpect(jsonPath("$.status").value(request.getStatus()));
        }
        System.out.println("Complete testing get a service request");

        //Testing get requests by customerId
        for (ServiceRequest request : requests) {
            mockMvc.perform(get("/servicerequests/customerId").param("customerId", request.getCustomerId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].requestId").value(request.getRequestId()))
                    .andExpect(jsonPath("$[0].customerId").value(request.getCustomerId()))
                    .andExpect(jsonPath("$[0].description").value(request.getDescription()))
                    .andExpect(jsonPath("$[0].status").value(request.getStatus()));
        }
        System.out.println("Complete testing get service requests of a customer");

        //Testing interface for update service request
        for (ServiceRequest request : requests) {
            mockMvc.perform(put("/servicerequests")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"requestId\":\"" + request.getRequestId() + "\", \"customerId\":\"" + request.getCustomerId() + "\", \"description\":\"" + request.getDescription() + "\",\"status\": \""+request.getStatus()+"updated"+"\"}"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.requestId").value(request.getRequestId()))
                    .andExpect(jsonPath("$.message").value("request updated successfully"));
        }
        System.out.println("Complete testing for updating service request");
    }
}
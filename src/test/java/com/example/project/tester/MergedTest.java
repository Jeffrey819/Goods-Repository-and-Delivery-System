package com.example.project.tester;

import com.example.project.entity.Tracking;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class MergedTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void runAllTests() throws Exception {
        CustomerControllerTest customerControllerTest = new CustomerControllerTest(mockMvc);
        customerControllerTest.testCreateAndFetchCustomers();

        GoodControllerTest goodControllerTest = new GoodControllerTest(mockMvc);
        goodControllerTest.testCreateAndFetchGoods();

        OrderControllerTest orderControllerTest = new OrderControllerTest(mockMvc);
        orderControllerTest.testCreateAndFetchOrders();

        ReportControllerTest reportControllerTest = new ReportControllerTest(mockMvc);
        reportControllerTest.testCreateAndFetchReports();

        ServiceRequestControllerTest serviceRequestControllerTest = new ServiceRequestControllerTest(mockMvc);
        serviceRequestControllerTest.testCreateAndFetchServiceRequests();

        TrackingControllerTest trackingControllerTest = new TrackingControllerTest(mockMvc);
        trackingControllerTest.testCreateAndFetchTrackings();

        UserControllerTest userControllerTest = new UserControllerTest(mockMvc);
        userControllerTest.testCreateAndFetchUsers();

        // Add other test methods here
    }
}

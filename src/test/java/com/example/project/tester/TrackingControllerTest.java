package com.example.project.tester;

import com.example.project.entity.Tracking;
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
public class TrackingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateAndFetchTrackings() throws Exception {
        List<Tracking> trackings = DataGenerator.generateRandomTrackings(20000);
        //Testing create a serviceRequest
        for (Tracking tracking : trackings) {
            mockMvc.perform(post("/tracking")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"trackingId\":\"" + tracking.getTrackingId() + "\", \"orderId\":\"" + tracking.getOrderId() + "\", \"location\":\"" + tracking.getLocation() + "\",\"timestamp\": \""+tracking.getTimestamp()+"\"}"))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.trackingId").value(tracking.getTrackingId()))
                    .andExpect(jsonPath("$.orderId").value(tracking.getOrderId()))
                    .andExpect(jsonPath("$.message").value("tracking created successfully"));
        }
        System.out.println("Complete testing create a tracking");
        //Testing interface for get trackings for a order

        for (Tracking tracking : trackings) {
            mockMvc.perform(get("/tracking")
                            .param("orderId", tracking.getOrderId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].trackingId").value(tracking.getTrackingId()))
                    .andExpect(jsonPath("$[0].orderId").value(tracking.getOrderId()))
                    .andExpect(jsonPath("$[0].location").value(tracking.getLocation()));
        }
        System.out.println("Complete testing get a tracking list");
    }
}

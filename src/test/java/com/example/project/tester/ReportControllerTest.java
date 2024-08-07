package com.example.project.tester;

import com.example.project.entity.Report;
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
public class ReportControllerTest {
    @Autowired
    private MockMvc mockMvc;

    public ReportControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }
    @Test
    public void testCreateAndFetchReports() throws Exception {
        List<Report> reports = DataGenerator.generateRandomReports(20000);
        //Testing create a report
        for (Report report : reports) {
            mockMvc.perform(post("/reports")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"reportId\":\"" + report.getReportId() + "\", \"userId\":\"" + report.getUserId() + "\", \"generatedDate\":\"" + report.getGeneratedDate() + "\",\"parameters\": \""+report.getParameters()+"\"}"))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.reportId").value(report.getReportId()))
                    .andExpect(jsonPath("$.message").value("Report created successfully"));
        }
        System.out.println("Complete testing create report");

        //Testing get a report by reportId
        for (Report report : reports) {
            mockMvc.perform(get("/reports").param("reportId", report.getReportId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.reportId").value(report.getReportId()))
                    .andExpect(jsonPath("$.userId").value(report.getUserId()))
                    .andExpect(jsonPath("$.parameters").value(report.getParameters()));
        }
        System.out.println("Complete testing get a report");

        //Testing get reports of a user
        for (Report report : reports) {
            mockMvc.perform(get("/reports/userId").param("userId", report.getUserId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].reportId").value(report.getReportId()))
                    .andExpect(jsonPath("$[0].userId").value(report.getUserId()))
                    .andExpect(jsonPath("$[0].parameters").value(report.getParameters()));
        }
        System.out.println("Complete testing get reports of a user");

        //Testing update a report
        for (Report report : reports) {
            mockMvc.perform(put("/reports")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"reportId\":\"" + report.getReportId() + "\", \"userId\":\"" + report.getUserId() + "\", \"generatedDate\":\"" + report.getGeneratedDate() + "\",\"parameters\": \""+report.getParameters()+"updated"+"\"}"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("Report updated successfully"));
        }
        System.out.println("Complete testing update a report");

    }
}

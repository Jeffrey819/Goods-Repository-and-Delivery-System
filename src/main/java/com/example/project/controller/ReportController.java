package com.example.project.controller;

import com.example.project.entity.Report;
import com.example.project.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<?> getReportByReportId(@RequestParam("reportId") String reportId) {
        Optional<Report> report = reportService.findByReportId(reportId);
        if(report.isPresent())
            return ResponseEntity.ok(report.get());
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Report with reportId " + reportId + " not found");
    }

    @GetMapping("/userId")
    public ResponseEntity<?> getReportByUserId(@RequestParam("userId") String userId) {
        List<Report> reports = reportService.findByUserId(userId);
        if(reports.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reports of user " + userId + " not found");
        }
        else
        {
            return ResponseEntity.ok(reports);
        }
    }

    @PostMapping
    public ResponseEntity<?> createReport(@RequestBody Report report) {
        Optional<Report> savedReport = reportService.findByReportId(report.getReportId());
        if(savedReport.isPresent())
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Report with reportId " + report.getReportId() + " already exists");
        }
        else
        {
            Map<String,String> info = new HashMap<>();
            reportService.save(report);
            info.put("reportId", report.getReportId());
            info.put("message","Report created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(info);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateReport(@RequestBody Report updatedreport) {
        Optional<Report> report = reportService.findByReportId(updatedreport.getReportId());
        if(report.isPresent())
        {
            reportService.save(updatedreport);
            Map<String,String> info = new HashMap<>();
            info.put("reportId", updatedreport.getReportId());
            info.put("message","Report updated successfully");
            return ResponseEntity.ok(info);
        }
        else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Report with reportId " + updatedreport.getReportId() + " not found");
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteReport(@RequestParam("reportId") String reportId) {
        reportService.deleteByReportId(reportId);
        return ResponseEntity.ok("Report with reportId "+ reportId+" deleted successfully");
    }



}

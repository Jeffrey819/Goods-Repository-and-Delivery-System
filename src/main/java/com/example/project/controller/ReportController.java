package com.example.project.controller;

import com.example.project.entity.Report;
import com.example.project.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reports")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/reportId")
    public ResponseEntity<Report> getReportByReportId(@RequestParam String reportId) {
        Optional<Report> report = reportService.findByReportId(reportId);
        if(report.isPresent())
            return ResponseEntity.ok(report.get());
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/userId")
    public ResponseEntity<List<Report>> getReportByUserId(@RequestParam String userId) {
        Optional<List<Report>> reports = reportService.findByUserId(userId);
        if(reports.isPresent())
            return ResponseEntity.ok(reports.get());
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Report> createReport(@RequestBody Report report) {
        Report savedReport = reportService.save(report);
        return ResponseEntity.ok(savedReport);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteReport(@RequestBody String reportId) {
        reportService.deleteByReportId(reportId);
        return ResponseEntity.ok("Report deleted successfully");
    }



}

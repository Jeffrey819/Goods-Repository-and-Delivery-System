package com.example.project.controller;

import com.example.project.entity.Report;
import com.example.project.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/reports")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/reportId")
    public ResponseEntity<Report> getReportByReportId(@RequestParam("reportId") String reportId) {
        Optional<Report> report = reportService.findByReportId(reportId);
        if(report.isPresent())
            return ResponseEntity.ok(report.get());
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/userId")
    public ResponseEntity<List<Report>> getReportByUserId(@RequestParam("userId") String userId) {
        Optional<List<Report>> reports = reportService.findByUserId(userId);
        if(reports.isPresent())
            return ResponseEntity.ok(reports.get());
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Map<String,String>> createReport(@RequestBody Report report) {
        Map<String,String> info = new HashMap<>();
        reportService.save(report);
        info.put("reportId", report.getReportId());
        info.put("message","Report created successfully");
        return ResponseEntity.ok(info);
    }

    @PutMapping
    public ResponseEntity<Map<String,String>> updateReport(@RequestBody Report updatedreport) {
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
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteReport(@RequestParam("reportId") String reportId) {
        reportService.deleteByReportId(reportId);
        return ResponseEntity.ok("Report deleted successfully");
    }



}

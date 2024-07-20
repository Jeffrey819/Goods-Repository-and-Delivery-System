package com.example.project.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "Reports")
public class Report {
    @Id
    @Column(name = "ReportID",nullable = false,length = 36)
    private String reportId;

    @Column(name = "UserID",nullable = false,length = 36)
    private String userId;

    @Column(name = "Parameters",nullable = false,columnDefinition = "TEXT")
    private String parameters;

    @Column(name = "GeneratedDate",nullable = false,columnDefinition = "DATETIME")
    private LocalDateTime generatedDate;

    //Constructors
    public Report(){}

    public Report(String reportId, String userId, String parameters, LocalDateTime generatedDate) {
        this.reportId = reportId;
        this.userId = userId;
        this.parameters = parameters;
        this.generatedDate = generatedDate;
    }

    //Getters and Setters
    public String getReportId() {
        return reportId;
    }
    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getParameters() {
        return parameters;
    }
    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public LocalDateTime getGeneratedDate() {
        return generatedDate;
    }
    public void setGeneratedDate(LocalDateTime generatedDate) {
        this.generatedDate = generatedDate;
    }

}

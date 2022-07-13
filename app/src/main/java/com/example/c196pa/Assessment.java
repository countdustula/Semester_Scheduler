package com.example.c196pa;

public class Assessment {
    private String assessmentDetails;
    private String startDate;
    private String endDate;
    private String assessmentName;

    public Assessment(String assessmentName, String assessmentDetails, String startDate, String endDate) {
        this.assessmentName = assessmentName;
        this.assessmentDetails = assessmentDetails;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getAssessmentDetails() {
        return assessmentDetails;
    }

    public void setAssessmentDetails(String assessmentDetails) {
        this.assessmentDetails = assessmentDetails;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }
}

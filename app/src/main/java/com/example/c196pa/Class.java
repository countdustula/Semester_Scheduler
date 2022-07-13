package com.example.c196pa;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Class {

    @PrimaryKey
    @NonNull
    private String className;
    private int assessmentAmt;
    private String classStatus;

    @TypeConverters({Converters.class})
    private ArrayList<Assessment> associatedAssessments;

    private String classStart;
    private String classEnd;
    private String instructorName;
    private String instructorNumber;
    private String instructorEmail;
    private String optionalNotes;


    public String getClassStatus() {
        return classStatus;
    }

    public void setClassStatus(String classStatus) {
        this.classStatus = classStatus;
    }

    public void setClassStart(String classStart) {
        this.classStart = classStart;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorNumber() {
        return instructorNumber;
    }

    public void setInstructorNumber(String instructorNumber) {
        this.instructorNumber = instructorNumber;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

//    public Class(String className, String classStatus, ArrayList<Assessment> associatedAssessments, String classStart, String classEnd, String instructorName, String instructorNumber, String instructorEmail) {
//        this.className = className;
//        this.assessmentAmt = associatedAssessments.size();
//        this.classStatus = classStatus;
//        this.associatedAssessments = associatedAssessments;
//        this.classStart = classStart;
//        this.classEnd = classEnd;
//        this.instructorName = instructorName;
//        this.instructorNumber = instructorNumber;
//        this.instructorEmail = instructorEmail;
//        this.optionalNotes = null;
//    }

    public String getOptionalNotes() {
        return optionalNotes;
    }

    public void setOptionalNotes(String optionalNotes) {
        this.optionalNotes = optionalNotes;
    }

    public Class(String className, String classStatus, ArrayList<Assessment> associatedAssessments, String classStart, String classEnd, String instructorName, String instructorNumber, String instructorEmail, String optionalNotes) {
        this.className = className;
        this.assessmentAmt = associatedAssessments.size();
        this.classStatus = classStatus;
        this.associatedAssessments = associatedAssessments;
        this.classStart = classStart;
        this.classEnd = classEnd;
        this.instructorName = instructorName;
        this.instructorNumber = instructorNumber;
        this.instructorEmail = instructorEmail;
        this.optionalNotes = optionalNotes;
    }


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getAssessmentAmt() {
        return assessmentAmt;
    }

    public void setAssessmentAmt(int assessmentAmt) {
        this.assessmentAmt = assessmentAmt;
    }

    public ArrayList<Assessment> getAssociatedAssessments() {
        return associatedAssessments;
    }

    public void setAssociatedAssessments(ArrayList<Assessment> associatedAssessments) {
        this.associatedAssessments = associatedAssessments;
    }

    public String getClassStart() {
        return classStart;
    }

    public void setclassStart(String classStart) {
        this.classStart = classStart;
    }

    public String getClassEnd() {
        return classEnd;
    }

    public void setClassEnd(String classEnd) {
        this.classEnd = classEnd;
    }
}

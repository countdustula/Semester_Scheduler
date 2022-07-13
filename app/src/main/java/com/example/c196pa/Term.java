package com.example.c196pa;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Term {
    @PrimaryKey
    @NonNull
    private String termName;

    private int classAmt;
    private ArrayList<Class> associatedClasses;
    private String startDate;
    private String endDate;

    public Term (String termName, ArrayList<Class> associatedClasses, String startDate, String endDate){
        this.termName = termName;
        this.classAmt = associatedClasses.size();
        this.associatedClasses = associatedClasses;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public int getClassAmt() {
        return classAmt;
    }

    public void setClassAmt(int classAmt) {
        this.classAmt = classAmt;
    }

    public ArrayList<Class> getAssociatedClasses() {
        return associatedClasses;
    }

    public void setAssociatedClasses(ArrayList<Class> associatedClasses) {
        this.associatedClasses = associatedClasses;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndDate() {
        return endDate;
    }
}

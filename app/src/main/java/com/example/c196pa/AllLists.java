package com.example.c196pa;





import java.util.ArrayList;

public class AllLists {
    private static ArrayList<Class> AllClasses = new ArrayList<>();
    private static ArrayList<Term> AllTerms = new ArrayList<>();
    private ArrayList<Assessment> AllAssessments;
    private static ArrayList<Assessment> TemporaryAssessmentList = new ArrayList<>();
    private static ArrayList<Assessment> TemporaryClassesList = new ArrayList<>();

    public static ArrayList<Assessment> getTempList(){
        return TemporaryAssessmentList;
    }

    public static void addToTempList(Assessment assessment){ TemporaryAssessmentList.add(assessment);}

    public static void clearTemp(){
        TemporaryAssessmentList.clear();
    }

    public static ArrayList<Class> getAllClasses() {
        return AllClasses;
    }

    public static ArrayList<Assessment> getTemporaryClassesList() {
        return TemporaryClassesList;
    }

    public static void setTemporaryClassesList(ArrayList<Assessment> temporaryClassesList) {
        TemporaryClassesList = temporaryClassesList;
    }

    public static void setAllClasses(ArrayList<Class> allClasses) {
        AllClasses = allClasses;
    }

    public static ArrayList<Term> getAllTerms() {
        return AllTerms;
    }

    public static void setAllTerms(ArrayList<Term> allTerms) {
        AllTerms = allTerms;
    }

    public ArrayList<Assessment> getAllAssessments() {
        return AllAssessments;
    }

    public void setAllAssessments(ArrayList<Assessment> allAssessments) {
        AllAssessments = allAssessments;
    }

    public void addClass(Class classToBeAdded){
        AllClasses.add(classToBeAdded);
    }

    public void addTerm(Term termToBeAdded){
        AllTerms.add(termToBeAdded);
    }

    public void addAssessment(Assessment assessment){
        AllAssessments.add(assessment);
    }


}

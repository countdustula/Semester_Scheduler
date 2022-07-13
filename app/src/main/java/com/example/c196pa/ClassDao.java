package com.example.c196pa;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.DeleteTable;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ClassDao {

    @Query("SELECT * FROM Class")
    List<Class> getAllClasses();

    @Query("SELECT * FROM Term")
    List<Term> getAllTerms();

    @Insert
    void insertClass(Class... aClass);

    @Insert
    void insertTerm(Term... term);

    @Delete
    void deleteClass(Class aClass);

    @Delete
    void deleteTerm(Term... term);






}

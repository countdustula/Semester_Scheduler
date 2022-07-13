package com.example.c196pa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AssessmentEditActivity extends AppCompatActivity {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    RadioGroup OAandPA;
    RadioButton OA;
    Button cancel;
    Button start;
    Button end;
    EditText description;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private DatePickerDialog.OnDateSetListener dateSetListener2;
    Button submit;
    Button delete;
    String className;
    String classStart;
    String classEnd;
    String classDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_edit);

        cancel = findViewById(R.id.EditAssessmentCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssessmentEditActivity.this, CreateClassActivity.class);
                intent.putExtra("setClassName", className);
                intent.putExtra("setClassStart", classStart);
                intent.putExtra("setClassEnd", classEnd);
                intent.putExtra("setClassDescription", classDescription);
                startActivity(intent);
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            className = extras.getString("className");
            classStart = extras.getString("classStart");
            classEnd = extras.getString("classEnd");
            classDescription = extras.getString("classDescription");
        }


        Integer index = null;

        if(extras != null){
            index = extras.getInt("index");
        }

        OAandPA = findViewById(R.id.EditAssessmentOAandPA);
        OA = findViewById(R.id.EditAssessmentOA);
        start = findViewById(R.id.EditAssessmentStartDate);
        end = findViewById(R.id.EditAssessmentEndDate);
        description = findViewById(R.id.EditAssessmentDescription);
        submit = findViewById(R.id.EditAssessmentSubmit);

        if(AllLists.getTempList().get(index).getAssessmentName().equals("Objective Assessment")){
            OAandPA.check(R.id.EditAssessmentOA);
        }
        else{
            OAandPA.check(R.id.EditAssessmentPA);
        }
        start.setText(AllLists.getTempList().get(index).getStartDate());
        end.setText(AllLists.getTempList().get(index).getEndDate());
        description.setText(AllLists.getTempList().get(index).getAssessmentDetails());


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AssessmentEditActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable((new ColorDrawable(Color.TRANSPARENT)));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date = month + "/" + day + "/" + year;
                start.setText(date);
            }
        };

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AssessmentEditActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateSetListener2,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable((new ColorDrawable(Color.TRANSPARENT)));
                dialog.show();
            }
        });

        dateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date = month + "/" + day + "/" + year;
                end.setText(date);
            }
        };


        Integer finalIndex = index;
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssessmentEditActivity.this, CreateClassActivity.class);
                intent.putExtra("setClassName", className);
                intent.putExtra("setClassStart", classStart);
                intent.putExtra("setClassEnd", classEnd);
                intent.putExtra("setClassDescription", classDescription);
                if (description.getText().toString().isEmpty()){
                    alert("Assessment Description is empty.", "Please enter an assessment description.");
                }
                else if (start.getText().toString().isEmpty()){
                    alert("No start date was entered.", "Please enter a starting date for the assessment.");
                }
                else if(end.getText().toString().isEmpty()){
                    alert("No end date was entered.", "Please enter an end date for the assessment");
                }
                else if(!verifyDates(start.getText().toString(), end.getText().toString())){
                    alert("Start and end date is not valid.", "Starting date must come before or match the end date.");
                }
                else if(OAandPA.getCheckedRadioButtonId() == -1){
                    alert("Assessment type is empty.", "Assessment must be either objective or performance based.  Please choose one.");
                }
                else if ((OAandPA.getCheckedRadioButtonId()) == (findViewById(R.id.EditAssessmentPA).getId())){
                    AllLists.getTempList().get(finalIndex).setAssessmentName("Performance Assessment");
                    AllLists.getTempList().get(finalIndex).setAssessmentDetails(description.getText().toString());
                    AllLists.getTempList().get(finalIndex).setStartDate(start.getText().toString());
                    AllLists.getTempList().get(finalIndex).setEndDate(end.getText().toString());
                    startActivity(intent);

                }
                else if ((OAandPA.getCheckedRadioButtonId()) == (findViewById(R.id.EditAssessmentOA).getId())){
                    AllLists.getTempList().get(finalIndex).setAssessmentName("Objective Assessment");
                    AllLists.getTempList().get(finalIndex).setAssessmentDetails(description.getText().toString());
                    AllLists.getTempList().get(finalIndex).setStartDate(start.getText().toString());
                    AllLists.getTempList().get(finalIndex).setEndDate(end.getText().toString());
                    startActivity(intent);
                }
            };
        });

        delete = findViewById(R.id.EditAssessmentDelete);
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssessmentEditActivity.this, CreateClassActivity.class);
                intent.putExtra("setClassName", className);
                intent.putExtra("setClassStart", classStart);
                intent.putExtra("setClassEnd", classEnd);
                intent.putExtra("setClassDescription", classDescription);
                AllLists.getTempList().remove(AllLists.getTempList().get(finalIndex));
                startActivity(intent);
            }
        });

    }


    private boolean verifyDates(String dateOne, String dateTwo){
        boolean isBefore = false;
        try{
            if(simpleDateFormat.parse(dateOne).before(simpleDateFormat.parse(dateTwo))){
                isBefore = true;
            }
            else if(simpleDateFormat.parse(dateOne).equals(simpleDateFormat.parse(dateTwo))){
                isBefore = true;
            }
            else{
                isBefore = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isBefore;
    }

    private void alert(String alertTitle, String message){
        AlertDialog alertDialog = new AlertDialog.Builder(AssessmentEditActivity.this)
                .setTitle(alertTitle)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        alertDialog.show();
    }
}

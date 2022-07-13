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
import android.widget.RadioGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateAssessmentActivity2 extends AppCompatActivity {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private DatePickerDialog.OnDateSetListener dateSetListener2;
    Button assessmentStart;
    Button assessmentEnd;
    Button submit;
    EditText description;
    RadioGroup assessmentType;
    Button cancel;
    Integer index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_assessment2);


        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            index = extras.getInt("pleaseWork");
        }


        assessmentStart = findViewById(R.id.EditAssessment2StartDate2);
        assessmentEnd = findViewById(R.id.assessmentEndDate2);
        submit = findViewById(R.id.submitAssessment2);
        description = findViewById(R.id.assessmentDescription2);
        assessmentType = findViewById(R.id.AsessmentType2);
        cancel = findViewById(R.id.Cancel2);

        Integer finalIndex = index;
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAssessmentActivity2.this, ClassEditActivity.class);
                intent.putExtra("ClassIndex", finalIndex);
                startActivity(intent);
            }
        });

        Integer finalIndex1 = index;
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (description.getText().toString().isEmpty()){
                    alert("Assessment Description is empty.", "Please enter an assessment description.");
                }
                else if (assessmentStart.getText().toString().isEmpty()){
                    alert("No start date was entered.", "Please enter a starting date for the assessment.");
                }
                else if(assessmentEnd.getText().toString().isEmpty()){
                    alert("No end date was entered.", "Please enter an end date for the assessment");
                }
                else if(!verifyDates(assessmentStart.getText().toString(), assessmentEnd.getText().toString())){
                    alert("Start and end date is not valid.", "Starting date must come before or match the end date.");
                }
                else if(assessmentType.getCheckedRadioButtonId() == -1){
                    alert("Assessment type is empty.", "Assessment must be either objective or performance based.  Please choose one.");
                }
                else if ((assessmentType.getCheckedRadioButtonId()) == (findViewById(R.id.ProjectAssessment2).getId())){
                    AllLists.getTempList().add(new Assessment(
                            "Performance Assessment",
                            description.getText().toString(),
                            assessmentStart.getText().toString(),
                            assessmentEnd.getText().toString()));
                    Intent intent = new Intent(CreateAssessmentActivity2.this, ClassEditActivity.class);
                    intent.putExtra("ClassIndex", finalIndex);
                    startActivity(intent);
                }
                else if ((assessmentType.getCheckedRadioButtonId()) == (findViewById(R.id.ObjectiveAssessment2).getId())){
                    AllLists.getTempList().add(new Assessment(
                            "Objective Assessment",
                            description.getText().toString(),
                            assessmentStart.getText().toString(),
                            assessmentEnd.getText().toString()));
                    Intent intent2 = new Intent(CreateAssessmentActivity2.this, ClassEditActivity.class);
                    intent2.putExtra("ClassIndex", finalIndex);
                    startActivity(intent2);
                }


            };
        });


        assessmentStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(CreateAssessmentActivity2.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable((new ColorDrawable(Color.TRANSPARENT)));
                dialog.show();
            }
        });

        assessmentEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(CreateAssessmentActivity2.this,
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
                assessmentEnd.setText(date);
            }
        };

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date = month + "/" + day + "/" + year;
                assessmentStart.setText(date);
            }
        };
    }

    private void alert(String alertTitle, String message){
        AlertDialog alertDialog = new AlertDialog.Builder(CreateAssessmentActivity2.this)
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
}
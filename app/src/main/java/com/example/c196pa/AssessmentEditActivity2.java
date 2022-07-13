package com.example.c196pa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
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
import java.util.Date;

public class AssessmentEditActivity2 extends AppCompatActivity {

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
        Button notifyMe;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_assessment_edit2);

            Integer index = null;
            Integer index2 = null;

            Bundle extras = getIntent().getExtras();
            if(extras != null){
                index = extras.getInt("index");
                index2 = extras.getInt("itsTheClassIndex");
            }

            cancel = findViewById(R.id.EditAssessment2Cancel);
            Integer finalIndex2 = index2;
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AssessmentEditActivity2.this, ClassEditActivity.class);
                    intent.putExtra("ClassIndex", finalIndex2);
                    startActivity(intent);
                }
            });

            notifyMe = findViewById(R.id.editAssessmentNotifyMe);
            OAandPA = findViewById(R.id.EditAssessment2OAandPA);
            OA = findViewById(R.id.EditAssessment2OA);
            start = findViewById(R.id.EditAssessment2StartDate);
            end = findViewById(R.id.EditAssessment2EndDate);
            description = findViewById(R.id.EditAssessment2Description);
            submit = findViewById(R.id.EditAssessment2Submit);

            if(AllLists.getTempList().get(index).getAssessmentName().equals("Objective Assessment")){
                OAandPA.check(R.id.EditAssessment2OA);
            }
            else{
                OAandPA.check(R.id.EditAssessment2PA);
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

                    DatePickerDialog dialog = new DatePickerDialog(AssessmentEditActivity2.this,
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

                    DatePickerDialog dialog = new DatePickerDialog(AssessmentEditActivity2.this,
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
            Integer finalIndex1 = index2;
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AssessmentEditActivity2.this, ClassEditActivity.class);
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
                    else if ((OAandPA.getCheckedRadioButtonId()) == (findViewById(R.id.EditAssessment2PA).getId())){
                        AllLists.getTempList().get(finalIndex).setAssessmentName("Performance Assessment");
                        AllLists.getTempList().get(finalIndex).setAssessmentDetails(description.getText().toString());
                        AllLists.getTempList().get(finalIndex).setStartDate(start.getText().toString());
                        AllLists.getTempList().get(finalIndex).setEndDate(end.getText().toString());
                        intent.putExtra("ClassIndex", finalIndex1);
                        startActivity(intent);

                    }
                    else if ((OAandPA.getCheckedRadioButtonId()) == (findViewById(R.id.EditAssessment2OA).getId())){
                        AllLists.getTempList().get(finalIndex).setAssessmentName("Objective Assessment");
                        AllLists.getTempList().get(finalIndex).setAssessmentDetails(description.getText().toString());
                        AllLists.getTempList().get(finalIndex).setStartDate(start.getText().toString());
                        AllLists.getTempList().get(finalIndex).setEndDate(end.getText().toString());
                        intent.putExtra("ClassIndex", finalIndex1);
                        startActivity(intent);
                    }
                };
            });

            delete = findViewById(R.id.editAssessment2Delete);
            delete.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AssessmentEditActivity2.this, ClassEditActivity.class);
                    AllLists.getTempList().remove(AllLists.getTempList().get(finalIndex));
                    intent.putExtra("ClassIndex", finalIndex1);
                    startActivity(intent);
                }
            });


            notifyMe.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Date startDateOnScreen = null;
                    Date endDateOnScreen = null;

                    try {
                        startDateOnScreen = simpleDateFormat.parse(start.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    try {
                        endDateOnScreen = simpleDateFormat.parse(end.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Long trigger = startDateOnScreen.getTime();
                    Intent intent = new Intent(AssessmentEditActivity2.this, MyReceiver.class);
                    intent.putExtra("key",  "Your assessment starts today.");
                    PendingIntent sender = PendingIntent.getBroadcast(AssessmentEditActivity2.this ,MainActivity.numAlert++ ,intent, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, trigger , sender);

                    Long trigger2 = endDateOnScreen.getTime();
                    Intent intent2 = new Intent(AssessmentEditActivity2.this, MyReceiver.class);
                    intent2.putExtra("key", "Your assessment ends today.");
                    PendingIntent sender2 = PendingIntent.getBroadcast(AssessmentEditActivity2.this ,MainActivity.numAlert++ ,intent2, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager2.set(AlarmManager.RTC_WAKEUP, trigger2, sender2);




                }});
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
            AlertDialog alertDialog = new AlertDialog.Builder(AssessmentEditActivity2.this)
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
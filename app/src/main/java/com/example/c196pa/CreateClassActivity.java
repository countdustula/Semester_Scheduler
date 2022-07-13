package com.example.c196pa;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;


import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.protocol.HTTP;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CreateClassActivity extends AppCompatActivity {
    private Button addAssessment;
    private RecyclerView recyclerView;
    private ArrayList<Assessment> assessmentList = new ArrayList<>();
    private Button startDate;
    private Button endDate;
    private Button createClass;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private DatePickerDialog.OnDateSetListener dateSetListener2;
    private EditText className;
    private Button cancel;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private recyclerAdapterAssessments.RecyclerViewClickListener listener;
    private EditText instructorName;
    private EditText instructorPhone;
    private EditText instructorEmail;
    private EditText optionalNotes;
    private RadioGroup classStatus;
    private Button sendNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);

        sendNotes = findViewById(R.id.createClassPopulateSMS);
        classStatus = findViewById(R.id.createClassStatusGroup);
        instructorName = findViewById(R.id.createClassInstructorName);
        instructorPhone = findViewById(R.id.createClassInstructorPhoneNumber);
        instructorEmail = findViewById(R.id.createClassInstructorEmail);
        optionalNotes = findViewById(R.id.createClassOptionalNotes);
        startDate = findViewById(R.id.EditClassStart);
        endDate = findViewById(R.id.EditClassEnd);
        className = findViewById(R.id.EditClassName);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            startDate.setText(extras.getString("setClassStart"));
            endDate.setText(extras.getString("setClassEnd"));
            className.setText(extras.getString("setClassName"));
            instructorName.setText(extras.getString("setInstructorName"));
            instructorPhone.setText(extras.getString("setInstructorPhone"));
            instructorEmail.setText(extras.getString("setInstructorEmail"));
            optionalNotes.setText(extras.getString("setOptionalNotes"));
        }


        sendNotes.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent sendIntent = new Intent();
               sendIntent.setAction(Intent.ACTION_SEND);
               sendIntent.putExtra(Intent.EXTRA_TEXT, optionalNotes.getText().toString());
               sendIntent.putExtra(Intent.EXTRA_TITLE, "Here are the notes for " + className.getText().toString());
               sendIntent.setType("text/plain");
               Intent shareIntent = Intent.createChooser(sendIntent, null);
               startActivity(shareIntent);
        }});

        cancel = findViewById(R.id.EditClassCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateClassActivity.this, ClassesActivity.class);
                AllLists.clearTemp();
                startActivity(intent);
            }
        });


        createClass = findViewById(R.id.EditClassSaveChanges);
        createClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateClassActivity.this, ClassesActivity.class);
                if (className.getText().toString().isEmpty()) {
                    alert("No class name.", "Please enter a name for the class.");
                } else if (startDate.getText().toString().isEmpty()) {
                    alert("No start date was entered.", "Please enter a starting date for the class.");
                } else if (endDate.getText().toString().isEmpty()) {
                    alert("No end date was entered.", "Please enter an end date for the class");
                } else if (!verifyDates(startDate.getText().toString(), endDate.getText().toString())) {
                    alert("Start and end date is not valid.", "Starting date must come before or match the end date.");
                } else if (AllLists.getTempList().isEmpty()) {
                    alert("No assessments added.", "Please add at least one assessment for the class.");
                } else if (instructorName.getText().toString().isEmpty()) {
                    alert("No instructor name", "Please add an instructor name");
                } else if (instructorEmail.getText().toString().isEmpty()) {
                    alert("No instructor email", "Please add an instructor email.");
                } else if (instructorPhone.getText().toString().isEmpty()) {
                    alert("No instructor phone number", "Please add an instructor phone number.");
                } else if (classStatus.getCheckedRadioButtonId() == -1) {
                    alert("No class status chosen.", "Please pick a class status.");
                } else {
                    ArrayList<Assessment> Temp = new ArrayList<>();
                    String status = null;
                    if (classStatus.getCheckedRadioButtonId() == findViewById(R.id.createClassInProgress).getId()) {
                        status = "In Progress";
                    } else if (classStatus.getCheckedRadioButtonId() == findViewById(R.id.createClassCompleted).getId()) {
                        status = "Completed";
                    } else if (classStatus.getCheckedRadioButtonId() == findViewById(R.id.createClassDropped).getId()) {
                        status = "Dropped";
                    } else if (classStatus.getCheckedRadioButtonId() == findViewById(R.id.createClassPlanToTake).getId()) {
                        status = "Plan to Take";
                    }

                    for (int i = 0; i < AllLists.getTempList().size(); i++) {
                        Temp.add(AllLists.getTempList().get(i));
                    }
                    AllLists.getAllClasses().add(new Class(
                            className.getText().toString(),
                            status,
                            Temp,
                            startDate.getText().toString(),
                            endDate.getText().toString(),
                            instructorName.getText().toString(),
                            instructorPhone.getText().toString(),
                            instructorEmail.getText().toString(),
                            optionalNotes.getText().toString()
                    ));


                    AppDatabase db = AppDatabase.getDbInstance(CreateClassActivity.this);
                    db.classDao().insertClass(new Class(
                            className.getText().toString(),
                            status,
                            Temp,
                            startDate.getText().toString(),
                            endDate.getText().toString(),
                            instructorName.getText().toString(),
                            instructorPhone.getText().toString(),
                            instructorEmail.getText().toString(),
                            optionalNotes.getText().toString()
                    ));

//                    Date startDateOnScreen = null;
//                    try{
//                        startDateOnScreen = simpleDateFormat.parse(startDate.getText().toString());
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    Long trigger = startDateOnScreen.getTime();
//                    Intent intent2 = new Intent(CreateClassActivity.this, MyReceiver.class);
//                    intent2.putExtra("key", className.getText().toString() + "starts today.");
//                    PendingIntent sender = PendingIntent.getBroadcast(CreateClassActivity.this ,MainActivity.numAlert++ ,intent2, PendingIntent.FLAG_MUTABLE);
//                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//                    alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);


                    startActivity(intent);
                    AllLists.clearTemp();
                }
            }
        });

        addAssessment = findViewById(R.id.EditClassAddAssessment);

        addAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AllLists.getTempList().size() < 5) {
                    openAddAsessments();
                } else {
                    alert("Too many classes have been added.", "Only up to 5 assessments can be added.");
                }
            }
        });


        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(CreateClassActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable((new ColorDrawable(Color.TRANSPARENT)));
                dialog.show();
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(CreateClassActivity.this,
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
                endDate.setText(date);
            }
        }

        ;

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date = month + "/" + day + "/" + year;
                startDate.setText(date);
            }
        };

        recyclerView = findViewById(R.id.EditClassAssessmentRecyclerView);
        setAdapter();

    }




    private void setAdapter() {
        setOnClickListener();
        recyclerAdapterAssessments adapter = new recyclerAdapterAssessments(AllLists.getTempList(), listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener() {
        listener = new recyclerAdapterAssessments.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), AssessmentEditActivity.class);
                if (!className.getText().toString().isEmpty()) {
                    intent.putExtra("className", className.getText().toString());
                }
                if (!startDate.getText().toString().isEmpty()) {
                    intent.putExtra("classStart", startDate.getText().toString());
                }
                if (!endDate.getText().toString().isEmpty()) {
                    intent.putExtra("classEnd", endDate.getText().toString());
                }
                intent.putExtra("index", position);
                startActivity(intent);
            }
        };
    }


    public void openAddAsessments() {
        Intent intent = new Intent(this, CreateAssessmentActivity.class);
        if (!className.getText().toString().isEmpty()) {
            intent.putExtra("className", className.getText().toString());
        }
        if (!startDate.getText().toString().isEmpty()) {
            intent.putExtra("classStart", startDate.getText().toString());
        }
        if (!endDate.getText().toString().isEmpty()) {
            intent.putExtra("classEnd", endDate.getText().toString());
        }
        if (!instructorName.getText().toString().isEmpty()) {
            intent.putExtra("instructorName", instructorName.getText().toString());
        }
        if (!instructorPhone.getText().toString().isEmpty()) {
            intent.putExtra("instructorPhone", instructorPhone.getText().toString());
        }
        if (!instructorEmail.getText().toString().isEmpty()) {
            intent.putExtra("instructorEmail", instructorEmail.getText().toString());
        }
        if (!optionalNotes.getText().toString().isEmpty()) {
            intent.putExtra("optionalNotes", optionalNotes.getText().toString());
        }

        startActivity(intent);
    }


    private void alert(String alertTitle, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(CreateClassActivity.this)
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

    private boolean verifyDates(String dateOne, String dateTwo) {
        boolean isBefore = false;
        try {
            if (simpleDateFormat.parse(dateOne).before(simpleDateFormat.parse(dateTwo))) {
                isBefore = true;
            } else if (simpleDateFormat.parse(dateOne).equals(simpleDateFormat.parse(dateTwo))) {
                isBefore = true;
            } else {
                isBefore = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isBefore;
    }



    }
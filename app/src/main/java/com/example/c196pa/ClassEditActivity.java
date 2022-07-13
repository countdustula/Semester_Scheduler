package com.example.c196pa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.RadioGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ClassEditActivity extends AppCompatActivity {
    private Button cancel;
    private EditText name;
    private Button start;
    private Button end;
    private Button addAssessment;
    private Button saveChanges;
    private RecyclerView recyclerView;
    private Integer index;
    private recyclerAdapterAssessments.RecyclerViewClickListener listener;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private Button delete;
    private EditText instructorName;
    private EditText instructorPhone;
    private EditText instructorEmail;
    private EditText optionalNotes;
    private RadioGroup classStatus;
    private Button sendNotes;
    private Button notifyMe;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private DatePickerDialog.OnDateSetListener dateSetListener2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_edit);

        instructorName = findViewById(R.id.editClassInstructorName);
        instructorEmail = findViewById(R.id.editClassInstructorEmail);
        instructorPhone = findViewById(R.id.editClassInstructorPhoneNumber);
        optionalNotes = findViewById(R.id.editClassOptionalNotes);
        classStatus = findViewById(R.id.editClassStatusGroup);
        delete = findViewById(R.id.EditClassDelete);
        cancel = findViewById(R.id.EditClassCancel);
        start = findViewById(R.id.EditClassStart);
        end = findViewById(R.id.EditClassEnd);
        addAssessment = findViewById(R.id.EditClassAddAssessment);
        saveChanges = findViewById(R.id.EditClassSaveChanges);
        name = findViewById(R.id.EditClassName);
        recyclerView = findViewById(R.id.EditClassAssessmentRecyclerView);
        sendNotes = findViewById(R.id.editClassShareNotes);
        notifyMe = findViewById(R.id.editClassNotifyMe);

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
               Intent intent = new Intent(ClassEditActivity.this, MyReceiver.class);
               intent.putExtra("key", name.getText().toString() + " starts today.");
               PendingIntent sender = PendingIntent.getBroadcast(ClassEditActivity.this ,MainActivity.numAlert++ ,intent, PendingIntent.FLAG_IMMUTABLE);
               AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
               alarmManager.set(AlarmManager.RTC_WAKEUP, trigger , sender);

               Long trigger2 = endDateOnScreen.getTime();
               Intent intent2 = new Intent(ClassEditActivity.this, MyReceiver.class);
               intent2.putExtra("key", name.getText().toString() + " ends today.");
               PendingIntent sender2 = PendingIntent.getBroadcast(ClassEditActivity.this ,MainActivity.numAlert++ ,intent2, PendingIntent.FLAG_IMMUTABLE);
               AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
               alarmManager2.set(AlarmManager.RTC_WAKEUP, trigger2, sender2);




           }});

        sendNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, optionalNotes.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Here are the notes for " + name.getText().toString());
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }});

        Bundle extras = getIntent().getExtras();


            index = extras.getInt("ClassIndex");



        name.setText(AllLists.getAllClasses().get(index).getClassName());
        start.setText(AllLists.getAllClasses().get(index).getClassStart());
        end.setText(AllLists.getAllClasses().get(index).getClassEnd());
        instructorName.setText(AllLists.getAllClasses().get(index).getInstructorName());
        instructorPhone.setText(AllLists.getAllClasses().get(index).getInstructorNumber());
        instructorEmail.setText(AllLists.getAllClasses().get(index).getInstructorEmail());
        optionalNotes.setText(AllLists.getAllClasses().get(index).getOptionalNotes());
        if(AllLists.getAllClasses().get(index).getClassStatus().equals("In Progress")){
            classStatus.check(R.id.editClassInProgress);
        }
        else if (AllLists.getAllClasses().get(index).getClassStatus().equals("Completed")){
            classStatus.check(R.id.editClassCompleted);
        }
        else if (AllLists.getAllClasses().get(index).getClassStatus().equals("Dropped")){
            classStatus.check(R.id.editClassDropped);
        }
        else if (AllLists.getAllClasses().get(index).getClassStatus().equals("Plan to Take")){
            classStatus.check(R.id.editClassPlanToTake);
        }

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ClassEditActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable((new ColorDrawable(Color.TRANSPARENT)));
                dialog.show();
            }
        });


        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ClassEditActivity.this,
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
        }

        ;

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date = month + "/" + day + "/" + year;
                start.setText(date);
            }
        };

       addAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AllLists.getTempList().size() < 5) {
                    Intent intent = new Intent(ClassEditActivity.this, CreateAssessmentActivity2.class);
                    intent.putExtra("pleaseWork", index);
                    startActivity(intent);
                }
                else{
                    alert("Too many assessments", "Only five assessments can be assigned to one class.");
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppDatabase db = AppDatabase.getDbInstance(ClassEditActivity.this);
                db.classDao().deleteClass(AllLists.getAllClasses().get(index));

                AllLists.getAllClasses().remove(AllLists.getAllClasses().get(index));
                AllLists.getTempList().clear();

                Intent intent = new Intent(ClassEditActivity.this, ClassesActivity.class);
                startActivity(intent);
            }
        });

       saveChanges.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(ClassEditActivity.this, ClassesActivity.class);
               if (name.getText().toString().isEmpty()) {
                   alert("No class name.", "Please enter a name for the class.");
               } else if (start.getText().toString().isEmpty()) {
                   alert("No start date was entered.", "Please enter a starting date for the class.");
               } else if (end.getText().toString().isEmpty()) {
                   alert("No end date was entered.", "Please enter an end date for the class");
               } else if (!verifyDates(start.getText().toString(), end.getText().toString())) {
                   alert("Start and end date is not valid.", "Starting date must come before or match the end date.");
               } else if (AllLists.getTempList().isEmpty()) {
                   alert("No assessments added.", "Please add at least one assessment for the class.");
               } else if (instructorName.getText().toString().isEmpty()) {
                   alert("No instructor name", "Please enter an instructor name");
               } else if (instructorPhone.getText().toString().isEmpty()) {
                   alert("No instructor phone number", "Please enter a phone number.");
               } else if(instructorEmail.getText().toString().isEmpty()){
                   alert("No instructor email.", "Please enter an email");
               }else{
                   ArrayList<Assessment> Temp = new ArrayList<>();
                   String status = null;

                   for(int i = 0; i < AllLists.getTempList().size(); i++){
                       Temp.add(AllLists.getTempList().get(i));
                   }


                   if(classStatus.getCheckedRadioButtonId() == findViewById(R.id.editClassInProgress).getId()){
                       status = "In Progress";
                   }
                   else if(classStatus.getCheckedRadioButtonId() == findViewById(R.id.editClassCompleted).getId()){
                       status = "Completed";
                   }
                   else if(classStatus.getCheckedRadioButtonId() == findViewById(R.id.editClassDropped).getId()){
                       status = "Dropped";
                   }
                   else if(classStatus.getCheckedRadioButtonId() == findViewById(R.id.editClassPlanToTake).getId()){
                       status = "Plan to Take";
                   }


                   AppDatabase db = AppDatabase.getDbInstance(ClassEditActivity.this);
                   db.classDao().deleteClass(AllLists.getAllClasses().get(index));

                           AllLists.getAllClasses().get(index).setClassName(name.getText().toString());
                           AllLists.getAllClasses().get(index).setClassStatus(status);
                           AllLists.getAllClasses().get(index).setAssociatedAssessments(Temp);
                           AllLists.getAllClasses().get(index).setclassStart(start.getText().toString());
                           AllLists.getAllClasses().get(index).setClassEnd(end.getText().toString());
                           AllLists.getAllClasses().get(index).setInstructorName(instructorName.getText().toString());
                           AllLists.getAllClasses().get(index).setInstructorNumber(instructorPhone.getText().toString());
                           AllLists.getAllClasses().get(index).setInstructorEmail(instructorEmail.getText().toString());
                           AllLists.getAllClasses().get(index).setOptionalNotes(optionalNotes.getText().toString());

                   db.classDao().insertClass(AllLists.getAllClasses().get(index));

                   AllLists.clearTemp();
                   startActivity(intent);
               }
           }
       });



//        cancel = findViewById(R.id.EditClassCancel);
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ClassEditActivity.this, ClassesActivity.class);
//                AllLists.getTempList().clear();
//                startActivity(intent);
//            }
//        });

        setAdapter();
    }

    private void setAdapter(){
        setOnClickListener();
        recyclerAdapterAssessments adapter=new recyclerAdapterAssessments(AllLists.getTempList(), listener);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener() {
        listener = new recyclerAdapterAssessments.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(ClassEditActivity.this, AssessmentEditActivity2.class);


                intent.putExtra("index", position);
                intent.putExtra("itsTheClassIndex", index);
                startActivity(intent);
            }
        };
    }

    private void alert(String alertTitle, String message){
        AlertDialog alertDialog = new AlertDialog.Builder(ClassEditActivity.this)
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
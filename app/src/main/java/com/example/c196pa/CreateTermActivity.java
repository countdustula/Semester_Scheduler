package com.example.c196pa;

import static java.lang.String.valueOf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class CreateTermActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Button startDateButton;
    private Button endDateButton;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private DatePickerDialog.OnDateSetListener dateSetListener2;
    private Button back;
    TextView selectClass;
    Button submit;
    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    TextView termName;
    private recyclerAdapterClasses.RecyclerViewClickListener listener;
    private recyclerAdapterClasses.RecyclerViewClickListener listener2;
    ArrayList<Class> toBeAdded = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_term);

        termName = findViewById(R.id.createTermName);
        recyclerView = findViewById(R.id.createTermRecyclerView);
        recyclerView2 = findViewById(R.id.createTermRecyclerView2);




        endDateButton = findViewById(R.id.EndDate);
        startDateButton = findViewById(R.id.StartDate);

        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(CreateTermActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable((new ColorDrawable(Color.TRANSPARENT)));
                dialog.show();
            }
        });

        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(CreateTermActivity.this,
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
                endDateButton.setText(date);
            }
        };

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date = month + "/" + day + "/" + year;
                startDateButton.setText(date);
            }
        };

        back = findViewById(R.id.backButtonFromCreateTerm2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateTermActivity.this, TermsActivity.class);
                for(int i = 0; i < toBeAdded.size(); i++){
                    AllLists.getAllClasses().add(toBeAdded.get(i));
                    toBeAdded.remove(i);
                }
                startActivity(intent);
            }
        });

        submit = findViewById(R.id.SubmitTerm);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateTermActivity.this, TermsActivity.class);
                if (startDateButton.getText().toString().isEmpty()) {
                    alert("No start date", "Please enter a start date.");
                } else if (endDateButton.getText().toString().isEmpty()) {
                    alert("No end date", "Please enter an end date");
                } else if (termName.getText().toString().isEmpty()) {
                    alert("No term name", "Please enter a term name");
                } else if (toBeAdded.isEmpty()){
                    alert("No classes in term", "Please add at least one class to the term");
                }else {

                    ArrayList<Class> Temp = new ArrayList<>();

                    for (int i = 0; i < toBeAdded.size(); i++) {
                        Temp.add(toBeAdded.get(i));
                    }
                    AllLists.getAllTerms().add(new Term(
                            termName.getText().toString(),
                            Temp,
                            startDateButton.getText().toString(),
                            endDateButton.getText().toString()
                    ));

                    AppDatabase db = AppDatabase.getDbInstance(CreateTermActivity.this);
                    db.classDao().insertTerm(new Term(
                            termName.getText().toString(),
                            Temp,
                            startDateButton.getText().toString(),
                            endDateButton.getText().toString()
                    ));

                    for(int i = 0; i< Temp.size(); i++) {
                        db.classDao().deleteClass(Temp.get(i));
                    }

                    toBeAdded.clear();
                    startActivity(intent);

                }
            }

            ;
        });


        setAdapter();
        setAdapter2();
    }

    private void setAdapter() {
        setOnClickListener();
        recyclerAdapterClasses adapter = new recyclerAdapterClasses(AllLists.getAllClasses(), listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setAdapter2() {
        setOnClickListener2();
        recyclerAdapterClasses adapter2 = new recyclerAdapterClasses(toBeAdded, listener2);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView2.setLayoutManager(layoutManager);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setAdapter(adapter2);
    }

    private void setOnClickListener2() {
        listener2 = new recyclerAdapterClasses.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                AllLists.getAllClasses().add(toBeAdded.get(position));
                recyclerView.getAdapter().notifyDataSetChanged();

                toBeAdded.remove(position);
                recyclerView2.getAdapter().notifyDataSetChanged();


            }
        };
    }

    private void setOnClickListener() {
        listener = new recyclerAdapterClasses.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                toBeAdded.add(AllLists.getAllClasses().get(position));
                recyclerView2.getAdapter().notifyDataSetChanged();

                AllLists.getAllClasses().remove(AllLists.getAllClasses().get(position));
                recyclerView.getAdapter().notifyDataSetChanged();




            }
        };
    }

    private void alert(String alertTitle, String message){
        AlertDialog alertDialog = new AlertDialog.Builder(CreateTermActivity.this)
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
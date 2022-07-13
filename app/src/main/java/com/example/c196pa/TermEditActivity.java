package com.example.c196pa;

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
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;


public class TermEditActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Button startDateButton;
    private Button endDateButton;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private DatePickerDialog.OnDateSetListener dateSetListener2;
    private Button back;
    TextView selectClass;
    Button submit;
    EditText termName;
    int index;
    Button delete;
    ArrayList<Class> editedClassList = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    private recyclerAdapterClasses.RecyclerViewClickListener listener;
    private recyclerAdapterClasses.RecyclerViewClickListener listener2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_edit);

        Bundle extras = getIntent().getExtras();

        index = extras.getInt("TermIndex");

        editedClassList = AllLists.getAllTerms().get(index).getAssociatedClasses();

        termName = findViewById(R.id.EditTermName);
        termName.setText(AllLists.getAllTerms().get(index).getTermName());

        recyclerView = findViewById(R.id.editTermRecyclerView);
        recyclerView2 = findViewById(R.id.editTermRecyclerView2);




        delete = findViewById(R.id.deleteTerm);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editedClassList.isEmpty()){
                    Intent intent = new Intent(TermEditActivity.this, TermsActivity.class);

                    AppDatabase db = AppDatabase.getDbInstance(TermEditActivity.this);
                    db.classDao().deleteTerm(AllLists.getAllTerms().get(index));
                    AllLists.getAllTerms().remove(index);

                    for(int i = 0; i < db.classDao().getAllClasses().size(); i++){
                        db.classDao().deleteClass(db.classDao().getAllClasses().get(i));
                    }

                    for(int i = 0; i < AllLists.getAllClasses().size(); i++){
                        db.classDao().insertClass(AllLists.getAllClasses().get(i));
                    }

                    startActivity(intent);
                }
                else {
                    alert("Cannot delete", "Cannot delete a term with classes.  Please remove classes.");
                }
            }
        });





        endDateButton = findViewById(R.id.EndDate2);

        endDateButton.setText(AllLists.getAllTerms().get(index).getEndDate());

        startDateButton = findViewById(R.id.StartDate2);

        startDateButton.setText(AllLists.getAllTerms().get(index).getStartDate());

        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(TermEditActivity.this,
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

                DatePickerDialog dialog = new DatePickerDialog(TermEditActivity.this,
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

//        back = findViewById(R.id.backButtonFromCreateTerm2);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(TermEditActivity.this, TermsActivity.class);
//                for(int i = 0; i < editedClassList.size(); i++){
//                    AllLists.getAllClasses().add(editedClassList.get(i));
//                    editedClassList.remove(i);
//                }
//                startActivity(intent);
//            }
//        });


        submit = findViewById(R.id.SubmitTerm2);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TermEditActivity.this, TermsActivity.class);
                if (startDateButton.getText().toString().isEmpty()) {
                    alert("No start date", "Please enter a start date.");
                } else if (endDateButton.getText().toString().isEmpty()) {
                    alert("No end date", "Please enter an end date");
                } else if (termName.getText().toString().isEmpty()) {
                    alert("No term name", "Please enter a term name");
                }else if (editedClassList.isEmpty()){
                    alert("No classes in term", "Please add at least one class to the term.");
                 }else{
                    ArrayList<Class> Temp = new ArrayList<>();

                    for(int i = 0; i < editedClassList.size(); i++){
                        Temp.add(editedClassList.get(i));
                    }

                    AppDatabase db = AppDatabase.getDbInstance(TermEditActivity.this);

                    db.classDao().deleteTerm(AllLists.getAllTerms().get(index));


                            AllLists.getAllTerms().get(index).setTermName(termName.getText().toString());
                            AllLists.getAllTerms().get(index).setAssociatedClasses(Temp);
                            AllLists.getAllTerms().get(index).setStartDate(startDateButton.getText().toString());
                            AllLists.getAllTerms().get(index).setEndDate(endDateButton.getText().toString());


                    db.classDao().insertTerm(AllLists.getAllTerms().get(index));


                    for(int i = 0; i< Temp.size(); i++) {
                        db.classDao().deleteClass(Temp.get(i));
                    }



                    for(int i = 0; i < db.classDao().getAllClasses().size(); i++){
                        db.classDao().deleteClass(db.classDao().getAllClasses().get(i));
                    }

                    for(int i = 0; i < AllLists.getAllClasses().size(); i++){
                        db.classDao().insertClass(AllLists.getAllClasses().get(i));
                    }

                    startActivity(intent);
                }
            };
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
        recyclerAdapterClasses adapter2 = new recyclerAdapterClasses(editedClassList, listener2);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView2.setLayoutManager(layoutManager);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setAdapter(adapter2);
    }

    private void setOnClickListener2() {
        listener2 = new recyclerAdapterClasses.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                AllLists.getAllClasses().add(editedClassList.get(position));
                recyclerView.getAdapter().notifyDataSetChanged();

                editedClassList.remove(position);
                recyclerView2.getAdapter().notifyDataSetChanged();


            }
        };
    }

    private void setOnClickListener() {
        listener = new recyclerAdapterClasses.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                editedClassList.add(AllLists.getAllClasses().get(position));
                recyclerView2.getAdapter().notifyDataSetChanged();

                AllLists.getAllClasses().remove(AllLists.getAllClasses().get(position));
                recyclerView.getAdapter().notifyDataSetChanged();




            }
        };
    }

    private void alert(String alertTitle, String message){
        AlertDialog alertDialog = new AlertDialog.Builder(TermEditActivity.this)
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
package com.example.c196pa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ClassesActivity extends AppCompatActivity {

    TextView noClassesFound;
    FloatingActionButton addClass;
    RecyclerView classRecyclerView;
    Button back;
    private recyclerAdapterClasses.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        addClass = (FloatingActionButton) findViewById(R.id.addClass);
        noClassesFound = findViewById(R.id.noClassesFound);

        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddClass();
            }
        });



        if (AllLists.getAllClasses().isEmpty()) {
            noClassesFound.setText("No classes found.");
        }

        classRecyclerView = findViewById(R.id.classRecyclerView);
        setAdapter();



    }

    public void openAddClass() {
        Intent intent = new Intent(this, CreateClassActivity.class);
        startActivity(intent);
    }

    public void openMainActivity() {
        Intent intent2 = new Intent(this, MainActivity.class);
        startActivity(intent2);
    }


    private void setAdapter() {
        setOnClickListener();
        recyclerAdapterClasses adapter = new recyclerAdapterClasses(AllLists.getAllClasses(), listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        classRecyclerView.setLayoutManager(layoutManager);
        classRecyclerView.setItemAnimator(new DefaultItemAnimator());
        classRecyclerView.setAdapter(adapter);
    }

    private void setOnClickListener() {
        listener = new recyclerAdapterClasses.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(ClassesActivity.this, ClassEditActivity.class);
                intent.putExtra("ClassIndex", position);
                for(int i = 0; i < AllLists.getAllClasses().get(position).getAssociatedAssessments().size(); i++){
                    AllLists.getTempList().add(AllLists.getAllClasses().get(position).getAssociatedAssessments().get(i));
                }
                startActivity(intent);
            }
        };
    }

}
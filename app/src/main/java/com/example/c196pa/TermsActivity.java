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

public class TermsActivity extends AppCompatActivity {

    FloatingActionButton addTerm;
    Button back;
    TextView noTerms;
    RecyclerView recyclerView;
    private recyclerAdapterClasses.RecyclerViewClickListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        addTerm = (FloatingActionButton) findViewById(R.id.AddTerm);
        noTerms = findViewById(R.id.noTermsFound);

        addTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddTerms();
            }
        });



        if (AllLists.getAllTerms().isEmpty()){
            noTerms.setText("No Terms Found.");
        }
        else {
            recyclerView = findViewById(R.id.recyclerViewTerms);
            setAdapter();
        }
    }

    public void openAddTerms() {
        Intent intent = new Intent(this, CreateTermActivity.class);
        startActivity(intent);
    }

    public void openMainMenu(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void setAdapter(){
        setOnClickListener();
        recyclerAdapterTerms adapter = new recyclerAdapterTerms(AllLists.getAllTerms(), listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


    private void setOnClickListener() {
        listener = new recyclerAdapterClasses.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(TermsActivity.this, TermEditActivity.class);
                intent.putExtra("TermIndex", position);
                startActivity(intent);
            }
        };
    }

}





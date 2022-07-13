package com.example.c196pa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private Button termsButton;
    private Button classesButton;
    public static int numAlert = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        termsButton = (Button)findViewById(R.id.terms);
        termsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTerms();
            }
        });

        classesButton = (Button)findViewById(R.id.classes);
        classesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) { openClasses(); }
        });

        createNotificationChannel();
        loadFromDB();
    }
    public void openTerms(){
        Intent intent = new Intent(this, TermsActivity.class);
        startActivity(intent);
    }

    public void openClasses(){
        Intent intent = new Intent(this, ClassesActivity.class);
        startActivity(intent);
    }


    public void loadFromDB(){
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        AllLists.setAllClasses((ArrayList<Class>) db.classDao().getAllClasses());
        AllLists.setAllTerms((ArrayList<Term>) db.classDao().getAllTerms());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            String CHANNEL_ID = "channelID";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}

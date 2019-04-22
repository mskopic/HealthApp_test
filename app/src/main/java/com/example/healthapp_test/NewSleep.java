package com.example.healthapp_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

public class NewSleep extends AppCompatActivity {


    public int hours;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sleep);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Sleep Schedule");
        //keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    }

    public void set_schedule(View v){
        EditText editText = (EditText) findViewById(R.id.hours_sleep);
        hours = Integer.parseInt(editText.getText().toString());

        Intent schedule = new Intent(this, Set_Schedule.class);
        schedule.putExtra("username",getIntent().getStringExtra("username"));
        schedule.putExtra("Previous","Sleep");
        schedule.putExtra("hours_sleep",hours);
        startActivity(schedule);

    }

}

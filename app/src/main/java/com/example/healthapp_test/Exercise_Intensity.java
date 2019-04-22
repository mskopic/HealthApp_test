package com.example.healthapp_test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Exercise_Intensity extends AppCompatActivity {

    public int ex_intensity;
    public String ex_type;
    public String ex_name;
    public boolean ac;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_intensity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Exercise Intensity");


        //get exercise type that was previously selected (Weight Training or Cardio)
        Intent intent = getIntent();
        ex_type = intent.getStringExtra("ex_type");
        ex_name = intent.getStringExtra("ex_name");

        ListView list = (ListView) findViewById(R.id.listview);

        String[] listItem;
        if(ex_type.equals("Weight Training")) {
             listItem = getResources().getStringArray(R.array.weight_intensity);
        }
        else{
            listItem = getResources().getStringArray(R.array.cardio_intensity);
        }
        //list stuff
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.my_list, listItem);
        list.setAdapter(adapter);
        list.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                ex_intensity= position;

            }
        });

        // if already created
        final String user = intent.getStringExtra("username");
        Gson userGson = new Gson();
        SharedPreferences sp = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        UserDetails currUser = userGson.fromJson(sp.getString(user,""), UserDetails.class);
        final ArrayList<Exercise_Goal> savedExGoals = currUser.ex_goals;
        ac = intent.getBooleanExtra("already_created",false);
        if(ac){
            int ex_num = intent.getIntExtra("ex_num",0);
            Exercise_Goal ex = savedExGoals.get(ex_num);
            list.setItemChecked(ex.getEx_intensity(),true);
            ex_intensity = ex.getEx_intensity();

        }

    }
    public void set_schedule(View v){
        Intent schedule = new Intent(this, Set_Schedule.class);
        schedule.putExtra("Previous","Exercise");
        schedule.putExtra("ex_type",ex_type);
        schedule.putExtra("ex_intensity",ex_intensity);
        schedule.putExtra("ex_name",ex_name);
        schedule.putExtra("already_created",ac);
        schedule.putExtra("username",getIntent().getStringExtra("username"));

        //if already created, add name of exercise we stored in sharedPreferences
        if(ac) {
            Log.i("saved_ex - intensity",getIntent().getStringExtra("saved_ex"));
            schedule.putExtra("saved_ex", getIntent().getStringExtra("saved_ex"));
            schedule.putExtra("ex_num",getIntent().getStringExtra("ex_num"));

        }



        startActivity(schedule);

    }


}

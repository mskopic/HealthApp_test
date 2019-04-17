package com.example.healthapp_test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Sleep extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        SharedPreferences sp = getSharedPreferences("Goals", Context.MODE_PRIVATE);
        int num_sleep_goals = sp.getInt("num_sleep_goals",0);


        if(num_sleep_goals > 0) {
            ListView list = (ListView) findViewById(R.id.sleep_goals);
            final ArrayList<Sleep_Goal> sleep_goals = new ArrayList<Sleep_Goal>();
            final ArrayList<String> sleep_goals_names = new ArrayList<String>();
            Gson gson = new Gson();

            for(int i = 0;i<num_sleep_goals;i++){
                String name = "sleep_goal"+i;
                String json = sp.getString(name, "");
                Sleep_Goal sleep = gson.fromJson(json, Sleep_Goal.class);
                sleep_goals.add(sleep);
                sleep_goals_names.add(name);
            }

            // list stuff
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, sleep_goals_names);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // TODO Auto-generated method stub
                    Sleep_Goal chosen_goal = sleep_goals.get(position);
                    Intent new_sleep = new Intent(view.getContext(),NewSleep.class);
                    new_sleep.putExtra("already_created",true);
                    new_sleep.putExtra("sleep_num","sleep_goal"+position);
                    startActivity(new_sleep);

                }
            });
        }
    }

    public void new_sleep(View view){
        Intent new_sleep = new Intent(this,NewSleep.class);
        startActivity(new_sleep);
    }

}

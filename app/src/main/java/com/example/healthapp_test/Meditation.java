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

public class Meditation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*SharedPreferences sp = getSharedPreferences("Goals", Context.MODE_PRIVATE);
        int num_med_goals = sp.getInt("num_med_goals",0);


        if(num_med_goals > 0) {
            ListView list = (ListView) findViewById(R.id.med_goals);
            final ArrayList<Meditation_Goal> med_goals = new ArrayList<Meditation_Goal>();
            final ArrayList<String> med_goals_names = new ArrayList<String>();
            Gson gson = new Gson();

            for(int i = 0;i<num_med_goals;i++){
                String name = "med_goal"+i;
                String json = sp.getString(name, "");
                Meditation_Goal med = gson.fromJson(json, Meditation_Goal.class);
                med_goals.add(med);
                med_goals_names.add(med.getMed_name());
            }

            // list stuff
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, med_goals_names);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // TODO Auto-generated method stub
                    Meditation_Goal chosen_goal = med_goals.get(position);
                    Intent new_med = new Intent(view.getContext(),NewMed.class);
                    new_med.putExtra("already_created",true);
                    new_med.putExtra("med_num","med_goal"+position);
                    new_med.putExtra("med_plan_name", chosen_goal.getMed_name());
                    startActivity(new_med);

                }
            });
        }*/

        final String user = getIntent().getStringExtra("username");
        Gson userGson = new Gson();
        SharedPreferences sp = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        UserDetails currUser = userGson.fromJson(sp.getString(user,""), UserDetails.class);
        ArrayList<Meditation_Goal> savedMedGoals = currUser.med_goals;


        if(savedMedGoals.size() > 0) {
            ListView list = (ListView) findViewById(R.id.med_goals);
            final ArrayList<Meditation_Goal> med_goals = new ArrayList<Meditation_Goal>();
            final ArrayList<String> med_goals_names = new ArrayList<String>();
            Gson gson = new Gson();

            for(int i = 0;i<savedMedGoals.size();i++){
                Meditation_Goal med = savedMedGoals.get(i);
                med_goals.add(med);
                med_goals_names.add(med.getMed_name());
            }

            // list stuff
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, med_goals_names);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // TODO Auto-generated method stub
                    Meditation_Goal chosen_goal = med_goals.get(position);
                    Intent new_med = new Intent(view.getContext(),NewMed.class);
                    new_med.putExtra("already_created",true);
                    new_med.putExtra("med_num",position);
                    new_med.putExtra("med_plan_name", chosen_goal.getMed_name());
                    new_med.putExtra("username", user);
                    startActivity(new_med);

                }
            });
        }



    }

    public void new_med(View view){
        final String user = getIntent().getStringExtra("username");
        Intent new_med = new Intent(this,NewMed.class);
        new_med.putExtra("username", user);
        startActivity(new_med);
    }

}

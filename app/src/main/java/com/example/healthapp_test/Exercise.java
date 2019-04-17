package com.example.healthapp_test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Exercise extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //make list of previous exercises
        SharedPreferences sp = getSharedPreferences("Goals",Context.MODE_PRIVATE);
        int num_ex_goals = sp.getInt("num_ex_goals",0);


        //if we already have exercise goals made
        if(num_ex_goals > 0) {
            ListView list = (ListView) findViewById(R.id.ex_goals);
            final ArrayList<Exercise_Goal> ex_goals = new ArrayList<Exercise_Goal>();
            final ArrayList<String> ex_goals_names = new ArrayList<String>();
            Gson gson = new Gson();

            for(int i = 0;i<num_ex_goals;i++){
                String name = "exercise_goal"+i;
                String json = sp.getString(name, "");
                Exercise_Goal e = gson.fromJson(json, Exercise_Goal.class);
                ex_goals.add(e);
                ex_goals_names.add(e.getEx_name());
            }

            // list stuff
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, ex_goals_names);
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        // TODO Auto-generated method stub
                        Exercise_Goal chosen_goal = ex_goals.get(position);
                        Intent new_ex = new Intent(view.getContext(),NewEx.class);
                        new_ex.putExtra("already_created",true);
                        String old_ex = "exercise_goal"+position;
                        new_ex.putExtra("saved_ex",old_ex);
                        Log.i("saved_ex",old_ex);
                        startActivity(new_ex);

                    }
                });
        }


    }

    public void new_ex(View view){
        Intent new_ex = new Intent(this,NewEx.class);
        new_ex.putExtra("already_created",false);
        startActivity(new_ex);
    }






}

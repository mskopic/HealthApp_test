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

public class Meal extends AppCompatActivity {

    String diet_num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String diet_name = getIntent().getStringExtra("diet_plan_name");
        diet_num = getIntent().getStringExtra("diet_num");
        getSupportActionBar().setTitle(diet_name + " Meals");


        //get list of (possible) previous meals
        SharedPreferences sp = getSharedPreferences("Goals", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString(diet_num, "");
        Diet_Goal d = gson.fromJson(json, Diet_Goal.class);
        final ArrayList<Meal_Goal> meal_goals = d.meals;


        //if we already have meals made
        if(meal_goals.size() > 0) {
            Log.i("are_meals","This Diet Has Meals");
            ListView list = (ListView) findViewById(R.id.my_meals);
            final ArrayList<String> meal_goals_names = new ArrayList<String>();

            for(int i = 0;i<meal_goals.size();i++){
                meal_goals_names.add(meal_goals.get(i).getName());
            }

            // list stuff
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, meal_goals_names);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // TODO Auto-generated method stub
                    Meal_Goal chosen_goal = meal_goals.get(position);
                    Intent new_meal = new Intent(view.getContext(),NewMeal.class);
                    new_meal.putExtra("already_created",true);
                    new_meal.putExtra("saved_meal",position);
                    startActivity(new_meal);

                }
            });
        }




    }


    public void new_meal(View v){
        Intent new_meal = new Intent(this,NewMeal.class);
        new_meal.putExtra("diet_num",diet_num);
        startActivity(new_meal);


    }


}

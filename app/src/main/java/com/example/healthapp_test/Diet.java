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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Diet extends AppCompatActivity {

    boolean ac;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //make list of previous diets
        SharedPreferences sp = getSharedPreferences("Goals", Context.MODE_PRIVATE);
        int num_diet_goals = sp.getInt("num_diet_goals",0);

        SharedPreferences spUser = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        System.out.println(spUser.getAll());




        //if we already have exercise goals made
        if(num_diet_goals > 0) {
            ListView list = (ListView) findViewById(R.id.diet_goals);
            final ArrayList<Diet_Goal> diet_goals = new ArrayList<Diet_Goal>();
            final ArrayList<String> diet_goals_names = new ArrayList<String>();
            Gson gson = new Gson();

            for(int i = 0;i<num_diet_goals;i++){
                String name = "diet_goal"+i;
                String json = sp.getString(name, "");
                Diet_Goal d = gson.fromJson(json, Diet_Goal.class);
                diet_goals.add(d);
                diet_goals_names.add(d.getDiet_name());
            }

            // list stuff
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.my_list, diet_goals_names);
            list.setAdapter(adapter);
            list.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // TODO Auto-generated method stub
                    Diet_Goal chosen_goal = diet_goals.get(position);
                    Intent new_diet = new Intent(view.getContext(),Meal.class);
                    new_diet.putExtra("already_created",true);
                    new_diet.putExtra("diet_num","diet_goal"+position);
                    new_diet.putExtra("diet_plan_name", chosen_goal.getDiet_name());
                    new_diet.putExtra("previous","diet");
                    startActivity(new_diet);

                }
            });
        }
    }

    public void new_diet(View view){
        Intent new_diet = new Intent(this,DietPlan.class);
        new_diet.putExtra("already_created",false);
        startActivity(new_diet);
    }

}

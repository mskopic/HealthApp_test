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
import android.view.WindowManager;
import android.widget.EditText;

import com.google.gson.Gson;

public class DietMacros extends AppCompatActivity {

    public int calories;
    public double lbs_week;
    public int carbs;
    public int protein;
    public int fat;
    public String plan_name;
    public boolean ac;
    private EditText edit_cals;
    private EditText edit_lbs;
    private EditText edit_carbs;
    private EditText edit_protein;
    private EditText edit_fat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_macros);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Diet Macros");
        //keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        edit_cals = (EditText) findViewById(R.id.calories);
        edit_lbs = (EditText) findViewById(R.id.lbs_week);
        edit_carbs = (EditText )findViewById(R.id.carbs);
        edit_protein = (EditText) findViewById(R.id.protein);
        edit_fat = (EditText) findViewById(R.id.fats);

        Intent intent = getIntent();
        plan_name = intent.getStringExtra("diet_plan_name");
        ac = getIntent().getBooleanExtra("already_created",false);
        if(ac){
            SharedPreferences sharedPref = getSharedPreferences("Goals", Context.MODE_PRIVATE);
            //saved_diet is already made diet in json form
            String saved_diet = intent.getStringExtra("saved_diet");
            String json = sharedPref.getString(saved_diet, "");
            Gson gson = new Gson();
            Diet_Goal d = gson.fromJson(json, Diet_Goal.class);
            edit_cals.setText(d.getCalories());
            edit_lbs.setText(""+d.getLbs_week());
            edit_carbs.setText(d.getCarbs());
            edit_protein.setText(d.getProtein());
            edit_fat.setText(d.getFat());
        }


    }

    public void set_diet(View v){
        //get data
        calories = Integer.parseInt(edit_cals.getText().toString());
        lbs_week = Double.parseDouble(edit_lbs.getText().toString());
        carbs = Integer.parseInt(edit_carbs.getText().toString());
        protein = Integer.parseInt(edit_protein.getText().toString());
        fat = Integer.parseInt(edit_fat.getText().toString());

        //create new diet
        Diet_Goal d = new Diet_Goal();
        d.setCalories(calories);
        d.setCarbs(carbs);
        d.setFat(fat);
        d.setProtein(protein);
        d.setLbs_week(lbs_week);
        d.setDiet_name(plan_name);
        d.setDiet_type(getIntent().getIntExtra("diet_type",0));


        //make new Diet_Goal, add to SharedPreferences as a new goal or edit of previous
        SharedPreferences sp = getSharedPreferences("Goals", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json_back = gson.toJson(d);
        SharedPreferences.Editor prefsEditor = sp.edit();


        //go to meals
        Intent set_diet = new Intent(this, Meal.class);
        set_diet.putExtra("diet_plan_name", plan_name);
        // if an edit of a previous goal
        if(ac){
            String diet_num = getIntent().getStringExtra("saved_diet");
            prefsEditor.putString(diet_num, json_back);
            prefsEditor.commit();
            //let us know what diet we have when making meals
            set_diet.putExtra("diet_num",diet_num);

        }
        //otherwise this a new diet, update number of diets
        else{
            int num_diet_goals = sp.getInt("num_diet_goals",0);
            prefsEditor.putString("diet_goal"+num_diet_goals, json_back);
            prefsEditor.putInt("num_diet_goals", num_diet_goals+1);
            //let us know what diet we have when making meals
            set_diet.putExtra("diet_num","diet_goal"+num_diet_goals);
            prefsEditor.commit();
        }

        startActivity(set_diet);

    }

}

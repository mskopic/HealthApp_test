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

public class DietMacros extends AppCompatActivity {

    public int calories;
    public double lbs_week;
    public int carbs;
    public int protein;
    public int fat;
    public String plan_name;

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

        EditText editText = findViewById(R.id.calories);
        calories = Integer.parseInt(editText.getText().toString());

        editText = findViewById(R.id.lbs_week);
        lbs_week = Double.parseDouble(editText.getText().toString());

        editText = findViewById(R.id.carbs);
        carbs = Integer.parseInt(editText.getText().toString());

        editText = findViewById(R.id.protein);
        protein = Integer.parseInt(editText.getText().toString());

        editText = findViewById(R.id.fats);
        fat = Integer.parseInt(editText.getText().toString());

        Intent intent = getIntent();
        plan_name = intent.getStringExtra("diet_plan_name");



    }

    public void set_diet(View v){
        Intent set_diet = new Intent(this, Meal.class);
        set_diet.putExtra("diet_plan_name", plan_name);
        startActivity(set_diet);

    }

}

package com.example.healthapp_test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class DietMacros extends AppCompatActivity {

    public int calories;
    public double lbs_week;
    public int carbs;
    public int protein;
    public int fat;
    public String plan_name;
    public boolean ac;
    private EditText edit_cals;
    private TextView edit_lbs;
    private EditText edit_carbs;
    private EditText edit_protein;
    private EditText edit_fat;
    private Boolean machine_changed_edittext = false;



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
        edit_cals.setFilters(new InputFilter[]{new InputFilterMinMax("0", "3000")});
        edit_cals.addTextChangedListener(new TextWatcher() {
            String before;
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (s.length() > 0) {
                    if (!machine_changed_edittext) {
                        machine_changed_edittext = true;
                        Log.i("editing", "editing cals");
                        int cals = Integer.parseInt(s.toString());
                        double lbs = (cals - 1800) / 500.0;
                        edit_lbs.setText(String.valueOf(lbs));
                    }
                    machine_changed_edittext = false;
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    if (!machine_changed_edittext) {
                        machine_changed_edittext = true;
                        Log.i("editing", "editing calories");
                        int cals = Integer.parseInt(s.toString());
                        double lbs = (cals - 1800) / 500.0;
                        Log.i("lbs should be", lbs + "");
                        edit_lbs.setText(String.valueOf(lbs));
                    }
                    machine_changed_edittext = false;
                }
            }
        });

        edit_lbs = findViewById(R.id.lbs_week);

        edit_carbs = (EditText )findViewById(R.id.carbs);
        edit_carbs.setFilters(new InputFilter[]{new InputFilterMinMax("0", "250")});
        edit_carbs.addTextChangedListener(new TextWatcher() {
            public int before;
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (s.length() > 0) {
                    if (!machine_changed_edittext) {
                        machine_changed_edittext = true;
                        Log.i("editing", "editing carbs");
                        Log.i("before = ", before + "");
                        int carbs = Integer.parseInt(s.toString());
                        int cals = Integer.parseInt(edit_cals.getText().toString());
                        cals += (carbs-before) * 4;
                        edit_cals.setText(String.valueOf(cals));
                        double lbs = (cals - 1800) / 500.0;
                        edit_lbs.setText(String.valueOf(lbs));
                    }
                    machine_changed_edittext = false;
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
                if(s.length() > 0)
                    before = Integer.parseInt(s.toString());

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });


        edit_protein = (EditText) findViewById(R.id.protein);
        edit_protein.setFilters(new InputFilter[]{new InputFilterMinMax("0", "250")});
        edit_protein.addTextChangedListener(new TextWatcher() {
            private int before;
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (s.length() > 0) {
                    if (!machine_changed_edittext) {
                        machine_changed_edittext = true;
                        Log.i("editing", "editing carbs");
                        int protein = Integer.parseInt(s.toString());
                        int cals = Integer.parseInt(edit_cals.getText().toString());
                        cals += (protein-before) * 4;
                        edit_cals.setText(String.valueOf(cals));
                        double lbs = (cals - 1800) / 500.0;
                        edit_lbs.setText(String.valueOf(lbs));
                    }
                    machine_changed_edittext = false;
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
                if(s.length() > 0){
                    before = Integer.parseInt(s.toString());
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        edit_fat = (EditText) findViewById(R.id.fats);
        edit_fat.setFilters(new InputFilter[]{new InputFilterMinMax("0", "250")});
        edit_fat.addTextChangedListener(new TextWatcher() {
            private int before;
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (s.length() > 0) {
                    if (!machine_changed_edittext) {
                        machine_changed_edittext = true;
                        Log.i("editing", "editing carbs");
                        int fat = Integer.parseInt(s.toString());
                        int cals = Integer.parseInt(edit_cals.getText().toString());
                        cals += (fat-before) * 8;
                        edit_cals.setText(String.valueOf(cals));
                        double lbs = (cals - 1800) / 500.0;
                        edit_lbs.setText(String.valueOf(lbs));
                    }
                    machine_changed_edittext = false;
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
                if(s.length() > 0){
                    before = Integer.parseInt(s.toString());
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });


        Intent intent = getIntent();
        final String user = getIntent().getStringExtra("username");
        Gson userGson = new Gson();
        SharedPreferences sp = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        UserDetails currUser = userGson.fromJson(sp.getString(user,""), UserDetails.class);
        final ArrayList<Diet_Goal> savedDietGoals = currUser.diet_goals;
        ac = intent.getBooleanExtra("already_created",false);
        if(ac){
            int diet_num = intent.getIntExtra("diet_num",0);
            Diet_Goal d = savedDietGoals.get(diet_num);
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
        String user = getIntent().getStringExtra("username");
        int diet_num = getIntent().getIntExtra("diet_num",0);
        SharedPreferences sp = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        Gson userGson = new Gson();
        UserDetails currUser = userGson.fromJson(sp.getString(user,""), UserDetails.class);
        Diet_Goal currGoal = currUser.diet_goals.get(diet_num);

        currGoal.setCalories(calories);
        currGoal.setCarbs(carbs);
        currGoal.setFat(fat);
        currGoal.setProtein(protein);
        currGoal.setLbs_week(lbs_week);
        currGoal.setDiet_name(plan_name);
        currGoal.setDiet_type(getIntent().getIntExtra("diet_type",0));
        currUser.diet_goals.remove(diet_num);
        currUser.diet_goals.add(diet_num, currGoal);

        //go to meals
        Intent set_diet = new Intent(this, Meal.class);
        set_diet.putExtra("already_created",ac);
        set_diet.putExtra("username",user);
        set_diet.putExtra("diet_num",diet_num);

        startActivity(set_diet);

    }



}

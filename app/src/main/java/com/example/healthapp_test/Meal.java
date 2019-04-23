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
import android.view.MenuItem;
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



        Intent intent = getIntent();
        final String user = getIntent().getStringExtra("username");
        Gson userGson = new Gson();
        SharedPreferences sp = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        UserDetails currUser = userGson.fromJson(sp.getString(user,""), UserDetails.class);
        ArrayList<Diet_Goal> savedDietGoals = currUser.diet_goals;
        final int diet_num = intent.getIntExtra("diet_num",0);
        Diet_Goal d = savedDietGoals.get(diet_num);
        getSupportActionBar().setTitle(d.getDiet_name() + " Meals");

        final ArrayList<Meal_Goal> savedMealGoals = d.meals;

        //if we already have meals made
        if(savedMealGoals.size() > 0) {
            Log.i("are_meals","This Diet Has Meals");
            ListView list = (ListView) findViewById(R.id.my_meals);
            final ArrayList<String> meal_goals_names = new ArrayList<String>();

            //make listview of meal names
            for(int i = 0;i<savedMealGoals.size();i++){
                meal_goals_names.add(savedMealGoals.get(i).getName());
            }

            // list stuff - on click should take you to meal
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, meal_goals_names);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // TODO Auto-generated method stub
                    Meal_Goal chosen_goal = savedMealGoals.get(position);
                    Intent new_meal = new Intent(view.getContext(),NewMeal.class);
                    new_meal.putExtra("already_created_meal",true);
                    new_meal.putExtra("already_created",getIntent().getBooleanExtra("already_created",false));
                    new_meal.putExtra("diet_num",diet_num);
                    new_meal.putExtra("meal_num",position);
                    new_meal.putExtra("username", user);
                    startActivity(new_meal);

                }
            });
        }



    }


    public void new_meal(View v){
        Gson userGson = new Gson();
        String user = getIntent().getStringExtra("username");
        SharedPreferences sp = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        UserDetails currUser = userGson.fromJson(sp.getString(user,""), UserDetails.class);
        int pos = getIntent().getIntExtra("diet_num",0);
        Diet_Goal d = currUser.diet_goals.get(pos);
        d.meals.add(new Meal_Goal());
        int meal_num = d.meals.size()-1;
        SharedPreferences.Editor spEditor = sp.edit();
        String json = userGson.toJson(currUser);
        spEditor.putString(user,json);
        spEditor.commit();

        Intent new_meal = new Intent(this,NewMeal.class);
        new_meal.putExtra("already_created_meal",false);
        new_meal.putExtra("already_created",getIntent().getStringExtra("already_created"));
        new_meal.putExtra("diet_num",pos);
        new_meal.putExtra("username", user);
        new_meal.putExtra("meal_num",meal_num);
        startActivity(new_meal);


    }

    public void onBackPressed()
    {
        boolean alreadyCreatedMeal = getIntent().getBooleanExtra("already_created_meal",false);
        boolean alreadyCreated = getIntent().getBooleanExtra("already_created",false);
        int diet_num = getIntent().getIntExtra("diet_num",0);
        String user = getIntent().getStringExtra("username");
        int meal_num =  getIntent().getIntExtra("meal_num",0);

        Intent intent;

        if(!alreadyCreatedMeal){
            SharedPreferences sp = getSharedPreferences("user_details", Context.MODE_PRIVATE);
            Gson userGson = new Gson();
            UserDetails currUser = userGson.fromJson(sp.getString(user, ""), UserDetails.class);
            currUser.diet_goals.get(diet_num).meals.remove(meal_num);
            SharedPreferences.Editor spEditor = sp.edit();
            String json = userGson.toJson(currUser);
            spEditor.putString(user, json);
            spEditor.commit();
        }
        if(!alreadyCreated){
            intent = new Intent(this, DietMacros.class);
            //send all info gained and already with us
            intent.putExtra("already_created",alreadyCreated);
            intent.putExtra("username",user);
            intent.putExtra("diet_num",diet_num);
        }else{
            intent = new Intent(this,Diet.class);
            intent.putExtra("username",user);

        }
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




}

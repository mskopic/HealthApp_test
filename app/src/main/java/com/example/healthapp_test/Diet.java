package com.example.healthapp_test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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

        final String user = getIntent().getStringExtra("username");
        Gson userGson = new Gson();
        SharedPreferences sp = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        UserDetails currUser = userGson.fromJson(sp.getString(user,""), UserDetails.class);
        ArrayList<Diet_Goal> savedDietGoals = currUser.diet_goals;

        //if we already have exercise goals made
        if(savedDietGoals.size() > 0) {
            ListView list = (ListView) findViewById(R.id.diet_goals);
            final ArrayList<Diet_Goal> diet_goals = new ArrayList<Diet_Goal>();
            final ArrayList<String> diet_goals_names = new ArrayList<String>();
            Gson gson = new Gson();

            for(int i = 0;i<savedDietGoals.size();i++){
                Diet_Goal d = savedDietGoals.get(i);
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
                    new_diet.putExtra("diet_num",position);
                    new_diet.putExtra("username", user);

                    startActivity(new_diet);

                }
            });
        }
    }

    public void new_diet(View view){
        Diet_Goal newDietGoal = new Diet_Goal();
        Gson userGson = new Gson();
        String user = getIntent().getStringExtra("username");
        SharedPreferences sp = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        UserDetails currUser = userGson.fromJson(sp.getString(user,""), UserDetails.class);
        currUser.diet_goals.add(newDietGoal);
        int pos = currUser.diet_goals.size()-1;
        SharedPreferences.Editor spEditor = sp.edit();
        String json = userGson.toJson(currUser);
        spEditor.putString(user,json);
        spEditor.commit();
        Intent new_diet = new Intent(this,DietPlan.class);
        new_diet.putExtra("already_created",false);
        new_diet.putExtra("diet_num",pos);
        new_diet.putExtra("username", user);
        startActivity(new_diet);
    }


    public void onBackPressed()
    {

        String user = getIntent().getStringExtra("username");
        Intent intent = new Intent(this,TabsActivity.class);
        intent.putExtra("username",user);
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

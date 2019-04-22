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

public class Exercise extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //make list of previous exercises
        final String user = getIntent().getStringExtra("username");
        Gson userGson = new Gson();
        SharedPreferences sp = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        UserDetails currUser = userGson.fromJson(sp.getString(user,""), UserDetails.class);
        final ArrayList<Exercise_Goal> savedExGoals = currUser.ex_goals;


        //if we already have exercise goals made
        if(savedExGoals.size() > 0) {
            ListView list = (ListView) findViewById(R.id.ex_goals);
            final ArrayList<Exercise_Goal> ex_goals = new ArrayList<Exercise_Goal>();
            final ArrayList<String> ex_goals_names = new ArrayList<String>();

            for(int i = 0;i<savedExGoals.size();i++){
                Exercise_Goal ex = savedExGoals.get(i);
                ex_goals.add(ex);
                ex_goals_names.add(ex.getEx_name());
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
                        new_ex.putExtra("ex_num",position);
                        new_ex.putExtra("username", user);

                        startActivity(new_ex);

                    }
                });
        }


    }

    public void new_ex(View view){

        Exercise_Goal newExGoal = new Exercise_Goal();
        Gson userGson = new Gson();
        String user = getIntent().getStringExtra("username");
        SharedPreferences sp = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        UserDetails currUser = userGson.fromJson(sp.getString(user,""), UserDetails.class);
        currUser.ex_goals.add(newExGoal);
        int pos = currUser.ex_goals.size()-1;

        SharedPreferences.Editor spEditor = sp.edit();
        String json = userGson.toJson(currUser);
        spEditor.putString(user,json);
        spEditor.commit();

        Intent new_ex = new Intent(this,NewEx.class);
        new_ex.putExtra("already_created",false);
        new_ex.putExtra("username", user);
        new_ex.putExtra("ex_num", pos);
        startActivity(new_ex);
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

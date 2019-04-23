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
                    new_med.putExtra("username", user);
                    startActivity(new_med);

                }
            });
        }



    }

    public void new_med(View view){

        Meditation_Goal newMedGoal = new Meditation_Goal();
        Gson userGson = new Gson();
        String user = getIntent().getStringExtra("username");
        SharedPreferences sp = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        UserDetails currUser = userGson.fromJson(sp.getString(user,""), UserDetails.class);
        currUser.med_goals.add(newMedGoal);
        int pos = currUser.med_goals.size()-1;
        SharedPreferences.Editor spEditor = sp.edit();
        String json = userGson.toJson(currUser);
        spEditor.putString(user,json);
        spEditor.commit();

        Intent new_med = new Intent(this,NewMed.class);
        new_med.putExtra("already_created",false);
        new_med.putExtra("username", user);
        new_med.putExtra("med_num", pos);
        startActivity(new_med);
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

package com.example.healthapp_test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class NewEx extends AppCompatActivity {

    public static String ex_type;
    public static String plan_name;
    public boolean ac;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ex);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Exercise");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        //list of exercise choices
        ListView list = (ListView) findViewById(R.id.listview);
        String[] listItem = getResources().getStringArray(R.array.exercise_choices);


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.my_list, listItem);
        list.setAdapter(adapter);
        list.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                ex_type=adapter.getItem(position);

            }
        });

        Intent intent = getIntent();
        final String user = getIntent().getStringExtra("username");
        Gson userGson = new Gson();
        SharedPreferences sp = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        UserDetails currUser = userGson.fromJson(sp.getString(user,""), UserDetails.class);
        final ArrayList<Exercise_Goal> savedExGoals = currUser.ex_goals;
        ac = intent.getBooleanExtra("already_created",false);
        if(ac){
            int ex_num = intent.getIntExtra("ex_num",0);
            Exercise_Goal ex = savedExGoals.get(ex_num);


            EditText et  = (EditText) findViewById(R.id.plan_name);
            et.setText(ex.getEx_name());

            if(ex.getEx_type().equals("Cardio")) {
                list.setItemChecked(0, true);
                ex_type = "Cardio";
            }
            else {
                list.setItemChecked(1, true);
                ex_type = "Weight Training";
            }


        }


    }

   public void new_ex_type(View v){
       EditText editText = (EditText) findViewById(R.id.plan_name);
       plan_name = editText.getText().toString();

       String user = getIntent().getStringExtra("username");
       int ex_num = getIntent().getIntExtra("ex_num",0);
       SharedPreferences sp = getSharedPreferences("user_details", Context.MODE_PRIVATE);
       Gson userGson = new Gson();
       UserDetails currUser = userGson.fromJson(sp.getString(user,""), UserDetails.class);
       Exercise_Goal currGoal = currUser.ex_goals.get(ex_num);
       currGoal.setEx_type(ex_type);
       currGoal.setEx_name(plan_name);
       currUser.ex_goals.remove(ex_num);
       currUser.ex_goals.add(ex_num, currGoal);
       SharedPreferences.Editor spEditor = sp.edit();
       String json = userGson.toJson(currUser);
       spEditor.putString(user,json);
       spEditor.commit();
       Intent new_ex = new Intent(this, Exercise_Intensity.class);
       new_ex.putExtra("already_created",ac);
       new_ex.putExtra("username",getIntent().getStringExtra("username"));
       new_ex.putExtra("ex_num",ex_num);
       startActivity(new_ex);
   }





}

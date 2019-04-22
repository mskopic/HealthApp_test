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
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class NewMed extends AppCompatActivity {

    public int plan;
    public boolean ac;
    public String plan_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_med);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Meditation Goal");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView list = (ListView) findViewById(R.id.meditation_choices);
        String[] listItem = getResources().getStringArray(R.array.meditation_choices);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.my_list, listItem);
        list.setAdapter(adapter);
        list.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                plan = position;

            }
        });

        Intent intent = getIntent();
        final String user = getIntent().getStringExtra("username");
        Gson userGson = new Gson();
        SharedPreferences sp = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        UserDetails currUser = userGson.fromJson(sp.getString(user,""), UserDetails.class);
        ArrayList<Meditation_Goal> savedMedGoals = currUser.med_goals;
        ac = intent.getBooleanExtra("already_created",false);
        if(ac){
            //saved_diet is already made diet in json form
            int med_num = intent.getIntExtra("med_num",0);
            Meditation_Goal med = savedMedGoals.get(med_num);

            //set plan name to what it was
            EditText et  = (EditText) findViewById(R.id.plan_name);
            et.setText(med.getMed_name());
            //experiment
            list.setItemChecked(med.getMed_type(),true);

        }
    }

    public void set_schedule(View v){
        EditText editText = (EditText) findViewById(R.id.plan_name);
        plan_name = editText.getText().toString();
        Intent schedule = new Intent(this, Set_Schedule.class);
        schedule.putExtra("Previous","Meditation");
        //send all info gained and already with us
        schedule.putExtra("med_type", plan);
        schedule.putExtra("med_plan_name", plan_name);
        schedule.putExtra("already_created",ac);
        schedule.putExtra("username",getIntent().getStringExtra("username"));
        if(ac) {
            schedule.putExtra("med_num", getIntent().getIntExtra("med_num",0));
        }
        startActivity(schedule);

    }

}

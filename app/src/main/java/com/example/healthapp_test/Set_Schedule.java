package com.example.healthapp_test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.sql.Time;
import java.util.ArrayList;


public class Set_Schedule extends AppCompatActivity {

    public ArrayList<String> schedule_days = new ArrayList<String>();
    public ListView list;
    private ArrayAdapter<String> adapter;
    private SparseBooleanArray checked;
    public boolean am;
    public String time;
    public SharedPreferences sharedPref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set__schedule);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //get intent that led us here
        Intent intent = getIntent();
        String prev_act = intent.getStringExtra("Previous");
        TextView t = findViewById(R.id.special_text);

        //change message based off of activity that led us here
        if(prev_act.equals("Sleep")){
            t.setText("Select Alarm Time");
        }
        else if(prev_act.equals("Meal")){
            t.setText("Select Notification Times For Your Meal");
        }
        else if(prev_act.equals("Exercise")){
            t.setText("Select Notification Times For Your Workout");
        }
        else if(prev_act.equals("Meditation")){
            t.setText("Select Notification Times For Your Meditation");
        }


        //list stuff
        list = (ListView) findViewById(R.id.listview);
        String[] listItem = getResources().getStringArray(R.array.days);
        //boolean array tells us if item is selected
        checked = new SparseBooleanArray(listItem.length);
        adapter = new ArrayAdapter<String>(this,
                R.layout.my_list,listItem);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                if(checked.get(position)) {
                    checked.append(position,false);
                    view.setSelected(false);
                }
                else if(!checked.get(position)) {
                    checked.append(position,true);
                    view.setSelected(true);

                }

            }

        });
        list.setAdapter(adapter);

        //spinners for morning/afternoon
        Spinner spinner2 = (Spinner) findViewById(R.id.ampm_spinner);
        final ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.ampm, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);


        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                if (adapter2.getItem(position).equals("AM")){
                    am = true;
                }
                else{
                    am = false;
                }

            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


        //if we already have created this, get preferences
        boolean ac = intent.getBooleanExtra("already_created",false);
       /* if(ac){
            addOldDays(prev_act,checked,list);

        }*/

    }

    public void set_days(View v){
        //add all checked days to list
        SparseBooleanArray checked = list.getCheckedItemPositions();
        for (int i = 0; i < checked.size(); i++) {
            // Item position in adapter
            int position = checked.keyAt(i);
            // Add day if it is checked
            if (checked.valueAt(i))
                schedule_days.add(adapter.getItem(position));
        }

        Intent intent = getIntent();
        String prev_act = intent.getStringExtra("Previous");
        sharedPref = getSharedPreferences("Goals",Context.MODE_PRIVATE);

        // get time
        EditText editText = (EditText) findViewById(R.id.time);
        time = editText.getText().toString();
        String[] time_part = time.split(":");
        int hour = Integer.parseInt(time_part[0]);
        int minute = Integer.parseInt(time_part[1]);
        boolean ac = intent.getBooleanExtra("already_created",false);

        //change message based off of activity that led us here
        if(prev_act.equals("Sleep")){
        }
        else if(prev_act.equals("Meal")){
            if(ac){
                Log.i("Schedule","Old Goal");
                //create meal
                Meal_Goal me = new Meal_Goal();
                me.setLow(intent.getIntExtra("low",0));
                me.setHigh(intent.getIntExtra("high",0));
                me.setName(intent.getStringExtra("name"));
                me.days = (schedule_days);
                me.setTime(new Time(hour, minute, 0));
                //get diet meal is a part of
                me.setDiet(intent.getStringExtra("diet_num"));
                String json = sharedPref.getString(me.getDiet(), "");
                Gson gson = new Gson();
                Diet_Goal d = gson.fromJson(json, Diet_Goal.class);
                //set meal at position where old meal is to new meal with updates
                int position = intent.getIntExtra("saved_meal",0);
                d.meals.set(position,me);

                //update value of diet according to its key
                String json_back = gson.toJson(d);
                SharedPreferences.Editor prefsEditor = sharedPref.edit();
                prefsEditor.putString(me.getDiet(), json_back);
                prefsEditor.commit();
            }
            else {
                //create meal
                Meal_Goal me = new Meal_Goal();
                me.setLow(intent.getIntExtra("low",0));
                me.setHigh(intent.getIntExtra("high",0));
                me.setName(intent.getStringExtra("name"));
                me.setDiet(intent.getStringExtra("diet_num"));
                me.days = (schedule_days);
                me.setTime(new Time(hour, minute, 0));

                //get diet meal belongs to
                String json = sharedPref.getString(me.getDiet(), "");
                Gson gson = new Gson();
                Diet_Goal d = gson.fromJson(json, Diet_Goal.class);
                //add meal to diet
                d.meals.add(me);
                //update value of diet according to its key
                String json_back = gson.toJson(d);
                SharedPreferences.Editor prefsEditor = sharedPref.edit();
                prefsEditor.putString(me.getDiet(), json_back);
                prefsEditor.commit();
            }
        }
        else if(prev_act.equals("Exercise")){
            if(ac){
                String saved_ex = intent.getStringExtra("saved_ex");
                String json = sharedPref.getString(saved_ex, "");
                Gson gson = new Gson();
                Exercise_Goal eg = gson.fromJson(json, Exercise_Goal.class);
                eg.setEx_name(intent.getStringExtra("ex_name"));
                eg.setEx_type(intent.getStringExtra("ex_type"));
                eg.setEx_intensity(intent.getIntExtra("ex_intensity",0));
                eg.days = schedule_days;
                eg.setTime(new Time(hour,minute,0));
                String json_back = gson.toJson(eg);

                SharedPreferences.Editor prefsEditor = sharedPref.edit();
                prefsEditor.putString(saved_ex, json_back);
                prefsEditor.commit();

            }
            else{
                Exercise_Goal eg = new Exercise_Goal();
                eg.setEx_name(intent.getStringExtra("ex_name"));
                eg.setEx_type(intent.getStringExtra("ex_type"));
                eg.setEx_intensity(intent.getIntExtra("ex_intensity",0));
                eg.days = (schedule_days);
                eg.setTime(new Time(hour,minute,0));

                SharedPreferences.Editor prefsEditor = sharedPref.edit();
                Gson gson = new Gson();
                String json = gson.toJson(eg);

                int num_ex_goal = sharedPref.getInt("num_ex_goals",0);
                String name = "exercise_goal"+num_ex_goal;
                prefsEditor.putString(name, json);
                num_ex_goal++;
                prefsEditor.putInt("num_ex_goals",num_ex_goal);
                prefsEditor.commit();

            }



        }
        else if(prev_act.equals("Meditation")){
        }


        Intent done = new Intent(this,MainActivity.class);
        startActivity(done);
    }

    // have to override so back button takes us to right activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if ( id == android.R.id.home ) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addOldDays(String prev_act,SparseBooleanArray checked, ListView list){
        EditText time = findViewById(R.id.time);
        Intent intent = getIntent();
        ArrayList<String> days = new ArrayList<String>();
        if(prev_act.equals("Sleep")){
        }
        else if(prev_act.equals("Meal")){
        }
        else if(prev_act.equals("Exercise")){
            String saved_goal = intent.getStringExtra("saved_ex");
            sharedPref.getString(saved_goal,"");
            String json = sharedPref.getString(saved_goal, "");
            Gson gson = new Gson();
            Exercise_Goal eg = gson.fromJson(json, Exercise_Goal.class);
            days = eg.days;

        }
        else if(prev_act.equals("Meditation")){
        }

        for(int i = 0;i<days.size();i++){
            checked.append(i,true);

        }

    }




}

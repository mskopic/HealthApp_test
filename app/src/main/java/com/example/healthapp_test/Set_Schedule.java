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

    public ArrayList<Integer> schedule_days = new ArrayList<Integer>();
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
       if(ac){
            addOldDays(prev_act);
        }

    }

    public void set_days(View v){
        //add all checked days to list
        SparseBooleanArray checked = list.getCheckedItemPositions();
        for (int i = 0; i < checked.size(); i++) {
            // Item position in adapter
            int position = checked.keyAt(i);
            // Add day if it is checked
            if (checked.valueAt(i))
                schedule_days.add(position);
        }

        Intent intent = getIntent();
        String prev_act = intent.getStringExtra("Previous");
        //create new class based off everything that has been selected
        new_class(prev_act);
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

    public void addOldDays(String prev_act){
        sharedPref = getSharedPreferences("Goals",Context.MODE_PRIVATE);
        EditText time = findViewById(R.id.time);
        Intent intent = getIntent();
        ArrayList<Integer> days = new ArrayList<Integer>();
        String[] listItem = getResources().getStringArray(R.array.days);
        int hours = 0;
        int minutes = 0;
        if(prev_act.equals("Sleep")){
        }
        else if(prev_act.equals("Meal")){
            int saved_meal_position = intent.getIntExtra("saved_meal",0);
            String diet_num = intent.getStringExtra("diet_num");
            Log.i("diet_num",diet_num);
            String json = sharedPref.getString(diet_num, "");
            Gson gson = new Gson();
            Diet_Goal d = gson.fromJson(json, Diet_Goal.class);
            Meal_Goal meal = d.meals.get(saved_meal_position);
            //get days it is previously set to
            days = meal.days;
            hours = meal.getTime().getHours();
            minutes = meal.getTime().getMinutes();

        }
        else if(prev_act.equals("Exercise")){
            //get object we are adjusting
            String saved_goal = intent.getStringExtra("saved_ex");
            String json = sharedPref.getString(saved_goal, "");
            Gson gson = new Gson();
            Exercise_Goal eg = gson.fromJson(json, Exercise_Goal.class);
            //get days it is previously set to
            days = eg.days;
            hours = eg.getTime().getHours();
            minutes = eg.getTime().getMinutes();

        }
        else if(prev_act.equals("Meditation")){
            String saved_goal = intent.getStringExtra("med_num");
            String json = sharedPref.getString(saved_goal, "");
            Gson gson = new Gson();
            Meditation_Goal med = gson.fromJson(json, Meditation_Goal.class);
            //get days it is previously set to
            days = med.days;
            //set time
            hours = med.getTime().getHours();
            minutes = med.getTime().getMinutes();

        }

        //set items to be checked
        for(int i = 0;i<days.size();i++){
            list.setItemChecked(days.get(i),true);
        }
        for(int i = 0;i<days.size();i++){
            checked.append(i,true);

        }

        //set time
        if(hours > 12) {
            hours = hours - 12;
        }
        if(minutes == 0) {
            time.setText(hours + ":" + minutes+"0");
        }
        else{
            time.setText(hours + ":" + minutes);
        }



    }


    public void new_class(String prev_act){
        Intent intent = getIntent();
        sharedPref = getSharedPreferences("Goals",Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPref.edit();
        Gson gson = new Gson();

        //get time
        EditText editText = (EditText) findViewById(R.id.time);
        time = editText.getText().toString();
        String[] time_part = time.split(":");
        int hour = Integer.parseInt(time_part[0]);
        int minute = Integer.parseInt(time_part[1]);
        if(am == false){
            hour = hour + 12;
        }

        //see if we are editing or creating something new
        boolean ac = intent.getBooleanExtra("already_created",false);
        if(prev_act.equals("Sleep")){
            Sleep_Goal sl = new Sleep_Goal();
            sl.setHours_sleep(intent.getIntExtra("hours_sleep",0));
            sl.setAlarm(new Time(hour,minute,0));
            sl.setDays(schedule_days);
            String json = gson.toJson(sl);
            int num_sleep_goal = sharedPref.getInt("num_sleep_goals",0);
            String name = "sleep_goal"+num_sleep_goal;
            prefsEditor.putString(name, json);
            num_sleep_goal++;
            prefsEditor.putInt("num_sleep_goals",num_sleep_goal);
            prefsEditor.commit();
        }
        else if(prev_act.equals("Meal")){
            //create meal
            Meal_Goal me = new Meal_Goal();
            me.setLow(intent.getIntExtra("low",0));
            me.setHigh(intent.getIntExtra("high",0));
            me.setName(intent.getStringExtra("name"));
            me.setDiet(intent.getStringExtra("diet_num"));
            Log.i("diet_num",intent.getStringExtra("diet_num"));
            me.days = (schedule_days);
            me.setTime(new Time(hour, minute, 0));
            //get diet meal belongs to
            String json = sharedPref.getString(me.getDiet(), "");
            Log.i("meal.get_diet",me.getDiet());
            Diet_Goal d = gson.fromJson(json, Diet_Goal.class);
            if(ac){
                //set meal at position where old meal is to new meal with updates
                int position = intent.getIntExtra("saved_meal",0);
                d.meals.set(position,me);
                //update value of diet according to its key
                String json_back = gson.toJson(d);
                prefsEditor.putString(me.getDiet(), json_back);
                prefsEditor.commit();
            }
            else {
                //add meal to diet
                d.meals.add(me);
                //update value of diet according to its key
                String json_back = gson.toJson(d);
                prefsEditor.putString(me.getDiet(), json_back);
                prefsEditor.commit();
            }
        }
        else if(prev_act.equals("Exercise")){
            //create exercise
            Exercise_Goal eg = new Exercise_Goal();
            eg.setEx_name(intent.getStringExtra("ex_name"));
            eg.setEx_type(intent.getStringExtra("ex_type"));
            eg.setEx_intensity(intent.getIntExtra("ex_intensity",0));
            eg.days = schedule_days;
            eg.setTime(new Time(hour,minute,0));

            // if already created exercise, just change item at position
            if(ac){
                String saved_ex = intent.getStringExtra("saved_ex");
                String json_back = gson.toJson(eg);
                prefsEditor.putString(saved_ex, json_back);
                prefsEditor.commit();

            }
            // otherwise, add new item to shared preferences - ex_goal#
            else{
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
            Meditation_Goal med = new Meditation_Goal();
            med.setMed_name(intent.getStringExtra("med_plan_name"));
            med.setMed_type(intent.getIntExtra("med_type",0));
            med.days = schedule_days;
            med.setTime(new Time(hour,minute,0));

            if(ac){
                String saved_med = intent.getStringExtra("med_num");
                String json_back = gson.toJson(med);
                prefsEditor.putString(saved_med, json_back);
                prefsEditor.commit();
            }
            else{
                String json = gson.toJson(med);
                int num_med_goal = sharedPref.getInt("num_med_goals",0);
                String name = "med_goal"+num_med_goal;
                prefsEditor.putString(name, json);
                num_med_goal++;
                prefsEditor.putInt("num_med_goals",num_med_goal);
                prefsEditor.commit();

            }

        }


    }




}

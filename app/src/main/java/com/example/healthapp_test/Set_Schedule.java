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
import android.widget.Toast;

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

        //if no days selected
        if(schedule_days.size() == 0){
            Context context = getApplicationContext();
            CharSequence text = "You Must Select At Least One Day To Continue";
            Toast myToast = Toast.makeText(context,text, Toast.LENGTH_SHORT);
            myToast.show();
            return;
        }

        Intent intent = getIntent();
        String prev_act = intent.getStringExtra("Previous");
        //create new class based off everything that has been selected
        new_class(prev_act);
        Intent done = new Intent(this,TabsActivity.class);
        done.putExtra("username", getIntent().getStringExtra("username"));
        startActivity(done);
    }

    // have to override so back button takes us to right activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if ( id == android.R.id.home ) {
            Intent intent = getIntent();
            String prev_activity = intent.getStringExtra("Previous");
            goBack(prev_activity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addOldDays(String prev_act){
        final String user = getIntent().getStringExtra("username");
        sharedPref = getSharedPreferences("user_details",Context.MODE_PRIVATE);
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
            SharedPreferences sp = getSharedPreferences("user_details", Context.MODE_PRIVATE);
            Gson gson = new Gson();
            UserDetails currUser = gson.fromJson(sp.getString(user,""), UserDetails.class);
            ArrayList<Exercise_Goal> savedExGoals = currUser.ex_goals;
            int saved_goal = intent.getIntExtra("ex_num",0);
            Exercise_Goal eg = savedExGoals.get(saved_goal);
            //get days it is previously set to
            days = eg.days;
            hours = eg.getTime().getHours();
            minutes = eg.getTime().getMinutes();

        }
        else if(prev_act.equals("Meditation")){
            SharedPreferences sp = getSharedPreferences("user_details", Context.MODE_PRIVATE);
            Gson gson = new Gson();
            UserDetails currUser = gson.fromJson(sp.getString(user,""), UserDetails.class);
            ArrayList<Meditation_Goal> savedMedGoals = currUser.med_goals;
            int saved_goal = intent.getIntExtra("med_num",0);
            Meditation_Goal med = savedMedGoals.get(saved_goal);
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
        Spinner ampm = findViewById(R.id.ampm_spinner);
        if(hours > 12) {
            hours = hours - 12;
            ampm.setSelection(1,true);

        }
        else{
            ampm.setSelection(0,true);
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
        Gson gson = new Gson();
        String user = getIntent().getStringExtra("username");
        SharedPreferences sp = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sp.edit();
        UserDetails currUser = gson.fromJson(sp.getString(user,""), UserDetails.class);

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
            currUser.sleep_goals.add(sl);
            String json = gson.toJson(currUser);
            prefsEditor.putString(user,json);
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
            int pos = getIntent().getIntExtra("ex_num",0);

            Exercise_Goal eg = currUser.ex_goals.get(pos);
            eg.days = schedule_days;
            eg.setTime(new Time(hour,minute,0));
            currUser.ex_goals.remove(pos);
            currUser.ex_goals.add(pos,eg);
            String json = gson.toJson(currUser);
            prefsEditor.putString(user,json);
            prefsEditor.commit();

        }
        else if(prev_act.equals("Meditation")){
            int pos = getIntent().getIntExtra("med_num",0);
            Meditation_Goal med = currUser.med_goals.get(pos);
            med.days = schedule_days;
            med.setTime(new Time(hour,minute,0));
            currUser.med_goals.remove(pos);
            currUser.med_goals.add(pos,med);
            String json = gson.toJson(currUser);
            prefsEditor.putString(user,json);
            prefsEditor.commit();
        }


    }


    public void goBack(String previous){
        if(previous.equals("Exercise")){
            String user = getIntent().getStringExtra("username");
            int ex_num = getIntent().getIntExtra("ex_num",0);
            boolean ac = getIntent().getBooleanExtra("already_created",false);

            Intent intent = new Intent(this,Exercise_Intensity.class);
            intent.putExtra("username",user);
            intent.putExtra("ex_num",ex_num);
            intent.putExtra("already_created",ac);
            startActivity(intent);
        }
        else if(previous.equals("Meal")){

        }
        else if(previous.equals("Meditation")){
            String user = getIntent().getStringExtra("username");
            int med_num = getIntent().getIntExtra("med_num",0);
            boolean ac = getIntent().getBooleanExtra("already_created",false);

            Intent intent = new Intent(this,NewMed.class);
            intent.putExtra("username",user);
            intent.putExtra("med_num",med_num);
            intent.putExtra("already_created",ac);
            startActivity(intent);


        }
        else if(previous.equals("Mood")){

        }
        else if(previous.equals("Sleep")){

        }


    }



}

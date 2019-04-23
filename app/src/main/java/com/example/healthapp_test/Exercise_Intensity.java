package com.example.healthapp_test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Exercise_Intensity extends AppCompatActivity {

    public int ex_intensity;
    public String ex_type;
    public String ex_name;
    public boolean ac;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_intensity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Exercise Intensity");
        ex_intensity = -1;


        //get exercise type that was previously selected (Weight Training or Cardio)
        Intent intent = getIntent();
        final String user = intent.getStringExtra("username");
        Gson userGson = new Gson();
        SharedPreferences sp = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        UserDetails currUser = userGson.fromJson(sp.getString(user,""), UserDetails.class);
        final ArrayList<Exercise_Goal> savedExGoals = currUser.ex_goals;
        int ex_num = intent.getIntExtra("ex_num",0);
        Exercise_Goal ex = savedExGoals.get(ex_num);
        ex_type = ex.getEx_type();

        ListView list = (ListView) findViewById(R.id.listview);

        String[] listItem;
        if(ex_type.equals("Weight Training")) {
             listItem = getResources().getStringArray(R.array.weight_intensity);
        }
        else{
            listItem = getResources().getStringArray(R.array.cardio_intensity);
        }
        //list stuff
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.my_list, listItem);
        list.setAdapter(adapter);
        list.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                ex_intensity= position;

            }
        });
        // if already created
        ac = intent.getBooleanExtra("already_created",false);
        if(ac){
            list.setItemChecked(ex.getEx_intensity(),true);
            ex_intensity = ex.getEx_intensity();

        }

    }
    public void set_schedule(View v){
        if(ex_intensity == -1){
            Context context = getApplicationContext();
            CharSequence text = "You Must Select An Exercise Intensity To Continue";
            Toast myToast = Toast.makeText(context,text, Toast.LENGTH_SHORT);
            myToast.show();
            return;
        }

        String user = getIntent().getStringExtra("username");
        int ex_num = getIntent().getIntExtra("ex_num",0);
        SharedPreferences sp = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        Gson userGson = new Gson();
        UserDetails currUser = userGson.fromJson(sp.getString(user,""), UserDetails.class);
        Exercise_Goal currGoal = currUser.ex_goals.get(ex_num);
        currGoal.setEx_intensity(ex_intensity);
        currUser.ex_goals.remove(ex_num);
        currUser.ex_goals.add(ex_num, currGoal);
        SharedPreferences.Editor spEditor = sp.edit();
        String json = userGson.toJson(currUser);
        spEditor.putString(user,json);
        spEditor.commit();

        Intent schedule = new Intent(this, Set_Schedule.class);
        schedule.putExtra("Previous","Exercise");
        schedule.putExtra("already_created",ac);
        schedule.putExtra("username",getIntent().getStringExtra("username"));
        schedule.putExtra("ex_num",ex_num);

        startActivity(schedule);

    }


    public void onBackPressed()
    {

        String user = getIntent().getStringExtra("username");
        int ex_num = getIntent().getIntExtra("ex_num",0);
        boolean ac = getIntent().getBooleanExtra("already_created",false);

        Intent intent = new Intent(this,NewEx.class);
        intent.putExtra("username",user);
        intent.putExtra("ex_num",ex_num);
        intent.putExtra("already_created",ac);
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

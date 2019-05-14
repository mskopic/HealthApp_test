package com.example.healthapp_test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Mood extends AppCompatActivity {

    public String time;
    public boolean am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Spinner spinner2 = (Spinner) findViewById(R.id.ampm_spinner2);
        final ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.ampm, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);


        // get am/pm
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
        final String user = getIntent().getStringExtra("username");
        Gson userGson = new Gson();
        SharedPreferences sp = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        UserDetails currUser = userGson.fromJson(sp.getString(user,""), UserDetails.class);
        int hours = currUser.moodHr;
        int minutes = currUser.moodMin;
        if(hours!=100 && minutes!=100){
            EditText texttime = findViewById(R.id.time);
            time = texttime.getText().toString();
            if(hours > 12) {
                hours = hours - 12;
                spinner2.setSelection(1,true);

            }
            else{
                spinner2.setSelection(0,true);
            }
            if(minutes == 0) {
                texttime.setText(hours + ":" + minutes+"0");
            }
            else{
                texttime.setText(hours + ":" + minutes);
            }

        }




    }
    public void back_to_main(View view){
        Intent main = new Intent(this,TabsActivity.class);
        // get time of day
        EditText texttime = findViewById(R.id.time);
        time = texttime.getText().toString();
        String[] time_part = time.split(":");
        int hour = Integer.parseInt(time_part[0]);
        int minute = Integer.parseInt(time_part[1]);
        if(am == false){
            hour = hour + 12;
        }

        final String user = getIntent().getStringExtra("username");
        Gson userGson = new Gson();
        SharedPreferences sp = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sp.edit();
        UserDetails currUser = userGson.fromJson(sp.getString(user,""), UserDetails.class);
        currUser.moodHr = hour;
        currUser.moodMin = minute;
        String json = userGson.toJson(currUser);
        prefsEditor.putString(user,json);
        prefsEditor.commit();

        main.putExtra("username", user);
        startActivity(main);


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

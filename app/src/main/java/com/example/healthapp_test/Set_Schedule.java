package com.example.healthapp_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import java.util.ArrayList;


public class Set_Schedule extends AppCompatActivity {

    public ArrayList<String> schedule_days = new ArrayList<String>();
    public ListView list;
    private ArrayAdapter<String> adapter;
    private SparseBooleanArray checked;
    public boolean am;
    public String time;

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



        // get time
        EditText editText = (EditText) findViewById(R.id.editText2);
        String time = editText.getText().toString();

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
                    Log.i("List","Deselecting item");
                }
                else if(!checked.get(position)) {
                    checked.append(position,true);
                    view.setSelected(true);
                    Log.i("List","Selecting item");
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

        //get time
        EditText texttime = findViewById(R.id.editText2);
        time = texttime.getText().toString();

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

    }

    public void set_days(View v){
        //add all checked days to list
        SparseBooleanArray checked = list.getCheckedItemPositions();
        for (int i = 0; i < checked.size(); i++) {
            // Item position in adapter
            int position = checked.keyAt(i);
            // Add sport if it is checked i.e.) == TRUE!
            if (checked.valueAt(i))
                schedule_days.add(adapter.getItem(position));
        }

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
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



}

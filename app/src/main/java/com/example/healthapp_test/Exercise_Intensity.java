package com.example.healthapp_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Exercise_Intensity extends AppCompatActivity {

    public static String ex_intensity;
    public String ex_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_intensity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Exercise Intensity");


        //get exercise type that was previously selected (Weight Training or Cardio)
        Intent intent = getIntent();
        ex_type = intent.getStringExtra(NewEx.ex_type);

        ListView list = (ListView) findViewById(R.id.listview);
        String[] listItem;
        if(ex_type.equals("Weight Training")) {
             listItem = getResources().getStringArray(R.array.weight_intensity);
        }
        else{
            listItem = getResources().getStringArray(R.array.cardio_intensity);
        }


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, listItem);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                ex_intensity=adapter.getItem(position);

            }
        });

    }
    public void set_schedule(View v){
        Intent schedule = new Intent(this, Set_Schedule.class);
        schedule.putExtra("Previous","Exercise");
        startActivity(schedule);

    }

}

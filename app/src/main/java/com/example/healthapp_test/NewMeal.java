package com.example.healthapp_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class NewMeal extends AppCompatActivity {

    public int low;
    public int high;
    public int meal_size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Meal");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //meal calorie range
        final EditText editlow = findViewById(R.id.low);
        final EditText edithigh = findViewById(R.id.high);

        //spinners for morning/afternoon
        Spinner spinner2 = (Spinner) findViewById(R.id.meal_size);
        final ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.meal_sizes, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);


        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                if (adapter2.getItem(position).equals("Small")){
                    meal_size = 0;
                    editlow.setText("200");
                    edithigh.setText("400");
                }
                else if(adapter2.getItem(position).equals("Medium")){
                    meal_size = 1;
                    editlow.setText("400");
                    edithigh.setText("700");
                }
                else{
                    meal_size = 2;
                    editlow.setText("700");
                    edithigh.setText("1000");
                }

            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        //read input at end
        low = Integer.parseInt(editlow.getText().toString());
        high = Integer.parseInt(edithigh.getText().toString());
    }

    public void set_schedule(View v){
        Intent schedule = new Intent(this, Set_Schedule.class);
        schedule.putExtra("Previous","Meal");
        startActivity(schedule);

    }

}

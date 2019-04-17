package com.example.healthapp_test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;

public class NewMeal extends AppCompatActivity {

    public int low;
    public int high;
    public int meal_size;
    public String name;
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


        //change values of low and high based on meal size selected
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
                else if(adapter2.getItem(position).equals("Large")){
                    meal_size = 2;
                    editlow.setText("700");
                    edithigh.setText("1000");
                }
                else{
                    meal_size = 3;
                }

            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


        //if already created, fill in values
        boolean ac = getIntent().getBooleanExtra("already_created",false);
        if(ac){
            //if already created, get the meal we already created
            SharedPreferences sp = getSharedPreferences("Goals", Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String diet_num = getIntent().getStringExtra("diet_num");
            String json = sp.getString(diet_num, "");
            Diet_Goal d = gson.fromJson(json, Diet_Goal.class);
            Meal_Goal current_meal = d.meals.get(getIntent().getIntExtra("saved_meal",0));

            //set text of fields to values of created meal
            spinner2.setSelection(3,true);
            EditText editl = findViewById(R.id.low);
            EditText edith = findViewById(R.id.high);

            editl.setText(current_meal.getLow()+"");
            edith.setText(current_meal.getHigh()+"");


            EditText editname = findViewById(R.id.meal_name);
            editname.setText(current_meal.getName());


        }


    }

    public void set_schedule(View v){
        Intent schedule = new Intent(this, Set_Schedule.class);
        schedule.putExtra("Previous","Meal");

        Intent intent = getIntent();

        //send meal name, low, high and diet name to set_schedule, should be instantiated there
        EditText editText = findViewById(R.id.meal_name);
        EditText editlow = findViewById(R.id.low);
        EditText edithigh = findViewById(R.id.high);

        name = editText.getText().toString();
        low = Integer.parseInt(editlow.getText().toString());
        high = Integer.parseInt(edithigh.getText().toString());

        String diet_num = intent.getStringExtra("diet_num");
        int meal_num = intent.getIntExtra("saved_meal",0);

        schedule.putExtra("diet_num",diet_num);
        schedule.putExtra("low",low);
        schedule.putExtra("high",high);
        schedule.putExtra("name",name);
        schedule.putExtra("already_created",getIntent().getBooleanExtra("already_created",false));
        schedule.putExtra("saved_meal",meal_num);
        startActivity(schedule);

    }

    @Override
    public void onBackPressed()
    {
        String diet_num = getIntent().getStringExtra("diet_num");
        int meal_num = getIntent().getIntExtra("saved_meal",0);

        Intent intent = new Intent(this,Meal.class);
        intent.putExtra("diet_num",diet_num);
        intent.putExtra("already_created",getIntent().getBooleanExtra("already_created",false));
        intent.putExtra("saved_meal",meal_num);
        intent.putExtra("diet_plan_name", getIntent().getStringExtra("diet_plan_name"));
        Log.i("back pressed diet num",diet_num);
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

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

import java.util.ArrayList;

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


        Intent intent = getIntent();
        final String user = getIntent().getStringExtra("username");
        Gson userGson = new Gson();
        SharedPreferences sp = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        UserDetails currUser = userGson.fromJson(sp.getString(user,""), UserDetails.class);
        final ArrayList<Diet_Goal> savedDietGoals = currUser.diet_goals;


        boolean ac = getIntent().getBooleanExtra("already_created",false);
        if(ac){
            //if already created, get the meal we already created
            int diet_num = intent.getIntExtra("diet_num",0);
            Diet_Goal d = savedDietGoals.get(diet_num);
            int meal_num = intent.getIntExtra("meal_num",0);
            Meal_Goal m = d.meals.get(meal_num);

            spinner2.setSelection(3,true);
            EditText editl = findViewById(R.id.low);
            EditText edith = findViewById(R.id.high);
            EditText editname = findViewById(R.id.meal_name);



            if(getIntent().getBooleanExtra("already_created_meal",false)){
                editl.setText(m.getLow()+"");
                edith.setText(m.getHigh()+"");

                editname.setText(m.getName());
            }

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

        intent = getIntent();
        final String user = getIntent().getStringExtra("username");
        Gson userGson = new Gson();
        SharedPreferences sp = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        UserDetails currUser = userGson.fromJson(sp.getString(user,""), UserDetails.class);
        final ArrayList<Diet_Goal> savedDietGoals = currUser.diet_goals;
        int diet_num = intent.getIntExtra("diet_num",0);
        Diet_Goal d = savedDietGoals.get(diet_num);
        int meal_num = intent.getIntExtra("meal_num",0);
        Meal_Goal m = d.meals.get(meal_num);


        name = editText.getText().toString();
        low = Integer.parseInt(editlow.getText().toString());
        high = Integer.parseInt(edithigh.getText().toString());

        m.setName(name);
        m.setLow(low);
        m.setHigh(high);
        d.meals.remove(meal_num);
        d.meals.add(meal_num,m);
        currUser.diet_goals.remove(diet_num);
        currUser.diet_goals.add(diet_num, d);
        SharedPreferences.Editor spEditor = sp.edit();
        String json = userGson.toJson(currUser);
        spEditor.putString(user,json);
        spEditor.commit();
        schedule.putExtra("already_created_meal",getIntent().getBooleanExtra("already_created_meal",false));
        schedule.putExtra("already_created",getIntent().getBooleanExtra("already_created",false));
        schedule.putExtra("diet_num",diet_num);
        schedule.putExtra("username", user);
        schedule.putExtra("meal_num",meal_num);
        startActivity(schedule);

    }

    @Override
    public void onBackPressed()
    {
        boolean alreadyCreatedMeal = getIntent().getBooleanExtra("already_created_meal",false);
        boolean alreadyCreated = getIntent().getBooleanExtra("already_created",false);
        int diet_num = getIntent().getIntExtra("diet_num",0);
        String user = getIntent().getStringExtra("username");
        int meal_num =  getIntent().getIntExtra("meal_num",0);

        Intent intent = new Intent(this,Meal.class);
        intent.putExtra("username",user);
        intent.putExtra("diet_num",diet_num);
        intent.putExtra("meal_num",meal_num);
        intent.putExtra("already_created",alreadyCreated);
        intent.putExtra("already_created_meal",alreadyCreatedMeal);
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

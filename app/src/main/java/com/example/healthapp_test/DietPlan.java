package com.example.healthapp_test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import com.google.gson.Gson;

public class DietPlan extends AppCompatActivity {

    public int plan;
    public String plan_name;
    public boolean ac;
    public EditText plantext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_plan);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Diet Plan");
        //keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //get plan_name
        plantext = findViewById(R.id.plan_name);



        //list stuff
        ListView list = (ListView) findViewById(R.id.listview);
        String[] listItem = getResources().getStringArray(R.array.diets);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.my_list, listItem);
        list.setAdapter(adapter);
        list.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                //set diet plan to position
                plan= position;

            }
        });


        //if we are in an already created diet
        Intent intent = getIntent();
        ac = intent.getBooleanExtra("already_created",false);
        if(ac){
            SharedPreferences sharedPref = getSharedPreferences("Goals", Context.MODE_PRIVATE);
            //saved_diet is already made diet in json form
            String saved_diet = intent.getStringExtra("diet_num");
            String json = sharedPref.getString(saved_diet, "");
            Gson gson = new Gson();
            Diet_Goal d = gson.fromJson(json, Diet_Goal.class);

            //set plan name to what it was
            EditText et  = (EditText) findViewById(R.id.plan_name);
            et.setText(d.getDiet_name());
            //experiment
            list.setItemChecked(d.getDiet_type(),true);
            plan = d.getDiet_type();

        }


    }

    public void new_macros(View v){
        Intent new_d = new Intent(this, DietMacros.class);
        plan_name = plantext.getText().toString();
        //send all info gained and already with us
        new_d.putExtra("diet_type", plan);
        new_d.putExtra("diet_plan_name", plan_name);
        new_d.putExtra("already_created",ac);
        new_d.putExtra("saved_diet",getIntent().getStringExtra("saved_diet"));


        startActivity(new_d);

    }

}

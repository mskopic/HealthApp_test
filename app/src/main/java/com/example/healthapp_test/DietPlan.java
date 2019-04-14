package com.example.healthapp_test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

public class DietPlan extends AppCompatActivity {

    public int plan;
    public String plan_name;
    public boolean ac;
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


        ListView list = (ListView) findViewById(R.id.listview);
        String[] listItem = getResources().getStringArray(R.array.diets);




        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, listItem);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                plan= position;

            }
        });


        //if we are in an already created diet
        Intent intent = getIntent();
        ac = intent.getBooleanExtra("already_created",false);
        if(ac){
            SharedPreferences sharedPref = getSharedPreferences("Goals", Context.MODE_PRIVATE);
            //saved_diet is already made diet in json form
            String saved_diet = intent.getStringExtra("saved_diet");
            String json = sharedPref.getString(saved_diet, "");
            Gson gson = new Gson();
            Diet_Goal d = gson.fromJson(json, Diet_Goal.class);

            //set plan name to what it was
            EditText et  = (EditText) findViewById(R.id.plan_name);
            et.setText(d.getDiet_name());
            list.setSelection(d.getDiet_type());
            //experiment
            list.setItemChecked(d.getDiet_type(),true);

        }


    }

    public void new_macros(View v){
        Intent new_d = new Intent(this, DietMacros.class);
        EditText editText = (EditText) findViewById(R.id.plan_name);
        plan_name = editText.getText().toString();
        //send all info gained and already with us
        new_d.putExtra("diet_type", plan);
        new_d.putExtra("diet_plan_name", plan_name);
        new_d.putExtra("already_created",ac);
        new_d.putExtra("saved_diet",getIntent().getStringExtra("saved_diet"));


        startActivity(new_d);

    }

}

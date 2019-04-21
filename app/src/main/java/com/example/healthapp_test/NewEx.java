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

public class NewEx extends AppCompatActivity {

    public static String ex_type;
    public static String plan_name;
    public boolean ac;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ex);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Exercise");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        //list of exercise choices
        ListView list = (ListView) findViewById(R.id.listview);
        String[] listItem = getResources().getStringArray(R.array.exercise_choices);


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.my_list, listItem);
        list.setAdapter(adapter);
        list.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                ex_type=adapter.getItem(position);

            }
        });

        Intent intent = getIntent();
        ac = intent.getBooleanExtra("already_created",false);
        if(ac){
            SharedPreferences sharedPref = getSharedPreferences("Goals",Context.MODE_PRIVATE);
            String saved_ex = intent.getStringExtra("saved_ex");
            String json = sharedPref.getString(saved_ex, "");
            Gson gson = new Gson();
            Exercise_Goal e = gson.fromJson(json, Exercise_Goal.class);

            EditText et  = (EditText) findViewById(R.id.plan_name);
            et.setText(e.getEx_name());

            if(e.getEx_type().equals("Cardio")) {
                list.setItemChecked(0, true);
                ex_type = "Cardio";
            }
            else {
                list.setItemChecked(1, true);
                ex_type = "Weight Training";
            }


        }


    }

   public void new_ex_type(View v){
       EditText editText = (EditText) findViewById(R.id.plan_name);
       plan_name = editText.getText().toString();

       Intent new_ex = new Intent(this, Exercise_Intensity.class);
       new_ex.putExtra("ex_type", ex_type);
       new_ex.putExtra("ex_name", plan_name);
       new_ex.putExtra("already_created",ac);
       if(ac) {
           Log.i("saved_ex - new",getIntent().getStringExtra("saved_ex"));
           new_ex.putExtra("saved_ex", getIntent().getStringExtra("saved_ex"));
       }

       startActivity(new_ex);
   }





}

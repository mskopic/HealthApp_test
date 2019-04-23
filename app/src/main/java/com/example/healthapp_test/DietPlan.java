package com.example.healthapp_test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

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
        plan = -1;



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
        final String user = getIntent().getStringExtra("username");
        Gson userGson = new Gson();
        SharedPreferences sp = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        UserDetails currUser = userGson.fromJson(sp.getString(user,""), UserDetails.class);
        final ArrayList<Diet_Goal> savedDietGoals = currUser.diet_goals;

        ac = intent.getBooleanExtra("already_created",false);
        if(ac){
            int diet_num = intent.getIntExtra("diet_num",0);
            Diet_Goal d = savedDietGoals.get(diet_num);

            //set plan name to what it was
            EditText et  = (EditText) findViewById(R.id.plan_name);
            et.setText(d.getDiet_name());
            //experiment
            list.setItemChecked(d.getDiet_type(),true);
            plan = d.getDiet_type();

        }


    }

    public void new_macros(View v){
        if(plan == -1){
            Context context = getApplicationContext();

            CharSequence text = "You Must Select An Meal Plan To Continue";
            Toast myToast = Toast.makeText(context,text, Toast.LENGTH_SHORT);
            myToast.show();
            return;
        }

        Intent new_d = new Intent(this, DietMacros.class);
        plan_name = plantext.getText().toString();

        String user = getIntent().getStringExtra("username");
        int diet_num = getIntent().getIntExtra("diet_num",0);
        SharedPreferences sp = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        Gson userGson = new Gson();
        UserDetails currUser = userGson.fromJson(sp.getString(user,""), UserDetails.class);
        Diet_Goal currGoal = currUser.diet_goals.get(diet_num);
        currGoal.setDiet_type(plan);
        currGoal.setDiet_name(plan_name);
        currUser.diet_goals.remove(diet_num);
        currUser.diet_goals.add(diet_num, currGoal);
        SharedPreferences.Editor spEditor = sp.edit();
        String json = userGson.toJson(currUser);
        spEditor.putString(user,json);
        spEditor.commit();
        //send all info gained and already with us
        new_d.putExtra("already_created",ac);
        new_d.putExtra("username",user);
        new_d.putExtra("diet_num",diet_num);


        startActivity(new_d);

    }

    public void onBackPressed() {
        String user = getIntent().getStringExtra("username");
        int diet_num = getIntent().getIntExtra("diet_num",0);

        //if we are making a new goal, delete it since it was not finished
        if(!(getIntent().getBooleanExtra("already_created",false))) {
            SharedPreferences sp = getSharedPreferences("user_details", Context.MODE_PRIVATE);
            Gson userGson = new Gson();
            UserDetails currUser = userGson.fromJson(sp.getString(user, ""), UserDetails.class);
            currUser.diet_goals.remove(diet_num);
            SharedPreferences.Editor spEditor = sp.edit();
            String json = userGson.toJson(currUser);
            spEditor.putString(user, json);
            spEditor.commit();
        }


        Intent intent = new Intent(this,Diet.class);
        intent.putExtra("username",user);
        startActivity(intent);
    }

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

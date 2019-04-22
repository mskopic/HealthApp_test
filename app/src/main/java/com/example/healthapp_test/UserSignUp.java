package com.example.healthapp_test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.HashMap;

public class UserSignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign In Details");


    }

    public void saveInfo(View view) {
        EditText name = (EditText)findViewById(R.id.name);
        EditText dob = findViewById(R.id.dobcorr);
        String nameOf = name.getText().toString();
        String dobOf = dob.getText().toString();
        RadioGroup gender = findViewById(R.id.gender);
        RadioGroup weight = findViewById(R.id.weight);
        if(nameOf.isEmpty()||dobOf.isEmpty()||gender.getCheckedRadioButtonId()==-1
                ||weight.getCheckedRadioButtonId()==-1){
            TextView error = findViewById(R.id.error02);
            error.setText("Invalid! Please Enter All Fields!");
            error.setTextColor(Color.RED);
        }else{
            RadioButton selectedGender = findViewById(gender.getCheckedRadioButtonId());
            RadioButton selectedWeight = findViewById(weight.getCheckedRadioButtonId());
            String selGender = selectedGender.getText().toString();
            String selWeight = selectedWeight.getText().toString();
            Intent prev = getIntent();
            String user = prev.getStringExtra("username");
            String pass = prev.getStringExtra("password");
            UserDetails newUser = new UserDetails(user, pass, nameOf, dobOf, selGender,selWeight);
            SharedPreferences spUser = getSharedPreferences("user_details", Context.MODE_PRIVATE);
            Gson gson = new Gson();
            SharedPreferences.Editor userEdit = spUser.edit();
            userEdit.putString(user, gson.toJson(newUser));
            userEdit.commit();
            Intent newIntent = new Intent(this, TabsActivity.class);
            newIntent.putExtra("username",user);
            startActivity(newIntent);
        }




    }
}

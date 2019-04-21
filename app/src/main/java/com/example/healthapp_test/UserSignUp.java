package com.example.healthapp_test;

import android.content.Intent;
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

public class UserSignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    public void saveInfo(View view) {
        EditText name = (EditText)findViewById(R.id.name);
        EditText dob = findViewById(R.id.dobcorr);
        String nameOf = name.getText().toString();
        String dobOf = dob.getText().toString();
        RadioGroup gender = findViewById(R.id.gender);
        RadioGroup weight = findViewById(R.id.weight);
        RadioButton selectedGender = findViewById(gender.getCheckedRadioButtonId());
        RadioButton selectedWeight = findViewById(weight.getCheckedRadioButtonId());
        String selGender = selectedGender.getText().toString();
        String selWeight = selectedGender.getText().toString();
        Intent prev = getIntent();
        String user = prev.getStringExtra("username");
        String pass = prev.getStringExtra("password");
        UserDetails newUser = new UserDetails(user, pass, nameOf, dobOf, selGender,selWeight);
        Intent newIntent = new Intent(this, TabsActivity.class);
        startActivity(newIntent);


    }
}

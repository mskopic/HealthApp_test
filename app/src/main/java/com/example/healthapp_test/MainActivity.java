package com.example.healthapp_test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import com.example.healthapp_test.UserDetails;

public class MainActivity extends AppCompatActivity {

    public HashMap<String, String> dummyCredentials = new HashMap(){
        {
            put("u", "p");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the  activity_main.xml layout file
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Welcome to Health App!");

        SharedPreferences user = getSharedPreferences("user_details",Context.MODE_PRIVATE);
        SharedPreferences.Editor userEdit = user.edit();
        Gson gson = new Gson();
        String s = user.getString("credentials", "");
        if(s.equals("null")){
            userEdit.putString("credentials",gson.toJson(dummyCredentials));
            userEdit.commit();
        }

    }

    public void login(View v){
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);

        String u = username.getText().toString();
        String p = password.getText().toString();

        if(u.isEmpty()||p.isEmpty()){
            TextView error = findViewById(R.id.error);
            error.setText("Invalid Login. Please Enter All Fields!");
            error.setTextColor(Color.RED);

        }else if(!dummyCredentials.containsKey(u)){
            TextView error = findViewById(R.id.error);
            error.setText("Invalid Login. Username not found!");
            error.setTextColor(Color.RED);

        }else if(!dummyCredentials.get(u).equals(p)){
            TextView error = findViewById(R.id.error);
            error.setText("Invalid Login. Password entered not associated with Username!");
            error.setTextColor(Color.RED);

        } else{
            Intent newIntent = new Intent(this,TabsActivity.class);
            newIntent.putExtra("username", u.trim());
            startActivity(newIntent);
        }

    }

    public void signup(View view) {
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);

        String u = username.getText().toString();
        String p = password.getText().toString();

        if(u.isEmpty()||p.isEmpty()){
            TextView error = findViewById(R.id.error);
            error.setText("Invalid Sign-up. Please Enter All Fields!");
            error.setTextColor(Color.RED);
        }else if(dummyCredentials.containsKey(u)){
            TextView error = findViewById(R.id.error);
            error.setText("Invalid Sign-up. Username exists already!");
            error.setTextColor(Color.RED);

        }else{
            Intent newIntent = new Intent(this,UserSignUp.class);
            newIntent.putExtra("username", u.trim());
            newIntent.putExtra("password", p.trim());
            startActivity(newIntent);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

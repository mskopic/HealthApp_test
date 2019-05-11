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
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.example.healthapp_test.UserDetails;

public class MainActivity extends AppCompatActivity {

    public Map<String, String> dummyCredentials = new HashMap(){
        {
            put("test", "testPassword");
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
        String s = user.getString("credentials", "null");
        if(s.equals("null")){
            JsonObject trying = new JsonObject();
            Set<Map.Entry<String, String>> cred = dummyCredentials.entrySet();
            Iterator creds = cred.iterator();
            while(creds.hasNext()){
                Map.Entry<String, String> currCreds = (Map.Entry<String, String>)creds.next();
                trying.addProperty(currCreds.getKey(),currCreds.getValue());
            }
            userEdit.putString("credentials",gson.toJson(trying));
            userEdit.commit();
        }

    }

    public void login(View v){
        SharedPreferences user = getSharedPreferences("user_details",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        HashMap<String, String> creds = gson.fromJson(user.getString("credentials", ""),HashMap.class);
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);

        String u = username.getText().toString();
        String p = password.getText().toString();

        if(u.isEmpty()||p.isEmpty()){
            Context context = getApplicationContext();
            CharSequence text = "Invalid Login. Please Enter All Fields!";
            Toast myToast = Toast.makeText(context,text, Toast.LENGTH_SHORT);
            myToast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 200);
            myToast.show();

        }else if(!creds.containsKey(u)){
            Context context = getApplicationContext();
            CharSequence text = "Invalid Login. Username not found!";
            Toast myToast = Toast.makeText(context,text, Toast.LENGTH_SHORT);
            myToast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 200);
            myToast.show();

        }else if(!creds.get(u).equals(p)){
            TextView error = findViewById(R.id.error);
            error.setText("Invalid Login. Password entered not associated with Username!");
            error.setTextColor(Color.RED);

        } else{
            if(user.getString(u.trim(),"null").equals("null")){
                UserDetails newUser = new UserDetails(u.trim(),p.trim(),
                        "TestPersona", "01/01/1997", "Male", "No Preference");
                SharedPreferences.Editor userEdit = user.edit();
                userEdit.putString(u.trim(), gson.toJson(newUser));
                userEdit.commit();



            }
            Intent newIntent = new Intent(this,TabsActivity.class);
            newIntent.putExtra("username", u.trim());
            startActivity(newIntent);
        }

    }

    public void signup(View view) {
            Intent newIntent = new Intent(this,UserSignUp.class);
            startActivity(newIntent);

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

package com.example.healthapp_test;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
        SharedPreferences user = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        HashMap<String, String> creds = gson.fromJson(user.getString("credentials", ""), HashMap.class);

        EditText username = findViewById(R.id.username2);
        EditText password = findViewById(R.id.password2);

        String u = username.getText().toString();
        String p = password.getText().toString();

        EditText name = (EditText) findViewById(R.id.name);
        EditText dob = findViewById(R.id.dobcorr);
        String nameOf = name.getText().toString();
        DateFormat formatter = new SimpleDateFormat("MM-dd-yy");
        String dobOf = dob.getText().toString();
        try {
            dobOf = formatter.parse(dob.getText().toString()).toString();
        } catch (ParseException e) {
            dobOf = "";
        }

        RadioGroup gender = findViewById(R.id.gender);
        RadioGroup weight = findViewById(R.id.weight);
        if (u.isEmpty() || p.isEmpty()||nameOf.isEmpty() || creds.containsKey(u)|| dobOf.isEmpty()
                || dobOf.equals("") || gender.getCheckedRadioButtonId() == -1
                || weight.getCheckedRadioButtonId() == -1) {
            String text = "Invalid! Please fix the following:\n";
            if (u.isEmpty()) {
                text += "Enter Username\n";
            }else if(creds.containsKey(u)){
                text += "Username exists already\n";
            }
            if (p.isEmpty()) {
                text += "Enter Password\n";
            }
            if (nameOf.isEmpty()) {
                text += "Enter Name\n";
            }
            if (dobOf.isEmpty() || dobOf.equals("")) {
                text += "Use \"Set DOB\" or use \"mm-dd-yy\" format\n";
            }
            if (gender.getCheckedRadioButtonId() == -1) {
                text += "Select a Gender\n";
            }
            if (weight.getCheckedRadioButtonId() == -1) {
                text += "Select a Weight Preference\n";
            }
            Context context = getApplicationContext();
            Toast myToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
            myToast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 175);
            myToast.show();

        } else {
            RadioButton selectedGender = findViewById(gender.getCheckedRadioButtonId());
            RadioButton selectedWeight = findViewById(weight.getCheckedRadioButtonId());
            String selGender = selectedGender.getText().toString();
            String selWeight = selectedWeight.getText().toString();
            Intent prev = getIntent();
            SharedPreferences.Editor userEdit = user.edit();
            creds.put(u.trim(), p.trim());
            JsonObject trying = new JsonObject();
            Set<Map.Entry<String, String>> cred = creds.entrySet();
            Iterator credPut = cred.iterator();
            while (credPut.hasNext()) {
                Map.Entry<String, String> currCreds = (Map.Entry<String, String>) credPut.next();
                trying.addProperty(currCreds.getKey(), currCreds.getValue());
            }
            userEdit.putString("credentials", gson.toJson(trying));
            userEdit.commit();
            UserDetails newUser = new UserDetails(u.trim(), p.trim(), nameOf, dobOf, selGender, selWeight);
            SharedPreferences spUser = getSharedPreferences("user_details", Context.MODE_PRIVATE);
            userEdit.putString(u.trim(), gson.toJson(newUser));
            userEdit.commit();
            Intent newIntent = new Intent(this, TabsActivity.class);
            newIntent.putExtra("username", u.trim());
            startActivity(newIntent);
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        Integer year = null;
        Integer month = null;
        Integer day = null;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            if(year!=null){
                c.set(Calendar.YEAR,year);
            }
            if(month!=null){
                c.set(Calendar.MONTH,month);
            }
            if(day!=null){
                c.set(Calendar.DAY_OF_MONTH,month);
            }
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            this.year = year;
            this.month = month;
            this.day = day;



            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            System.out.println(day);
            Date newDate = new GregorianCalendar(year,month,day).getTime();
            System.out.println(newDate);
            DateFormat formatter = new SimpleDateFormat("MM-dd-yy");
            String formattedDate = formatter.format(newDate);
            System.out.println(formattedDate);
            EditText dob = getActivity().findViewById(R.id.dobcorr);
            dob.setText(formattedDate);
        }
    }


}

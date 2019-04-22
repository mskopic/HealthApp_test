package com.example.healthapp_test;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {
    Button detail_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(getIntent().hasExtra("com.example.healthapp_test_DETAILS")){
            TextView tv = (TextView) findViewById(R.id.task_name);
            String text = getIntent().getExtras().getString("com.example.healthapp_test_DETAILS");
            tv.setText(text);

            // fill in details depending on which task user chose (HARDCODED)
            TextView task_description;
            TextView task_type;
            if(text.trim().equals("Lunch")){
                task_type = (TextView) findViewById(R.id.task_type);
                String textTask = "Diet Plan: Keto";
                task_type.setText(textTask);

                task_description = (TextView) findViewById(R.id.task_description);
                String textDescription = "Diet plan that you have selected. Check the Goals tab to make changes.";
                task_description.setText(textDescription);

            } else if(text.trim().equals("Exercise")){
                task_type = (TextView) findViewById(R.id.task_type);
                String textTask = "Exercise Plan: Yoga";
                task_type.setText(textTask);

                task_description = (TextView) findViewById(R.id.task_description);
                String textDescription = "Yoga plan that you have selected. Check the Goals tab to make changes.";
                task_description.setText(textDescription);

            } else if(text.trim().equals("Dinner")){
                task_type = (TextView) findViewById(R.id.task_type);
                String textTask = "Diet Plan: Keto";
                task_type.setText(textTask);

                task_description = (TextView) findViewById(R.id.task_description);
                String textDescription = "Diet plan that you have selected. Check the Goals tab to make changes.";
                task_description.setText(textDescription);

            } else if(text.trim().equals("Sleep")){
                task_type = (TextView) findViewById(R.id.task_type);
                String textTask = "Sleep Plan: 8 hours";
                task_type.setText(textTask);
                // maybe set up an alarm

                task_description = (TextView) findViewById(R.id.task_description);
                String textDescription = "Sleep plan that you have selected. Check the Goals tab to make changes.";
                task_description.setText(textDescription);
            }
        }
        detail_submit = (Button) findViewById(R.id.detail_submit);
        detail_submit.setVisibility(View.VISIBLE);
        detail_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

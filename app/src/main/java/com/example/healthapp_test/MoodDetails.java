package com.example.healthapp_test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class MoodDetails extends AppCompatActivity {
    RatingBar rb;
    TextView value;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_details);

        if(getIntent().hasExtra("com.example.healthapp_test_DETAILS")){
            TextView tv = (TextView) findViewById(R.id.task_name);
            String text = getIntent().getExtras().getString("com.example.healthapp_test_DETAILS");
            tv.setText(text);

            // fill in details depending on which task user chose (HARDCODED)
            TextView task_description;
            TextView task_type;

            task_type = (TextView) findViewById(R.id.task_type);
            String textTask = "How was your day?";
            task_type.setText(textTask);
            // maybe have scroll bar or radio button/Likert Scale

            task_description = (TextView) findViewById(R.id.task_description);
            String textDescription = "Check in to see what your overall mood was for today. " +
                    "Note that if the user has not rated for the day, the rating will be set " +
                    "to 3 out of 5 by default (Neutral Mood).";
            task_description.setText(textDescription);
        }
        rb = (RatingBar) findViewById(R.id.ratingBar);
        value = (TextView) findViewById(R.id.value);
        submit = (Button) findViewById(R.id.rating_submit);

        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating == 1) {
                    value.setText("Very Bad");
                }else if(rating == 2) {
                    value.setText("Bad");
                }else if(rating == 3) {
                    value.setText("Neutral");
                }else if(rating == 4){
                    value.setText("Good");
                }else if(rating == 5) {
                    value.setText("Very Good");
                }
                submit.setVisibility(View.VISIBLE);
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startIntent.putExtra("com.example.healthapp_test_MOOD_RATING", "COMPLETE");
                        startActivity(startIntent);
                    }
                });
            }
        });
    }
}

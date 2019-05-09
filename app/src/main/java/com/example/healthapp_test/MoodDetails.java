package com.example.healthapp_test;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class MoodDetails extends AppCompatActivity {
    RatingBar rb;
    TextView value;
    Button submit;
    EditText editText;
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
            String textDescription = "Check in to see what your overall mood was for today. \n\n" +
                    "You can select between one star (Very Bad) to five stars (Very Good). " +
                    "Type in the optional comment box below to elaborate more on why you selected " +
                    "that particular rating. Press the submit button after completing the form";
            task_description.setText(textDescription);
        }
        rb = (RatingBar) findViewById(R.id.ratingBar);
        value = (TextView) findViewById(R.id.value);
        submit = (Button) findViewById(R.id.rating_submit);
        editText = (EditText) findViewById(R.id.editText);

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
                        Toast.makeText(getApplicationContext(),"Rating Submitted", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        editText.setLines(6);
        editText.setHorizontallyScrolling(true);
        editText.setSingleLine();

        editText.setOnEditorActionListener(
                new android.widget.TextView.OnEditorActionListener()
                {
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
                    {
                        InputMethodManager imm = (InputMethodManager)getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                        return false;
                    }
                }


        );
    }
}

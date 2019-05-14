package com.example.healthapp_test;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
//added 5/11
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.GridLayout.LayoutParams;
import android.widget.ImageButton;
import android.app.Dialog;

import java.util.ArrayList;





/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TrendsTab.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TrendsTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrendsTab extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RadioGroup buttons;
    private RadioButton weekly_but;
    private RadioButton monthly_but;
    private RadioButton all_time_but;
    //added 5/11
    private Button help_butt;
    private PopupWindow pop_help;
    private Context this_context;
    private TextView tips;

    //final private ImageView imageView;

    public Spinner weekly_graphs;
    private Spinner monthly_graphs;
    private Spinner all_time_graphs;

    private OnFragmentInteractionListener mListener;

    public TrendsTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrendsTab.
     */
    // TODO: Rename and change types and number of parameters
    public static TrendsTab newInstance(String param1, String param2) {
        TrendsTab fragment = new TrendsTab();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this_context = this.getContext();


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }
    public void setTip(CharSequence s) {
        //Toast.makeText((Context)getContext(), s).show();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_trends_tab, container, false);
        final View view_p = inflater.inflate(R.layout.trends_popup, container, false);
        final ArrayList<String> chartNames = new ArrayList<>();
        chartNames.add("Mood Over Time");
        chartNames.add("Sleep Goal Over Time");
        chartNames.add("Effect of Calories on Mood");

        buttons = view.findViewById(R.id.timeButtons);
        buttons.check(R.id.week_trend_but);
        //Main Radio Buttons (outside of slider section)
        weekly_but = view.findViewById(R.id.week_trend_but);
        monthly_but = view.findViewById(R.id.month_trend_but);
        all_time_but = view.findViewById(R.id.all_trend_but);
        //added 5/11
        tips = view.findViewById(R.id.graph_tips);
        help_butt = view.findViewById(R.id.trends_tips);
        help_butt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final Dialog dialog = new Dialog(this_context);
                dialog.setContentView(R.layout.trends_popup);
                ImageButton closeButton = (ImageButton) view_p.findViewById(R.id.ib_close);

                // Set a click listener for the popup window close button
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Dismiss the popup window
                        dialog.dismiss();
                    }
                });

                dialog.show();
                /*//Toast.makeText(this_context, "Help:\n").show();
                pop_help = new PopupWindow(
                        view_p,
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT
                );

                ImageButton closeButton = (ImageButton) view_p.findViewById(R.id.ib_close);

                // Set a click listener for the popup window close button
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Dismiss the popup window
                        pop_help.dismiss();
                    }
                });*/
            }
        });

        final ImageView imageView = (ImageView) view.findViewById(R.id.week_circles);
        final ImageView graphView = (ImageView) view.findViewById(R.id.week_mood_time_graph);
        int time_frame = 0; //0 is week, 1 is month, 2 is alltime
        weekly_but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                imageView.setImageResource(R.drawable.week_one_balls);
                String text = weekly_graphs.getSelectedItem().toString();
                if (text == chartNames.get(0)) {
                    graphView.setImageResource(R.drawable.week_mood_time);
                    tips.setText("Tips:\nYour mood has been above average (3) 100% of days this week.");
                }
                if (text == chartNames.get(1)) {
                    graphView.setImageResource(R.drawable.week_sleep_time);
                }
                if (text == chartNames.get(2)) {
                    graphView.setImageResource(R.drawable.week_calories_mood);
                }
            }
        });
        monthly_but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                imageView.setImageResource(R.drawable.month_balls);
                String text = weekly_graphs.getSelectedItem().toString();
                if (text == chartNames.get(0)) {
                    graphView.setImageResource(R.drawable.month_mood_time);
                    tips.setText("Tips:\nYour mood has been above average (3) 70% of days this month.");
                }
                if (text == chartNames.get(1)) {
                    graphView.setImageResource(R.drawable.month_sleep_time);
                }
                if (text == chartNames.get(2)) {
                    graphView.setImageResource(R.drawable.month_calories_mood);
                }
            }
        });
        all_time_but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                imageView.setImageResource(R.drawable.not_implemented);
                String text = weekly_graphs.getSelectedItem().toString();
                if (text == chartNames.get(0)) {
                    graphView.setImageResource(R.drawable.all_mood_time);
                    tips.setText("Tips:\nYour mood has been above average (3) 71% of days since creating your health goals.");
                }
                if (text == chartNames.get(1)) {
                    graphView.setImageResource(R.drawable.all_sleep_time);
                }
                if (text == chartNames.get(2)) {
                    graphView.setImageResource(R.drawable.all_calories_mood);
                }
            }
        });


        //Spinner with graph options:
        weekly_graphs = view.findViewById(R.id.trend_graph_options);
        ArrayAdapter<String> adapter = new ArrayAdapter((Context)getContext(),
                android.R.layout.simple_spinner_item, chartNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //weekly_graphs.setOnItemSelectedListener(this);
        if (weekly_graphs != null) {
            weekly_graphs.setAdapter(adapter);
        }

        weekly_graphs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                switch(position) {
                    case 0:
                        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                        if (weekly_but.isChecked()) {
                            graphView.setImageResource(R.drawable.week_mood_time);
                            tips.setText("Tips:\nYour mood has been above average (3) 100% of days this week.");
                        } if (monthly_but.isChecked()) {
                            graphView.setImageResource(R.drawable.all_mood_time);
                            tips.setText("Tips:\nYour mood has been above average (3) 70% of days this month.");
                        } if (all_time_but.isChecked()) {
                            graphView.setImageResource(R.drawable.month_mood_time);
                            tips.setText("Tips:\nYour mood has been above average (3) 71% of days since creating your health goals.");
                        }
                        break;
                    case 1:
                        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                        if (weekly_but.isChecked()) {
                            graphView.setImageResource(R.drawable.week_sleep_time);
                        } if (monthly_but.isChecked()) {
                            graphView.setImageResource(R.drawable.month_sleep_time);
                        } if (all_time_but.isChecked()) {
                            graphView.setImageResource(R.drawable.all_sleep_time);
                        }
                        tips.setText("Tips:\nMeeting all sleep goals could help complete other health goals. Consistent sleep is show to improve many areas of health!");
                        break;
                    case 2:
                        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                        if (weekly_but.isChecked()) {
                            graphView.setImageResource(R.drawable.week_calories_mood);
                        } if (monthly_but.isChecked()) {
                            graphView.setImageResource(R.drawable.month_calories_mood);
                        } if (all_time_but.isChecked()) {
                            graphView.setImageResource(R.drawable.all_calories_mood);
                        }
                        tips.setText("Tips:\nMood increases with the number of calories consumed. Reaching calorie goals may lead to better mood.");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });



        return view;
    }

/*
        weekly_graphs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // Do your stuff at item selection time.
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // Intentionally kept blank
        }
    });
    */
    // Spinner click listener


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            return;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        //0 is Mood Over Time
        //1 is Sleep Goal Over Time
        //2 is Effect of Diet on Mood
        String item = parent.getItemAtPosition(position).toString();
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.week_trend_but:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.month_trend_but:
                if (checked)
                    // Ninjas rule
                    break;
            case R.id.all_trend_but:
                if (checked)
                    // Ninjas rule
                    break;
        }
        switch(position) {
            case 0:
                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            case 1:
                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            case 2:
                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            case 3:
                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void weeklyClicked() {

    }

    private void monthlyClicked() {

    }

    private void allTimeClicked() {

    }

    public void onTrendButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.week_trend_but:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.month_trend_but:
                if (checked)
                    // Ninjas rule
                    break;
            case R.id.all_trend_but:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }
}


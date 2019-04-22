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
import android.widget.Toast;

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

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_trends_tab, container, false);

        buttons = view.findViewById(R.id.timeButtons);
        buttons.check(R.id.week_trend_but);
        //Main Radio Buttons (outside of slider section)
        weekly_but = view.findViewById(R.id.week_trend_but);
        monthly_but = view.findViewById(R.id.month_trend_but);
        all_time_but = view.findViewById(R.id.all_trend_but);
        weekly_but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
        monthly_but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
        all_time_but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });

        ArrayList<String> chartNames = new ArrayList<>();
        chartNames.add("Mood Over Time");
        chartNames.add("Sleep Goal Over Time");
        chartNames.add("Effect of Diet on Mood");
        chartNames.add("Effect of Mood on Diet");

        //Code for switching between Weekly, Monthly, and All_Time data


       /* weekly_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weeklyClicked();
            }
        });

        monthly_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthlyClicked();
            }
        });

        all_time_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allTimeClicked();
            }
        }); */

        //Spinner with graph options:
        weekly_graphs = view.findViewById(R.id.trend_graph_options);
        ArrayAdapter<String> adapter = new ArrayAdapter((Context)getContext(),
                android.R.layout.simple_spinner_item, chartNames);
        /*ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource((Context)getContext(),
                R.array.weekly_graph_array, android.R.layout.simple_spinner_item); */
        //ArrayAdapter<String> adapter1 = new ArrayAdapter<>()

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
        //3 is Effect of Mood on Diet
        String item = parent.getItemAtPosition(position).toString();
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


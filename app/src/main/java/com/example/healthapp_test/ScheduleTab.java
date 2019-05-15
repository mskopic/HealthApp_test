package com.example.healthapp_test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScheduleTab.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScheduleTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleTab extends Fragment {

    View v;
    private RecyclerView myRecyclerView;
    private RecyclerView myRecyclerView1;
    private List<Schedule> firstContact;
    private List<Schedule> secondContact;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Intent prevIntent;
    public ScheduleTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleTab.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleTab newInstance(String param1, String param2) {
        ScheduleTab fragment = new ScheduleTab();
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
        final String user = getActivity().getIntent().getStringExtra("username");
        Gson userGson = new Gson();
        SharedPreferences sp = getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);
        UserDetails currUser = userGson.fromJson(sp.getString(user,""), UserDetails.class);
        firstContact = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK)-1;
        DateFormat dateFormat = new SimpleDateFormat("h:mm a");
        firstContact = new ArrayList<>();
        for(Exercise_Goal i: currUser.ex_goals){
            if(i.getDays().contains(day)){
                firstContact.add(new Schedule(i.getEx_name(),dateFormat.format(i.getTime()),R.drawable.exercise));
            }
        }
        for(Diet_Goal i: currUser.diet_goals){
            for(Meal_Goal j: i.meals){
                if(j.getDays().contains(day)){
                    firstContact.add(new Schedule(j.getName(),dateFormat.format(j.getTime()),R.drawable.diet));

                }
            }
        }
        for(Meditation_Goal i: currUser.med_goals){
            if(i.getDays().contains(day)){
                firstContact.add(new Schedule(i.getMed_name(),dateFormat.format(i.getTime()),R.drawable.meditate));
            }
        }


        for(Sleep_Goal i: currUser.sleep_goals){
            if(i.getDays().contains(day)){
                firstContact.add(new Schedule("Sleep",dateFormat.format(i.getAlarm())+ " Alarm",R.drawable.sleep));
            }
        }
        int hour = currUser.moodHr;
        int min = currUser.moodMin;

        if(hour!=100&&min!=100){
            firstContact.add(new Schedule("Mood",dateFormat.format(new Time(hour, min, 0)), R.drawable.mood));
        }

        Collections.sort(firstContact, new ScheduleComparator());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_schedule_tab, container, false);
        myRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        RecyclerViewAdapter recyclerAdapter = new RecyclerViewAdapter(getContext(), firstContact);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(recyclerAdapter);

        prevIntent = getActivity().getIntent();
        String user = prevIntent.getStringExtra("med_to_sched");


        return v;
    }

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

    static class ScheduleComparator implements Comparator<Schedule>
    {
        public int compare(Schedule i, Schedule j)
        {
            return i.getPhone().compareTo(j.getPhone());
        }
    }
}

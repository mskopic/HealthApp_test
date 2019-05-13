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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;


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
        Intent prevIntent = getActivity().getIntent();
        String user = prevIntent.getStringExtra("username");
        Gson gson = new Gson();
        SharedPreferences sp = this.getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sp.edit();
        UserDetails currUser = gson.fromJson(sp.getString(user,""), UserDetails.class);

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        firstContact = new ArrayList<>();
        firstContact.add(new Schedule("Lunch", "2:00pm", R.drawable.diet));
        if(currUser.ex_goals.size() == 0) {
            firstContact.add(new Schedule("Exercise", "4:00pm", R.drawable.exercise));
        }
        else{
            Exercise_Goal ex1 = currUser.ex_goals.get(0);
            int hours = ex1.getTime().getHours();
            int minutes = ex1.getTime().getMinutes();

            String time;
            if(hours > 12){
                hours = hours - 12;
                if(minutes == 0) {
                    time = hours + ":" + minutes + "0"+"pm";
                }
                else{
                    time = hours + ":" + minutes +"pm";
                }
            }
            else{
                time = hours+":"+minutes+"am";
                if(minutes == 0) {
                    time = hours + ":" + minutes + "0"+"am";
                }
                else{
                    time = hours + ":" + minutes +"am";
                }
            }
            String name = ex1.getEx_name();
            firstContact.add(new Schedule(name, time, R.drawable.exercise));
        }
        firstContact.add(new Schedule("Dinner", "7:00pm", R.drawable.diet));
        firstContact.add(new Schedule("Mood", "9:00pm", R.drawable.mood));
        firstContact.add(new Schedule("Sleep", "11:00pm", R.drawable.sleep));

        secondContact = new ArrayList<>();
        secondContact.add(new Schedule("Meditate", "9:00am", R.drawable.meditate));

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

        myRecyclerView1 = (RecyclerView) v.findViewById(R.id.recyclerView1);
        RecyclerViewAdapter recyclerAdapter1 = new RecyclerViewAdapter(getContext(), secondContact);
        myRecyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView1.setAdapter(recyclerAdapter1);

        prevIntent = getActivity().getIntent();
        String user = prevIntent.getStringExtra("med_to_sched");

        if(user != null) {
            if (user.trim().equals("med_to_sched") || user.trim().equals("true")) {
                myRecyclerView1.setVisibility(View.VISIBLE);
                View future_view = (View) v.findViewById(R.id.future_view);
                future_view.setVisibility(View.VISIBLE);
                TextView future_text_view = (TextView) v.findViewById(R.id.future_textview);
                future_text_view.setVisibility(View.VISIBLE);
            }
        }
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
}

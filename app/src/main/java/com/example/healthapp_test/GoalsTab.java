package com.example.healthapp_test;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GoalsTab.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GoalsTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoalsTab extends Fragment implements MyRecyclerViewAdapter.ItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    MyRecyclerViewAdapter adapter;

    public GoalsTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GoalsTab.
     */
    // TODO: Rename and change types and number of parameters
    public static GoalsTab newInstance(String param1, String param2) {
        GoalsTab fragment = new GoalsTab();
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

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_goals_tab, container, false);

        // data to populate the RecyclerView with
        ArrayList<String> goalNames = new ArrayList<>();
        goalNames.add("Diet");
        goalNames.add("Exercise");
        goalNames.add("Sleep");
        goalNames.add("Mood");
        goalNames.add("Meditation");

        // set up the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.my_goals);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(lm);
        adapter = new MyRecyclerViewAdapter(getActivity(), goalNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        getActivity().setTitle("Goals");
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), lm.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        return view;
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

    public void onItemClick(View view, int position){
        switch (position){
            case 0:
                Intent diet = new Intent(getActivity(),Diet.class);
                startActivity(diet);
                break;
            case 1:
                Intent ex = new Intent(getActivity(),Exercise.class);
                startActivity(ex);
                break;
            case 2:
                Intent sleep = new Intent(getActivity(),Sleep.class);
                startActivity(sleep);
                break;
            case 3:
                Intent mood = new Intent(getActivity(),Mood.class);
                startActivity(mood);
                break;
            case 4:
                Intent med = new Intent(getActivity(),Meditation.class);
                startActivity(med);
                break;

        }

    }





}

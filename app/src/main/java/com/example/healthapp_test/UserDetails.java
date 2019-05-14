package com.example.healthapp_test;

import java.util.ArrayList;

public class UserDetails {
    String username;
    String password;
    String name;
    String dob;
    String gender;
    String weight;
    ArrayList<Diet_Goal> diet_goals;
    ArrayList<String> diet_goals_names;
    ArrayList<Exercise_Goal> ex_goals;
    ArrayList<String> ex_goals_names;
    ArrayList<Meditation_Goal> med_goals;
    ArrayList<String> med_goals_names;
    ArrayList<Sleep_Goal> sleep_goals;
    ArrayList<String> sleep_goals_names;
    int moodHr=100;
    int moodMin=100;


    public UserDetails(String username, String password, String name, String dob, String gender, String weight){
        this.username = username;
        this.password = password;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.weight = weight;
        this.diet_goals = new ArrayList<Diet_Goal>();
        this.diet_goals_names = new ArrayList<String>();
        this.ex_goals = new ArrayList<Exercise_Goal>();
        this.ex_goals_names = new ArrayList<String>();
        this.med_goals = new ArrayList<Meditation_Goal>();
        this.med_goals_names = new ArrayList<String>();
        this.sleep_goals = new ArrayList<Sleep_Goal>();
        this.sleep_goals_names = new ArrayList<String>();

    }

    public UserDetails getCurr(String username){
        return this;
    }
}

package com.example.healthapp_test;

import java.sql.Time;
import java.util.ArrayList;

public class Meditation_Goal {
    public String med_name;
    public ArrayList<Integer> days = new ArrayList<Integer>();
    public int med_type;
    public Time time;

    public String getMed_name() {
        return med_name;
    }

    public void setMed_name(String med_name) {
        this.med_name = med_name;
    }

    public int getMed_type() {
        return med_type;
    }

    public void setMed_type(int med_type) {
        this.med_type = med_type;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public ArrayList<Integer> getDays() {
        return days;
    }

    public void setDays(ArrayList<Integer> days) {
        this.days = days;
    }
}

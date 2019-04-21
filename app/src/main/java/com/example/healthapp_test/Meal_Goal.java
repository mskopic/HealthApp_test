package com.example.healthapp_test;

import java.sql.Time;
import java.util.ArrayList;

public class Meal_Goal {
    public int low;
    public int high;
    public String name;
    public String diet;
    public ArrayList<Integer> days = new ArrayList<Integer>();
    private Time time;
    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
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

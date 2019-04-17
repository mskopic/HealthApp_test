package com.example.healthapp_test;

import java.sql.Time;
import java.util.ArrayList;

public class Sleep_Goal {
    private int hours_sleep;
    private Time alarm;
    public ArrayList<Integer> days = new ArrayList<Integer>();

    public int getHours_sleep() {
        return hours_sleep;
    }

    public void setHours_sleep(int hours_sleep) {
        this.hours_sleep = hours_sleep;
    }

    public Time getAlarm() {
        return alarm;
    }

    public void setAlarm(Time alarm) {
        this.alarm = alarm;
    }

    public ArrayList<Integer> getDays() {
        return days;
    }

    public void setDays(ArrayList<Integer> days) {
        this.days = days;
    }
}

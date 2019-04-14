package com.example.healthapp_test;

import java.sql.Time;
import java.util.ArrayList;

public class Exercise_Goal {

    private String ex_name;
    private String ex_type;
    private int ex_intensity;

    public ArrayList<String> days;
    private Time time;

    public String getEx_name() {
        return ex_name;
    }

    public void setEx_name(String ex_name) {
        this.ex_name = ex_name;
    }

    public String getEx_type() {
        return ex_type;
    }

    public void setEx_type(String ex_type) {
        this.ex_type = ex_type;
    }

    public int getEx_intensity() {
        return ex_intensity;
    }

    public void setEx_intensity(int ex_intensity) {
        this.ex_intensity = ex_intensity;
    }


    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }



}

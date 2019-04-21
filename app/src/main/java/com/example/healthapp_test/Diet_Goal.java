package com.example.healthapp_test;

import java.util.ArrayList;

public class Diet_Goal {
    private String diet_name;
    private int diet_type;
    private int calories;
    private int carbs;
    private int protein;
    private int fat;
    private double lbs_week;
    public ArrayList<Meal_Goal> meals = new ArrayList<Meal_Goal>();

    public String getDiet_name() {
        return diet_name;
    }

    public void setDiet_name(String diet_name) {
        this.diet_name = diet_name;
    }

    public int getDiet_type() {
        return diet_type;
    }

    public void setDiet_type(int diet_type) {
        this.diet_type = diet_type;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public double getLbs_week() {
        return lbs_week;
    }

    public void setLbs_week(double lbs_week) {
        this.lbs_week = lbs_week;
    }
}

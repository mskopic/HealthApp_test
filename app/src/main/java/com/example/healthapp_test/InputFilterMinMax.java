package com.example.healthapp_test;

import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;

public class InputFilterMinMax implements InputFilter {
    private int min, max;

    public InputFilterMinMax(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public InputFilterMinMax(String min, String max) {
        this.min = Integer.parseInt(min);
        this.max = Integer.parseInt(max);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            Double input = Double.parseDouble(dest.toString() + source.toString());
            if (isInRange(min, max, input))
                return null;
            else {
                Log.i("Filter","outside range");
                return min + "";
            }
        } catch (NumberFormatException nfe) { }
        Log.i("Filter","number exception???");
        return source;
    }

    private boolean isInRange(int a, int b, double c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}

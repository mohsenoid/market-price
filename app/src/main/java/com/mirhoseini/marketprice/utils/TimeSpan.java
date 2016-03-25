package com.mirhoseini.marketprice.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohsen on 24/03/16.
 */
public enum TimeSpan {
    DAY_30(0, "30days"), DAY_60(1, "60days"), DAY_180(2, "180days"), YEAR_1(3, "1year"), YEAR_2(4, "2year");//, ALL(5, "all");

    static ArrayList<String> values;

    int position;
    String value;

    TimeSpan(int position, String value) {
        this.position = position;
        this.value = value;
    }

    public static TimeSpan fromValue(String value) {
        if (value != null) {
            for (TimeSpan timeSpan : TimeSpan.values()) {
                if (value.equalsIgnoreCase(timeSpan.value)) {
                    return timeSpan;
                }
            }
        }
        return null;
    }

    public static TimeSpan fromPosition(int position) {
        for (TimeSpan timeSpan : TimeSpan.values()) {
            if (position == timeSpan.position) {
                return timeSpan;
            }
        }
        return null;
    }

    public static List<String> getValues() {
        if (values == null) {
            values = new ArrayList<>();

            for (TimeSpan timeSpan : TimeSpan.values()) {
                values.add(timeSpan.value);
            }
        }

        return values;
    }

    public String getValue() {
        return value;
    }

    public int getPosition() {
        return position;
    }
}

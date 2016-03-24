package com.mirhoseini.marketprice.utils;

/**
 * Created by Mohsen on 24/03/16.
 */
public enum TimeSpan {
    DAY_30(0, "30days"), DAY_60(1, "60days"), DAY_180(2, "180days"), YEAR_1(3, "1year"), YEAR_2(4, "2year"), ALL(5, "all");

    int position;
    String value;

    TimeSpan(int position, String value) {
        this.position = position;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public int getPosition() {
        return position;
    }
}

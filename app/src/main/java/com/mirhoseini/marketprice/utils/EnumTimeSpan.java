package com.mirhoseini.marketprice.utils;

/**
 * Created by Mohsen on 24/03/16.
 */
public enum EnumTimeSpan {
    DAY_30("30days"), DAY_60("60days"), DAY_180("180days"), YEAR_1("1year"), YEAR_2("2year"), ALL("all");

    String value;

    EnumTimeSpan(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

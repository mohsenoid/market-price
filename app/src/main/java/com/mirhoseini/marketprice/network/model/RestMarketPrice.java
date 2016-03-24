package com.mirhoseini.marketprice.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohsen on 24/03/16.
 */
public class RestMarketPrice {
    @SerializedName("values")
    private RestValues[] values;

    public RestValues[] getValues() {
        return values;
    }

    public void setValues(RestValues[] values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "RestMarketPrice [values = " + values + "]";
    }
}

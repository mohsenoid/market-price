package com.mirhoseini.marketprice.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohsen on 24/03/16.
 */
public class RestValues {
    @SerializedName("y")
    private String y;

    @SerializedName("x")
    private String x;

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return "RestValues [y = " + y + ", x = " + x + "]";
    }
}

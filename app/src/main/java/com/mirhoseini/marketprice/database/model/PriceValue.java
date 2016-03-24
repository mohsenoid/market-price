package com.mirhoseini.marketprice.database.model;

import com.mirhoseini.marketprice.database.DatabaseHelper;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Mohsen on 24/03/16.
 */
@Table(database = DatabaseHelper.class, name = "pricevalue")
public class PriceValue extends BaseModel {
    @PrimaryKey
    private int timeSpanId;
    @PrimaryKey
    private String x;
    @Column
    private String y;


    public PriceValue(int timeSpanId, String x, String y) {
        this.timeSpanId = timeSpanId;
        this.x = x;
        this.y = y;
    }

    public PriceValue() {
    }

    public int getTimeSpanId() {
        return timeSpanId;
    }

    public void setTimeSpanId(int timeSpanId) {
        this.timeSpanId = timeSpanId;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "PriceValue [timeSpanId = " + timeSpanId + ", x = " + x + ", y = " + y + "]";
    }

}

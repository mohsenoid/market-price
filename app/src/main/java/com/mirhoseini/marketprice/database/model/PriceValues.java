package com.mirhoseini.marketprice.database.model;

import com.mirhoseini.marketprice.database.DatabaseHelper;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Mohsen on 24/03/16.
 */
@Table(database = DatabaseHelper.class, name = "pricevalues")
public class PriceValues extends BaseModel {
    @PrimaryKey
    private String x;
    @Column
    private String y;

    public PriceValues(String x, String y) {
        this.y = y;
        this.x = x;
    }

    public PriceValues() {
    }

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
        return "PriceValues [y = " + y + ", x = " + x + "]";
    }

}

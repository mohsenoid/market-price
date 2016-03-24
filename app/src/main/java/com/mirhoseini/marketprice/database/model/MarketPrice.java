package com.mirhoseini.marketprice.database.model;

import com.mirhoseini.marketprice.database.DatabaseHelper;
import com.mirhoseini.marketprice.utils.TimeSpan;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Mohsen on 24/03/16.
 */

@Table(database = DatabaseHelper.class, name = "marketprice")
public class MarketPrice extends BaseModel {
    @PrimaryKey
    private String timeSpan;
    @Column
    private PriceValues[] values;

    public MarketPrice(TimeSpan timeSpan, PriceValues[] values) {
        this.timeSpan = timeSpan.getValue();
        this.values = values;
    }

    public MarketPrice() {
    }

    public String getTimeSpan() {
        return timeSpan;
    }

    public void setTimeSpan(TimeSpan timeSpan) {
        this.timeSpan = timeSpan.getValue();
    }

    public PriceValues[] getValues() {
        return values;
    }

    public void setValues(PriceValues[] values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        MarketPrice marketPrice = (MarketPrice) object;

        return this.timeSpan == marketPrice.getTimeSpan();
    }

    @Override
    public String toString() {
        return "MarketPrice{" +
                "timeSpan=" + timeSpan +
                ", values='" + values + '\'' +
                '}';
    }

}

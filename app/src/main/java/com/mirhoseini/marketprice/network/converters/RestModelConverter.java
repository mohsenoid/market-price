package com.mirhoseini.marketprice.network.converters;

import com.mirhoseini.marketprice.database.model.PriceValue;
import com.mirhoseini.marketprice.network.model.RestPriceValue;
import com.mirhoseini.marketprice.utils.TimeSpan;

/**
 * Created by Mohsen on 24/03/16.
 */
public class RestModelConverter {

    public static PriceValue convertRestModelToPriceValue(TimeSpan timeSpan, RestPriceValue restPriceValue) {
        // convert Rest response model to Database model
        return new PriceValue(timeSpan.getPosition(), Long.parseLong(restPriceValue.getX()), Float.parseFloat(restPriceValue.getY()));
    }
}

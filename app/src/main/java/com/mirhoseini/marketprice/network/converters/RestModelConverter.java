package com.mirhoseini.marketprice.network.converters;

import com.mirhoseini.marketprice.database.model.MarketPrice;
import com.mirhoseini.marketprice.database.model.PriceValues;
import com.mirhoseini.marketprice.network.model.RestMarketPrice;
import com.mirhoseini.marketprice.network.model.RestPriceValues;
import com.mirhoseini.marketprice.utils.TimeSpan;

import java.util.ArrayList;

/**
 * Created by Mohsen on 24/03/16.
 */
public class RestModelConverter {

    public static MarketPrice convertRestModelToMarketPrice(TimeSpan timeSpan, RestMarketPrice restMarketPrice) {
        ArrayList<PriceValues> priceValues = new ArrayList<>();

        for (RestPriceValues restPriceValues : restMarketPrice.getValues()) {
            priceValues.add(convertRestModelToPriceValues(restPriceValues));
        }

        return new MarketPrice(timeSpan, (PriceValues[]) priceValues.toArray());
    }

    public static PriceValues convertRestModelToPriceValues(RestPriceValues restPriceValues) {
        return new PriceValues(restPriceValues.getX(), restPriceValues.getY());
    }
}

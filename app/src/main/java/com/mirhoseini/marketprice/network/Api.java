package com.mirhoseini.marketprice.network;

import com.mirhoseini.marketprice.network.model.RestMarketPrice;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Mohsen on 24/03/16.
 */
public interface Api {
    @GET("market-price")
    Call<RestMarketPrice> getMarketPriceValues(@Query("timespan") String timespan, @Query("format") String format);
}

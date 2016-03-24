package com.mirhoseini.marketprice.network;

import com.mirhoseini.marketprice.network.model.RestValues;
import com.mirhoseini.marketprice.utils.EnumTimeSpan;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Mohsen on 24/03/16.
 */
public interface Api {
    @GET("maket-price")
    Call<RestValues> getMarketPriceValues(@Query("timespan") EnumTimeSpan timespan, @Query("format") String format);
}

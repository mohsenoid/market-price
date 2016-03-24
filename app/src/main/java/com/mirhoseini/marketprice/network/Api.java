package com.mirhoseini.marketprice.network;

import com.mirhoseini.marketprice.network.model.RestValues;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Mohsen on 24/03/16.
 */
public interface Api {
    @GET("maket-price?format=json")
    Call<RestValues> getMarketPriceValues();
}

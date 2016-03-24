package com.mirhoseini.marketprice.network;

import com.mirhoseini.marketprice.BuildConfig;
import com.mirhoseini.marketprice.network.model.RestValues;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mohsen on 24/03/16.
 */
public class NetworkHelper {

    Retrofit mRetrofit;
    Api mApi;

    @Inject
    @Singleton
    public NetworkHelper(String baseUrl) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create());

        //show retrofit logs if app is Debug
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);
            builder.client(httpClient.build());
        }

        mRetrofit = builder.build();

        mApi = mRetrofit.create(Api.class);
    }

    public void loadMarketPriceValues(final OnNetworkFinishedListener<RestValues> listener) {
        Call<RestValues> valuesCall = mApi.getMarketPriceValues();
        valuesCall.enqueue(new Callback<RestValues>() {

            @Override
            public void onResponse(Call<RestValues> call, Response<RestValues> response) {
                if (response.isSuccess()) {
                    if (listener != null) {
                        listener.onSuccess(response.body());
                    }
                } else {
                    if (listener != null)
                        listener.onError(new Exception("Response is not successful!"));
                }
            }

            @Override
            public void onFailure(Call<RestValues> call, Throwable t) {
                if (listener != null)
                    listener.onError(t);
            }
        });
    }
}

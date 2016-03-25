package com.mirhoseini.marketprice.network;

import com.mirhoseini.marketprice.BuildConfig;
import com.mirhoseini.marketprice.network.model.RestMarketPrice;
import com.mirhoseini.marketprice.utils.Constants;
import com.mirhoseini.marketprice.utils.TimeSpan;

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
    static NetworkHelper instance;

    Retrofit mRetrofit;
    Api mApi;

    private NetworkHelper() {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
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

    public static NetworkHelper getInstance() {
        if (instance == null)
            instance = new NetworkHelper();
        return instance;
    }

    public void loadMarketPriceValues(final TimeSpan timeSpan, final OnNetworkFinishedListener<RestMarketPrice> listener) {
        Call<RestMarketPrice> valuesCall = mApi.getMarketPriceValues(timeSpan.getValue(), Constants.API_FORMAT_JSON);
        valuesCall.enqueue(new Callback<RestMarketPrice>() {

            @Override
            public void onResponse(Call<RestMarketPrice> call, Response<RestMarketPrice> response) {
                if (response.isSuccess()) {
                    if (listener != null) {
                        listener.onSuccess(timeSpan, response.body());
                    }
                } else {
                    if (listener != null)
                        listener.onError(timeSpan, new Exception("Response is not successful!"));
                }
            }

            @Override
            public void onFailure(Call<RestMarketPrice> call, Throwable t) {
                if (listener != null)
                    listener.onError(timeSpan, t);
            }
        });
    }
}

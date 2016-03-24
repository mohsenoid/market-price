package com.mirhoseini.marketprice.network.module;

import android.app.Application;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mirhoseini.marketprice.network.NetworkHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mohsen on 24/03/16.
 */
@Module
public class NetworkModule {
    String mBaseUrl;
    boolean mIsDebug;

    // Constructor needs one parameter to instantiate.
    public NetworkModule(String baseUrl, boolean isDebug) {
        this.mBaseUrl = baseUrl;
        this.mIsDebug = isDebug;
    }


    @Provides
    @Singleton
    NetworkHelper provideNetworkHelper() {
        return new NetworkHelper(mBaseUrl);
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache) {
        OkHttpClient client = new OkHttpClient.Builder().cache(cache).build();
        return client;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson));

        //show retrofit logs if app is debug
        if (mIsDebug) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            okHttpClient.interceptors().add(logging);
            retrofitBuilder.client(okHttpClient);
        }

        return retrofitBuilder.build();
    }
}

package com.mirhoseini.marketprice;

import android.app.Application;
import android.content.Context;

import com.raizlabs.android.dbflow.config.FlowManager;

import timber.log.Timber;

/**
 * Created by Mohsen on 24/03/16.
 */
public class AppApplication extends Application {

    private static final String TAG = AppApplication.class.getSimpleName();

    public static AppApplication get(Context context) {
        return (AppApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // init DBFlow ORM database
        FlowManager.init(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

    }

}

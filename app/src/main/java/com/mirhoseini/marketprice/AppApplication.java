package com.mirhoseini.marketprice;

import android.app.Application;
import android.content.Context;

import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by Mohsen on 24/03/16.
 */
public class AppApplication extends Application {

    public static AppApplication get(Context context) {
        return (AppApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // init database
        FlowManager.init(this);

    }

}

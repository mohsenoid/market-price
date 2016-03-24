package com.mirhoseini.marketprice;

import android.app.Application;

import com.mirhoseini.marketprice.ui.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Mohsen on 24/03/16.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(BaseActivity baseActivity);

    Application getApplication();
}

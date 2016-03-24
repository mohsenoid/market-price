package com.mirhoseini.marketprice.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mirhoseini.marketprice.AppApplication;
import com.mirhoseini.marketprice.AppComponent;

/**
 * Created by Mohsen on 12/03/16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActivityComponent(AppApplication.get(this).getAppComponent());

    }

    protected abstract void setupActivityComponent(AppComponent appComponent);
}

package com.mirhoseini.marketprice.ui.main.presenter;

import com.mirhoseini.marketprice.utils.TimeSpan;

/**
 * Created by Mohsen on 24/03/16.
 */
public interface MainPresenter {

    void onResume();

    void onDestroy();

    boolean onBackPressed();

    void loadPriceValues(TimeSpan timeSpan, boolean isConnected);
}

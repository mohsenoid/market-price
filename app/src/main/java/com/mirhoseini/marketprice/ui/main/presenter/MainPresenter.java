package com.mirhoseini.marketprice.ui.main.presenter;

import com.mirhoseini.marketprice.utils.TimeSpan;

/**
 * Created by Mohsen on 24/03/16.
 */
public interface MainPresenter {

    void onResume();

    void onTimeSpanChanged(TimeSpan timeSpan);

    void onDestroy();

    boolean onBackPressed();

    void loadMarketPrice(TimeSpan timeSpan, boolean isConnected);
}

package com.mirhoseini.marketprice.ui.main.view;

import com.mirhoseini.marketprice.database.model.MarketPrice;
import com.mirhoseini.marketprice.utils.TimeSpan;

/**
 * Created by Mohsen on 24/03/16.
 */
public interface MainView {

    void showProgress();

    void hideProgress();

    void setMarketPrice(TimeSpan timeSpan, MarketPrice marketPrice);

    void showMessage(String message);

    void exit();

    void showExitMessage();

    void showConnectionError();

}
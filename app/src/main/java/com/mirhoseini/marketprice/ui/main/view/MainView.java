package com.mirhoseini.marketprice.ui.main.view;

import com.mirhoseini.marketprice.database.model.PriceValue;
import com.mirhoseini.marketprice.utils.TimeSpan;

import java.util.List;

/**
 * Created by Mohsen on 24/03/16.
 */
public interface MainView {

    void showProgress();

    void hideProgress();

    void setPriceValues(TimeSpan timeSpan, List<PriceValue> items);

    void showToastMessage(String message);

    void showProgressMessage(String message);

    void showOfflineMessage();

    void exit();

    void showExitMessage();

    void showConnectionError();

    void showRetryMessage();
}

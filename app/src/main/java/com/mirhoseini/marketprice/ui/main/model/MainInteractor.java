package com.mirhoseini.marketprice.ui.main.model;

import com.mirhoseini.marketprice.database.model.PriceValue;
import com.mirhoseini.marketprice.utils.TimeSpan;

import java.util.List;

/**
 * Created by Mohsen on 24/03/16.
 */
public interface MainInteractor {
    void loadMarketPrice(TimeSpan timeSpan, OnMainFinishedListener listener);

    List<PriceValue> loadPriceValuesFromDatabase(TimeSpan timeSpan);

    void deletePriceValues(TimeSpan timeSpan);

    void onDestroy();
}

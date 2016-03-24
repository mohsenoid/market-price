package com.mirhoseini.marketprice.ui.main.model;

import com.mirhoseini.marketprice.database.model.MarketPrice;
import com.mirhoseini.marketprice.utils.TimeSpan;

/**
 * Created by Mohsen on 24/03/16.
 */
public interface MainInteractor {
    void loadMarketPrice(TimeSpan timeSpan, OnMainFinishedListener listener);

    MarketPrice loadMarketPriceFromDatabase(TimeSpan timeSpan);

    void deleteMarketPrice(TimeSpan timeSpan);

    void onDestroy();
}

package com.mirhoseini.marketprice.ui.main.model;

import com.mirhoseini.marketprice.network.model.RestMarketPrice;
import com.mirhoseini.marketprice.utils.TimeSpan;

/**
 * Created by Mohsen on 24/03/16.
 */
public interface OnMainNetworkFinishedListener {

    void onNetworkSuccess(TimeSpan timeSpan, RestMarketPrice restMarketPrice);

    void onNetworkError(TimeSpan timeSpan, Throwable throwable);
}

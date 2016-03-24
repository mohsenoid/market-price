package com.mirhoseini.marketprice.ui.main.model;


import com.mirhoseini.marketprice.database.DatabaseHelper;
import com.mirhoseini.marketprice.database.model.PriceValue;
import com.mirhoseini.marketprice.network.NetworkHelper;
import com.mirhoseini.marketprice.network.OnNetworkFinishedListener;
import com.mirhoseini.marketprice.network.model.RestMarketPrice;
import com.mirhoseini.marketprice.utils.TimeSpan;

import java.util.List;


/**
 * Created by Mohsen on 24/03/16.
 */
public class MainInteractorImpl implements MainInteractor {
    NetworkHelper mNetworkHelper;
    DatabaseHelper mDatabaseHelper;

    public MainInteractorImpl() {
        mNetworkHelper = NetworkHelper.getInstance();
        mDatabaseHelper = DatabaseHelper.getInstance();
    }

    @Override
    public void loadMarketPrice(TimeSpan timeSpan, final OnMainFinishedListener listener) {
        mNetworkHelper.loadMarketPriceValues(timeSpan, new OnNetworkFinishedListener<RestMarketPrice>() {
            @Override
            public void onSuccess(TimeSpan timeSpan, RestMarketPrice restResponse) {
                if (listener != null)
                    listener.onSuccess(timeSpan, restResponse);
            }

            @Override
            public void onError(TimeSpan timeSpan, Throwable throwable) {
                if (listener != null)
                    listener.onError(timeSpan, throwable);
            }
        });
    }

    @Override
    public List<PriceValue> loadPriceValuesFromDatabase(TimeSpan timeSpan) {
        List<PriceValue> items = mDatabaseHelper.loadPriceValues(timeSpan.getPosition());

        return items;
    }

    @Override
    public void deletePriceValues(TimeSpan timeSpan) {
        mDatabaseHelper.deletePriceValues(timeSpan.getPosition());
    }

    @Override
    public void onDestroy() {
        mDatabaseHelper = null;
        mNetworkHelper = null;
    }
}

package com.mirhoseini.marketprice.ui.main.model;


import com.mirhoseini.marketprice.database.DatabaseHelper;
import com.mirhoseini.marketprice.database.model.MarketPrice;
import com.mirhoseini.marketprice.network.NetworkHelper;
import com.mirhoseini.marketprice.network.OnNetworkFinishedListener;
import com.mirhoseini.marketprice.network.model.RestMarketPrice;
import com.mirhoseini.marketprice.utils.TimeSpan;


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
    public MarketPrice loadMarketPriceFromDatabase(TimeSpan timeSpan) {
        MarketPrice marketPrice = mDatabaseHelper.loadMarketPrice(timeSpan);

        return marketPrice;
    }

    @Override
    public void deleteMarketPrice(TimeSpan timeSpan) {
        mDatabaseHelper.deleteMarketPrice(timeSpan);
    }

    @Override
    public void onDestroy() {
        mDatabaseHelper = null;
        mNetworkHelper = null;
    }
}

package com.mirhoseini.marketprice.ui.main.presenter;

import com.mirhoseini.marketprice.database.model.PriceValue;
import com.mirhoseini.marketprice.network.converters.RestModelConverter;
import com.mirhoseini.marketprice.network.model.RestMarketPrice;
import com.mirhoseini.marketprice.network.model.RestPriceValue;
import com.mirhoseini.marketprice.ui.main.model.MainInteractor;
import com.mirhoseini.marketprice.ui.main.model.MainInteractorImpl;
import com.mirhoseini.marketprice.ui.main.model.OnMainFinishedListener;
import com.mirhoseini.marketprice.ui.main.view.MainView;
import com.mirhoseini.marketprice.utils.TimeSpan;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Mohsen on 24/03/16.
 */
public class MainPresenterImpl implements MainPresenter, OnMainFinishedListener {
    private static boolean sDoubleBackToExitPressedOnce;
    private static boolean sOfflineMessageShowedOnce;

    MainInteractor mMainInteractor;
    private MainView mMainView;

    public MainPresenterImpl(MainView mainView) {
        this.mMainView = mainView;

        mMainInteractor = new MainInteractorImpl();

    }

    @Override
    public void onResume() {
        if (mMainView != null) {
            mMainView.showProgress();
        }

        sDoubleBackToExitPressedOnce = false;

    }

    @Override
    public void onTimeSpanChanged(TimeSpan timeSpan) {

    }

    @Override
    public void onDestroy() {
        mMainView = null;
        mMainInteractor.onDestroy();
        mMainInteractor = null;
    }

    @Override
    public boolean onBackPressed() {
        if (sDoubleBackToExitPressedOnce) {
            if (mMainView != null) {
                mMainView.exit();
            }
            return false;
        }
        sDoubleBackToExitPressedOnce = true;

        if (mMainView != null) {
            mMainView.showExitMessage();
        }

        Timer t = new Timer();
        t.schedule(new TimerTask() {

            @Override
            public void run() {
                sDoubleBackToExitPressedOnce = false;
            }
        }, 2500);

        return false;
    }

    @Override
    public void loadPriceValues(TimeSpan timeSpan, boolean isConnected) {
        List<PriceValue> items = mMainInteractor.loadPriceValuesFromDatabase(timeSpan);

        boolean hasData = items.size() > 0;

        if (isConnected)
            mMainInteractor.loadMarketPrice(timeSpan, this);
        else if (hasData) {
            mMainView.setPriceValues(timeSpan, items);
            mMainView.hideProgress();
            if (!sOfflineMessageShowedOnce) {
                mMainView.showOfflineMessage();
                sOfflineMessageShowedOnce = true;
            }
        } else {
            if (mMainView != null) {
                mMainView.showConnectionError();
            }
        }
    }

    @Override
    public void onSuccess(TimeSpan timeSpan, RestMarketPrice restMarketPrice) {
        mMainInteractor.deletePriceValues(timeSpan);

        for (RestPriceValue restPriceValue : restMarketPrice.getValues())
            RestModelConverter.convertRestModelToPriceValue(timeSpan, restPriceValue).save();

        List<PriceValue> items = mMainInteractor.loadPriceValuesFromDatabase(timeSpan);

        boolean hasData = items.size() > 0;

        if (hasData) {
            if (mMainView != null) {
                mMainView.setPriceValues(timeSpan, items);
            }
        }

        mMainView.hideProgress();

    }

    @Override
    public void onError(TimeSpan timeSpan, Throwable throwable) {
        if (mMainView != null) {
            mMainView.showMessage(throwable.getMessage());
            mMainView.hideProgress();
        }
    }
}

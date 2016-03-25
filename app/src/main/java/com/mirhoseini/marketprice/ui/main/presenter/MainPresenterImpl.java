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
    MainInteractor mMainInteractor;

    private MainView mMainView;

    private boolean mDoubleBackToExitPressedOnce;

    public MainPresenterImpl(MainView mainView) {
        this.mMainView = mainView;

        mMainInteractor = new MainInteractorImpl();

    }

    @Override
    public void onResume() {
        if (mMainView != null) {
            mMainView.showProgress();
        }

        mDoubleBackToExitPressedOnce = false;

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
        if (mDoubleBackToExitPressedOnce) {
            if (mMainView != null) {
                mMainView.exit();
            }
            return false;
        }
        mDoubleBackToExitPressedOnce = true;

        if (mMainView != null) {
            mMainView.showExitMessage();
        }

        Timer t = new Timer();
        t.schedule(new TimerTask() {

            @Override
            public void run() {
                mDoubleBackToExitPressedOnce = false;
            }
        }, 2500);

        return false;
    }

    @Override
    public void loadPriceValues(TimeSpan timeSpan, boolean isConnected) {
        List<PriceValue> items = mMainInteractor.loadPriceValuesFromDatabase(timeSpan);

        boolean hasData = items.size() > 0;

        if (hasData) {
            if (mMainView != null) {

            }
        }

//        if (!AppApplication.sSessionCategoryUpdated) {
        if (isConnected)
            mMainInteractor.loadMarketPrice(timeSpan, this);
        else if (hasData) {
            mMainView.setPriceValues(timeSpan, items);
            mMainView.hideProgress();
            mMainView.showMessage("Working offline!");
        } else {
            if (mMainView != null) {
                mMainView.showConnectionError();
            }
        }
//        }
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

//        AppApplication.sSessionCategoryUpdated = true;
    }

    @Override
    public void onError(TimeSpan timeSpan, Throwable throwable) {
        if (mMainView != null) {
            mMainView.showMessage(throwable.getMessage());
            mMainView.hideProgress();
        }
    }
}

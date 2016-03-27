package com.mirhoseini.marketprice.ui.main.presenter;

import com.mirhoseini.marketprice.database.model.PriceValue;
import com.mirhoseini.marketprice.network.model.RestMarketPrice;
import com.mirhoseini.marketprice.ui.main.model.MainInteractor;
import com.mirhoseini.marketprice.ui.main.model.MainInteractorImpl;
import com.mirhoseini.marketprice.ui.main.model.OnMainDatabaseFinishedListener;
import com.mirhoseini.marketprice.ui.main.model.OnMainNetworkFinishedListener;
import com.mirhoseini.marketprice.ui.main.view.MainActivity;
import com.mirhoseini.marketprice.ui.main.view.MainView;
import com.mirhoseini.marketprice.utils.TimeSpan;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Mohsen on 24/03/16.
 */
public class MainPresenterImpl implements MainPresenter, OnMainNetworkFinishedListener {
    private static boolean sDoubleBackToExitPressedOnce;

    MainInteractor mMainInteractor;
    private MainView mMainView;

    public MainPresenterImpl(MainView mainView) {
        this.mMainView = mainView;

        mMainInteractor = new MainInteractorImpl();

        sDoubleBackToExitPressedOnce = false;
    }

    @Override
    public void onResume() {

        sDoubleBackToExitPressedOnce = false;

    }

    @Override
    public void onDestroy() {
        //delete all references with UI destruction
        mMainView = null;
        mMainInteractor.onDestroy();
        mMainInteractor = null;
    }

    @Override
    public boolean onBackPressed() {
        // check for double back press to exit
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

        if (mMainView != null) {
            mMainView.showProgress();
            mMainView.showProgressMessage("Loading Price Values...");
        }

        if (mMainInteractor != null) {//check if object is not destroyed

            List<PriceValue> items = mMainInteractor.loadPriceValuesFromDatabase(timeSpan);

            boolean hasData = items.size() > 0;

            //load cache data from database
            if (hasData) {
                if (mMainView != null) {
                    mMainView.setPriceValues(timeSpan, items);
                    mMainView.hideProgress();
                }

            }

            //load data from network
            if (isConnected) {

                mMainInteractor.loadMarketPrice(timeSpan, this);

            } else if (hasData) {// show offline message if cache data is available in Database

                if (mMainView != null) {
                    mMainView.showOfflineMessage();
                }

            } else if (!hasData) { // network connection is required for the first run with no cached data

                if (mMainView != null) {
                    mMainView.showConnectionError();
                    mMainView.hideProgress();
                }

            }
        }

    }


    @Override
    public void onNetworkSuccess(TimeSpan timeSpan, RestMarketPrice restMarketPrice) {
        if (mMainView != null) {
            mMainView.showProgressMessage("Saving Price Values to Database...");
        }

        if (mMainInteractor != null) {//check if object is not destroyed

            //save received values to database
            mMainInteractor.saveMarketPrice(timeSpan, restMarketPrice, new OnMainDatabaseFinishedListener() {
                @Override
                public void onDatabaseSuccess(TimeSpan timeSpan) {

                    if (mMainInteractor != null) {//check if object is not destroyed

                        List<PriceValue> items = mMainInteractor.loadPriceValuesFromDatabase(timeSpan);

                        boolean hasData = items.size() > 0;

                        if (hasData) {
                            if (mMainView != null) {
                                mMainView.setPriceValues(timeSpan, items);
                            }
                        }

                        mMainView.hideProgress();

                    }
                }
            });
        }

    }

    @Override
    public void onNetworkError(TimeSpan timeSpan, Throwable throwable) {

        if (mMainView != null) {
            mMainView.showToastMessage(throwable.getMessage());
        }

        if (mMainInteractor != null) {//check if object is not destroyed

            List<PriceValue> items = mMainInteractor.loadPriceValuesFromDatabase(timeSpan);

            boolean hasData = items.size() > 0;

            if (hasData) {

                if (mMainView != null) {
                    mMainView.setPriceValues(timeSpan, items);
                }

            }
        }

        if (mMainView != null) {
            mMainView.hideProgress();
            mMainView.showRetryMessage();
        }

    }

}

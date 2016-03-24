package com.mirhoseini.marketprice.ui.main.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mirhoseini.marketprice.R;
import com.mirhoseini.marketprice.database.model.MarketPrice;
import com.mirhoseini.marketprice.ui.BaseActivity;
import com.mirhoseini.marketprice.ui.main.presenter.MainPresenter;
import com.mirhoseini.marketprice.ui.main.presenter.MainPresenterImpl;
import com.mirhoseini.marketprice.utils.TimeSpan;
import com.mirhoseini.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mohsen on 24/03/16.
 */
public class MainActivity extends BaseActivity implements MainView {

    MainPresenter mMainPresenter;

    @Bind(R.id.list)
    RecyclerView mRecyclerView;
    @Bind(R.id.progress)
    ProgressBar mProgressBar;

    private AlertDialog mInternetConnectionDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mMainPresenter = new MainPresenterImpl(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMainPresenter.onResume();

        if (mInternetConnectionDialog != null)
            mInternetConnectionDialog.dismiss();

        mMainPresenter.loadMarketPrice(TimeSpan.DAY_30, Utils.isConnected(this));
    }


    @Override
    protected void onDestroy() {
        mMainPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }


    @Override
    public void setMarketPrice(TimeSpan timeSpan, MarketPrice marketPrice) {
        //Todo: load data to graph
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(mRecyclerView, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void exit() {
        Utils.exit(this);
    }

    @Override
    public void showExitMessage() {
        Toast.makeText(this, R.string.msg_exit, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showConnectionError() {
        if (mInternetConnectionDialog != null)
            mInternetConnectionDialog.dismiss();

        mInternetConnectionDialog = Utils.showNoInternetConnectionDialog(this, true);
    }

    @Override
    public void onBackPressed() {
        if (mMainPresenter.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
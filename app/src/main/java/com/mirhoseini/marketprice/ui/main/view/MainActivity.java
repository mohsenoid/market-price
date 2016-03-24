package com.mirhoseini.marketprice.ui.main.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.mirhoseini.appsettings.AppSettings;
import com.mirhoseini.marketprice.R;
import com.mirhoseini.marketprice.database.model.PriceValue;
import com.mirhoseini.marketprice.ui.BaseActivity;
import com.mirhoseini.marketprice.ui.main.presenter.MainPresenter;
import com.mirhoseini.marketprice.ui.main.presenter.MainPresenterImpl;
import com.mirhoseini.marketprice.utils.Constants;
import com.mirhoseini.marketprice.utils.TimeSpan;
import com.mirhoseini.utils.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;

/**
 * Created by Mohsen on 24/03/16.
 */
public class MainActivity extends BaseActivity implements MainView {

    MainPresenter mMainPresenter;

    AlertDialog mInternetConnectionDialog;

    @Bind(R.id.list)
    RecyclerView mRecyclerView;
    @Bind(R.id.progress)
    ProgressBar mProgressBar;
    @Bind(R.id.timespan_spinner)
    Spinner mTimeSpan;


    @OnItemSelected(R.id.timespan_spinner)
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mMainPresenter.loadPriceValues(TimeSpan.values()[position], Utils.isConnected(this));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        loadLastTimeSpan();

        mMainPresenter = new MainPresenterImpl(this);

    }

    private void loadLastTimeSpan() {
        TimeSpan lastTimeSpan = TimeSpan.valueOf(AppSettings.getString(this, Constants.LAST_TIMESPAN, TimeSpan.DAY_30.getValue()));

        mTimeSpan.setAdapter(new ArrayAdapter<TimeSpan>(this, android.R.layout.simple_spinner_dropdown_item, TimeSpan.values()));
        mTimeSpan.setSelection(lastTimeSpan.getPosition());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMainPresenter.onResume();

        if (mInternetConnectionDialog != null)
            mInternetConnectionDialog.dismiss();

        mMainPresenter.loadPriceValues(TimeSpan.values()[mTimeSpan.getSelectedItemPosition()], Utils.isConnected(this));
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

        mTimeSpan.setEnabled(false);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);

        mTimeSpan.setEnabled(true);
    }


    @Override
    public void setPriceValues(TimeSpan timeSpan, List<PriceValue> items) {
        //Todo: load data to graph
        saveLastTimeSpan(timeSpan);


    }

    private void saveLastTimeSpan(TimeSpan timeSpan) {
        AppSettings.setValue(this, Constants.LAST_TIMESPAN, timeSpan.getPosition());
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
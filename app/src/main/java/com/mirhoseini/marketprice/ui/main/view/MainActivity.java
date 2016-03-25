package com.mirhoseini.marketprice.ui.main.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.mirhoseini.appsettings.AppSettings;
import com.mirhoseini.marketprice.R;
import com.mirhoseini.marketprice.database.model.PriceValue;
import com.mirhoseini.marketprice.ui.BaseActivity;
import com.mirhoseini.marketprice.ui.main.presenter.MainPresenter;
import com.mirhoseini.marketprice.ui.main.presenter.MainPresenterImpl;
import com.mirhoseini.marketprice.utils.Constants;
import com.mirhoseini.marketprice.utils.TimeSpan;
import com.mirhoseini.utils.Utils;

import java.util.Date;
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

    @Bind(R.id.graph)
    GraphView mGraph;
    @Bind(R.id.progress)
    ProgressBar mProgressBar;
    @Bind(R.id.timespan_spinner)
    Spinner mTimeSpan;

    Context mContext;


    @OnItemSelected(R.id.timespan_spinner)
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mMainPresenter.loadPriceValues(TimeSpan.values()[position], Utils.isConnected(this));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        ButterKnife.bind(this);

        loadLastTimeSpan();

        mMainPresenter = new MainPresenterImpl(this);

    }

    private void loadLastTimeSpan() {
        TimeSpan lastTimeSpan = TimeSpan.fromValue(AppSettings.getString(this, Constants.LAST_TIMESPAN, TimeSpan.DAY_30.getValue()));

        mTimeSpan.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, TimeSpan.getValues()));
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
        mGraph.setVisibility(View.INVISIBLE);

        mTimeSpan.setEnabled(false);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.INVISIBLE);
        mGraph.setVisibility(View.VISIBLE);

        mTimeSpan.setEnabled(true);
    }


    @Override
    public void setPriceValues(TimeSpan timeSpan, List<PriceValue> items) {
        //Todo: load data to graph
        saveLastTimeSpan(timeSpan);

        DataPoint[] data = new DataPoint[items.size()];
        for (int i = 0; i < items.size(); i++) {
            data[i] = new DataPoint(new Date(items.get(i).getX() * 1000), items.get(i).getY());
        }

        mGraph.removeAllSeries();

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(data);

        mGraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(mContext));
        mGraph.getGridLabelRenderer().setNumHorizontalLabels(4);
        mGraph.getViewport().setScalable(true);
        mGraph.getViewport().setScrollable(true);
        mGraph.addSeries(series);
    }

    private void saveLastTimeSpan(TimeSpan timeSpan) {
        AppSettings.setValue(this, Constants.LAST_TIMESPAN, timeSpan.getValue());
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(mGraph, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showOfflineMessage() {
        Snackbar.make(mGraph, R.string.offline_message, Snackbar.LENGTH_LONG)
                .setAction(R.string.go_online, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(
                                Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .show();
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
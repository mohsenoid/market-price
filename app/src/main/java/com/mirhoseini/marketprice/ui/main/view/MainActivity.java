package com.mirhoseini.marketprice.ui.main.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mirhoseini.appsettings.AppSettings;
import com.mirhoseini.marketprice.R;
import com.mirhoseini.marketprice.database.model.PriceValue;
import com.mirhoseini.marketprice.ui.BaseActivity;
import com.mirhoseini.marketprice.ui.main.presenter.MainPresenter;
import com.mirhoseini.marketprice.ui.main.presenter.MainPresenterImpl;
import com.mirhoseini.marketprice.utils.Constants;
import com.mirhoseini.marketprice.utils.TimeSpan;
import com.mirhoseini.marketprice.widget.MyGraph;
import com.mirhoseini.utils.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Mohsen on 24/03/16.
 */
public class MainActivity extends BaseActivity implements MainView {
    public static final String TAG = MainActivity.class.getSimpleName();

    MainPresenter mMainPresenter;

    AlertDialog mInternetConnectionDialog;

    @Bind(R.id.graph)
    MyGraph mGraph;
    @Bind(R.id.progress)
    View mProgress;
    @Bind(R.id.progress_message)
    TextView mProgressMessage;
    @Bind(R.id.timespan_spinner)
    Spinner mTimeSpan;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        mMainPresenter = new MainPresenterImpl(this);

        // binding Views using ButterKnife library
        ButterKnife.bind(this);

        Timber.tag(TAG);
        Timber.d("Activity Created");

        int lastTimeSpan;

        if (savedInstanceState == null) { //load lastTimeSpan from SharePreferences
            lastTimeSpan = AppSettings.getInt(this, Constants.LAST_TIMESPAN, TimeSpan.DAY_30.getPosition());
        } else { //load lastTimeSpan from saved state before UI change
            lastTimeSpan = savedInstanceState.getInt(Constants.LAST_TIMESPAN);
        }

        loadTimeSpanSpinnerData(lastTimeSpan);
    }

    //load TimeSpan Spinner data using static enum values
    private void loadTimeSpanSpinnerData(int savedTimeSpan) {
        Timber.d("loading TimeSpan Spinner");

        mTimeSpan.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, TimeSpan.getValues()));
        mTimeSpan.setSelection(savedTimeSpan);

        //set onItemSelectedListener after spinner adapter data loaded to avoid unwanted call before item selected
        mTimeSpan.post(new Runnable() {
            @Override
            public void run() {
                mTimeSpan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        mMainPresenter.loadPriceValues(TimeSpan.values()[position], Utils.isConnected(mContext));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        // nothing to do...
                    }
                });
            }
        });

    }

    @Override
    protected void onResume() {
        Timber.d("Activity Resumed");

        super.onResume();
        mMainPresenter.onResume();

        // dismiss no internet connection dialog in case of connection fixed
        if (mInternetConnectionDialog != null)
            mInternetConnectionDialog.dismiss();

        mMainPresenter.loadPriceValues(TimeSpan.values()[mTimeSpan.getSelectedItemPosition()], Utils.isConnected(this));
    }


    @Override
    protected void onDestroy() {
        Timber.d("Activity Destroyed");

        mMainPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        Timber.d("Showing Progress");

        mProgress.setVisibility(View.VISIBLE);
        mGraph.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        Timber.d("Hiding Progress");

        mProgress.setVisibility(View.INVISIBLE);
    }


    @Override
    public void setPriceValues(TimeSpan timeSpan, List<PriceValue> items) {
        Timber.d("Setting TimeSpan: %s Price Values: %s", timeSpan.getValue(), items.toString());

        //check if loaded items are the same as current request
        if (timeSpan == TimeSpan.values()[mTimeSpan.getSelectedItemPosition()]) {

            saveLastTimeSpan(timeSpan);

            mGraph.setPriceValues(items);
            mGraph.setVisibility(View.VISIBLE);

            //used jjoe64 graphview before preparing my own Graph View
            /*
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
            */
        }

    }

    // save user last selected TimeSpan
    private void saveLastTimeSpan(TimeSpan timeSpan) {
        Timber.d("Saving Last TimeSpan");

        AppSettings.setValue(this, Constants.LAST_TIMESPAN, timeSpan.getPosition());
    }

    @Override
    public void showToastMessage(String message) {
        Timber.d("Showing Toast Message: %s", message);

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressMessage(String message) {
        Timber.d("Showing Progress Message: %s", message);

        mProgressMessage.setText(message);
    }

    @Override
    public void showOfflineMessage() {
        Timber.d("Showing Offline Message");

        Snackbar.make(mGraph, R.string.offline_message, Snackbar.LENGTH_LONG)
                .setAction(R.string.go_online, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(
                                Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setActionTextColor(Color.GREEN)
                .show();
    }

    @Override
    public void exit() {
        Timber.d("Exiting");

        Utils.exit(this);
    }

    @Override
    public void showExitMessage() {
        Timber.d("Showing Exit Message");

        Toast.makeText(this, R.string.msg_exit, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showConnectionError() {
        Timber.d("Showing Connection Error Message");

        if (mInternetConnectionDialog != null)
            mInternetConnectionDialog.dismiss();

        mInternetConnectionDialog = Utils.showNoInternetConnectionDialog(this, false);
    }

    @Override
    public void showRetryMessage() {
        Timber.d("Showing Retry Message");

        Snackbar.make(mGraph, R.string.retry_message, Snackbar.LENGTH_LONG)
                .setAction(R.string.load_retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMainPresenter.loadPriceValues(TimeSpan.values()[mTimeSpan.getSelectedItemPosition()], Utils.isConnected(mContext));
                    }
                })
                .setActionTextColor(Color.RED)
                .show();
    }

    @Override
    public void onBackPressed() {
        Timber.d("Activity Back Pressed");

        if (mMainPresenter.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Timber.d("Activity Saving Instance State");

        super.onSaveInstanceState(outState);

        //save TimeSpan selected by user before data loaded and saved to SharedPreferences
        outState.putInt(Constants.LAST_TIMESPAN, mTimeSpan.getSelectedItemPosition());
    }

}
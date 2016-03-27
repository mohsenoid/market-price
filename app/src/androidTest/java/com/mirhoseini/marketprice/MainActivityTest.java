package com.mirhoseini.marketprice;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.mirhoseini.marketprice.ui.main.view.MainActivity;
import com.mirhoseini.marketprice.utils.TimeSpan;

import org.junit.Test;


/**
 * Created by Mohsen on 24/03/16.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mActivity;
    private Spinner mSpinner;
    private SpinnerAdapter mSpinnerAdapter;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();

        setActivityInitialTouchMode(false);
        mActivity = getActivity();
        mSpinner =
                (Spinner) mActivity.findViewById(R.id.timespan_spinner);
        mSpinnerAdapter = mSpinner.getAdapter();
    }

    @Test
    public void testPreConditions() {
        assertTrue(mSpinner.getOnItemSelectedListener() != null);
        assertTrue(mSpinnerAdapter != null);
        assertEquals(mSpinnerAdapter.getCount(), TimeSpan.getValues().size());
    }

    @Test
    public void testSpinnerUI() {
        mActivity.runOnUiThread(
                new Runnable() {
                    public void run() {
                        mSpinner.requestFocus();
                        mSpinner.setSelection(TimeSpan.DAY_60.getPosition());
                    }
                }
        );
    }

    @Test
    public void testSpinnerValue() {
        int mPos = mSpinner.getSelectedItemPosition();
        String mSelection = TimeSpan.DAY_60.getValue();

        String resultText = mSpinner.getSelectedItem().toString();

        assertEquals(resultText, mSelection);
    }

}
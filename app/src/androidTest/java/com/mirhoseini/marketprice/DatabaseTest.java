package com.mirhoseini.marketprice;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityTestCase;
import android.test.AndroidTestRunner;
import android.test.ApplicationTestCase;
import android.test.InstrumentationTestCase;
import android.test.InstrumentationTestRunner;
import android.test.mock.MockContext;

import com.mirhoseini.marketprice.database.DatabaseHelper;
import com.mirhoseini.marketprice.database.model.PriceValue;
import com.mirhoseini.marketprice.network.NetworkHelper;
import com.mirhoseini.marketprice.network.OnNetworkFinishedListener;
import com.mirhoseini.marketprice.network.model.RestMarketPrice;
import com.mirhoseini.marketprice.ui.main.view.MainActivity;
import com.mirhoseini.marketprice.utils.TimeSpan;
import com.raizlabs.android.dbflow.config.FlowManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Mohsen on 3/26/16.
 */
public class DatabaseTest extends InstrumentationTestCase {

    Context context;

    DatabaseHelper databaseHelper;
    TimeSpan timeSpan;


    public void setUp() throws Exception {
        super.setUp();

        context = getInstrumentation().getContext();

        assertNotNull(context);

        FlowManager.init(context);

        timeSpan = TimeSpan.DAY_30;
        databaseHelper = DatabaseHelper.getInstance();

    }


    @Test
    public void testDatabase() throws InterruptedException {

        assertNotNull(databaseHelper);

        databaseHelper.deletePriceValues(timeSpan.getPosition());

        PriceValue priceValue = new PriceValue(timeSpan.getPosition(), 1459026112l, 400);
        databaseHelper.insertPriceValue(priceValue);

        List<PriceValue> items = databaseHelper.loadPriceValues(timeSpan.getPosition());

        assertNotNull(items);
        assertTrue(items.size() == 1);
        assertEquals(items.get(0), priceValue);

        databaseHelper.deletePriceValues(timeSpan.getPosition());

        List<PriceValue> noItem = databaseHelper.loadPriceValues(timeSpan.getPosition());

        assertNotNull(noItem);
        assertTrue(noItem.size() == 0);

    }
}

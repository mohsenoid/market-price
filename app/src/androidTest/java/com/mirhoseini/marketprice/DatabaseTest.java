package com.mirhoseini.marketprice;

import android.content.Context;
import android.test.InstrumentationTestCase;

import com.mirhoseini.marketprice.database.DatabaseHelper;
import com.mirhoseini.marketprice.database.model.PriceValue;
import com.mirhoseini.marketprice.utils.TimeSpan;
import com.raizlabs.android.dbflow.config.FlowManager;

import org.junit.Test;

import java.util.List;

/**
 * Created by Mohsen on 24/03/16.
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

package com.mirhoseini.marketprice.database;

import com.mirhoseini.marketprice.database.model.MarketPrice;
import com.mirhoseini.marketprice.database.model.MarketPrice_Table;
import com.mirhoseini.marketprice.utils.TimeSpan;
import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;


/**
 * Created by Mohsen on 24/03/16.
 */
@Database(name = DatabaseHelper.NAME, version = DatabaseHelper.VERSION,
        sqlHelperClass = SQLCipherHelperImpl.class)
public class DatabaseHelper {
    public static final String NAME = "market_db";
    public static final int VERSION = 1;
    static DatabaseHelper instance;

    public static DatabaseHelper getInstance() {
        if (instance != null)
            instance = new DatabaseHelper();

        return instance;
    }

    public MarketPrice loadMarketPrice(TimeSpan timeSpan) {
        return SQLite.select()
                .from(MarketPrice.class)
                .where(MarketPrice_Table.timeSpan.is(timeSpan.getValue()))
                .querySingle();
    }

    public void deleteAllMarketPrices() {
        Delete.table(MarketPrice.class);
    }

    public void deleteMarketPrice(TimeSpan timeSpan) {
        Delete.table(MarketPrice.class, MarketPrice_Table.timeSpan.is(timeSpan.getValue()));
    }


}

package com.mirhoseini.marketprice.database;

import com.mirhoseini.marketprice.database.model.PriceValue;
import com.mirhoseini.marketprice.database.model.PriceValue_Table;
import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;


/**
 * Created by Mohsen on 24/03/16.
 */
@Database(name = DatabaseHelper.NAME, version = DatabaseHelper.VERSION) , sqlHelperClass = SQLCipherHelperImpl.class)
public class DatabaseHelper {
    public static final String NAME = "market_db";
    public static final int VERSION = 1;
    static DatabaseHelper instance;

    public static DatabaseHelper getInstance() {
        if (instance == null)
            instance = new DatabaseHelper();

        return instance;
    }

    public List<PriceValue> loadPriceValues(int timeSpanId) {
        return SQLite.select()
                .from(PriceValue.class)
                .where(PriceValue_Table.timeSpanId.is(timeSpanId))
                .queryList();
    }

    public void deletePriceValues(int timeSpanId) {
        Delete.table(PriceValue.class, PriceValue_Table.timeSpanId.is(timeSpanId));
    }

}

package data.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mxion on 9/21/2017.
 */

public class dbhelper extends SQLiteOpenHelper {


    private static final int version = 1;
    private static final String database_name = "ShoppingList.db";

    public dbhelper(Context context) {
        super(context, database_name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataSource.CREATE_LIST_TABLE);
        db.execSQL(DataSource.CREATE_ITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DataSource.DELETE_LIST_TABLE);
        db.execSQL(DataSource.DELETE_ITEM_TABLE);

        db.execSQL(DataSource.CREATE_LIST_TABLE);
        db.execSQL(DataSource.CREATE_ITEM_TABLE);
    }
}


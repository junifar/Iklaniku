package com.rubahapi.iklaniku.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by prasetia on 1/10/2017
 */

class SpotIklanDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DB_NAME = "spotiklan.db";

    SpotIklanDbHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_VEHICLE_TABLE = "CREATE TABLE " + SpotIklanContract.VehicleEntry.TABLE_NAME + " (" +
                SpotIklanContract.VehicleEntry.COLUMN_ID + " INTEGER PRIMARY_KEY AUTOINCREMENT," +
                SpotIklanContract.VehicleEntry.COLUMN_NAME + " TEXT NOT NULL," +
                SpotIklanContract.VehicleEntry.COLUMN_PLAT_NUMBER + " TEXT NOT NULL," +
                SpotIklanContract.VehicleEntry.COLUMN_IMAGE_PATH + " TEXT," +
                SpotIklanContract.VehicleEntry.COLUMN_VEHICLE_TYPE + " INTEGER," +
                "UNIQUE (" + SpotIklanContract.VehicleEntry.COLUMN_PLAT_NUMBER + ") ON CONFLICT REPLACE)";

        sqLiteDatabase.execSQL(SQL_CREATE_VEHICLE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + SpotIklanContract.VehicleEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}

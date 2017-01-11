package com.rubahapi.iklaniku.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.rubahapi.iklaniku.data.SpotIklanContract;

/**
 * Created by prasetia on 1/11/2017
 */

public class VehicleService extends IntentService{

    public VehicleService() {
        super("Vehicle");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ContentValues cv = new ContentValues();
        cv.put(SpotIklanContract.VehicleEntry.COLUMN_NAME, "Vario 125D");
        cv.put(SpotIklanContract.VehicleEntry.COLUMN_PLAT_NUMBER, "B3664KMD");
        cv.put(SpotIklanContract.VehicleEntry.COLUMN_VEHICLE_TYPE, 2);
        cv.put(SpotIklanContract.VehicleEntry.COLUMN_IMAGE_PATH, "http://www.hondacengkareng.com/wp-content/uploads/2012/03/Honda-Vario-125.jpg");

        Log.d("URI : ", "content://" + SpotIklanContract.CONTENT_AUTHORITY + "/" + SpotIklanContract.PATH_SPOT_IKLAN + "/" + SpotIklanContract.VehicleEntry.PATH_VEHICLE);
        Uri uri = Uri.parse("content://" + SpotIklanContract.CONTENT_AUTHORITY + "/" + SpotIklanContract.PATH_SPOT_IKLAN + "/" + SpotIklanContract.VehicleEntry.PATH_VEHICLE);
        //getContentResolver().delete(uri, null, null);
        getContentResolver().insert(uri, cv);
        getContentResolver().notifyChange(uri, null);
    }
}

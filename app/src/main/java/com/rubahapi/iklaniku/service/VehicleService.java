package com.rubahapi.iklaniku.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;

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
        cv.put(SpotIklanContract.VehicleEntry.COLUMN_NAME, "Vario Techno 125");
        cv.put(SpotIklanContract.VehicleEntry.COLUMN_PLAT_NUMBER, "B3664KMD");
        cv.put(SpotIklanContract.VehicleEntry.COLUMN_VEHICLE_TYPE, 2);
        cv.put(SpotIklanContract.VehicleEntry.COLUMN_IMAGE_PATH, "http://www.hondacengkareng.com/wp-content/uploads/2012/03/Honda-Vario-125.jpg");

        ContentValues cv1 = new ContentValues();
        cv1.put(SpotIklanContract.VehicleEntry.COLUMN_NAME, "Supra X 125D");
        cv1.put(SpotIklanContract.VehicleEntry.COLUMN_PLAT_NUMBER, "B6415KGU");
        cv1.put(SpotIklanContract.VehicleEntry.COLUMN_VEHICLE_TYPE, 2);
        cv1.put(SpotIklanContract.VehicleEntry.COLUMN_IMAGE_PATH, "http://www.hondacengkareng.com/wp-content/uploads/2012/03/Honda-Vario-125.jpg");

        ContentValues cv2 = new ContentValues();
        cv2.put(SpotIklanContract.VehicleEntry.COLUMN_NAME, "Legenda Astrea");
        cv2.put(SpotIklanContract.VehicleEntry.COLUMN_PLAT_NUMBER, "B5043RQ");
        cv2.put(SpotIklanContract.VehicleEntry.COLUMN_VEHICLE_TYPE, 2);
        cv2.put(SpotIklanContract.VehicleEntry.COLUMN_IMAGE_PATH, "http://www.hondacengkareng.com/wp-content/uploads/2012/03/Honda-Vario-125.jpg");

        Uri uri = Uri.parse("content://" + SpotIklanContract.CONTENT_AUTHORITY + "/" + SpotIklanContract.PATH_SPOT_IKLAN + "/" + SpotIklanContract.VehicleEntry.PATH_VEHICLE);
        getContentResolver().insert(uri, cv);
        getContentResolver().insert(uri, cv1);
        getContentResolver().insert(uri, cv2);
        getContentResolver().notifyChange(uri, null);
    }
}

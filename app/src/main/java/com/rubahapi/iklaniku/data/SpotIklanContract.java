package com.rubahapi.iklaniku.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by prasetia on 1/10/2017
 */

class SpotIklanContract {

    static final String CONTENT_AUTHORITY = "com.rubahapi.iklaniku";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    static final String PATH_SPOT_IKLAN = "spotiklan";

    static final class VehicleEntry implements BaseColumns{

        static final String PATH_VEHICLE = "vehicle";
        static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_VEHICLE).build();

        static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SPOT_IKLAN;

        static final String TABLE_NAME = "VEHICLES";
        static final String COLUMN_ID = "ID";
        static final String COLUMN_NAME = "NAME";
        static final String COLUMN_PLAT_NUMBER = "PLAT_NUMBER";
        static final String COLUMN_IMAGE_PATH = "IMAGE_PATH";
        static final String COLUMN_VEHICLE_TYPE = "VEHICLE_TYPE";

        static Uri buildVehicleUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}

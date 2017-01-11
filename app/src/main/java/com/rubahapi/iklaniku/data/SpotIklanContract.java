package com.rubahapi.iklaniku.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by prasetia on 1/10/2017
 */

public class SpotIklanContract {

    public static final String CONTENT_AUTHORITY = "com.rubahapi.iklaniku";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_SPOT_IKLAN = "spotiklan";

    public static final class VehicleEntry implements BaseColumns{

        public static final String PATH_VEHICLE = "vehicle";
        static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_VEHICLE).build();

        static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SPOT_IKLAN;

        public static final String TABLE_NAME = "VEHICLES";
        public static final String COLUMN_ID = "ID";
        public static final String COLUMN_NAME = "NAME";
        public static final String COLUMN_PLAT_NUMBER = "PLAT_NUMBER";
        public static final String COLUMN_IMAGE_PATH = "IMAGE_PATH";
        public static final String COLUMN_VEHICLE_TYPE = "VEHICLE_TYPE";

        static Uri buildVehicleUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}

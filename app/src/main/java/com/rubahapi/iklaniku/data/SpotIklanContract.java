package com.rubahapi.iklaniku.data;

import android.provider.BaseColumns;

/**
 * Created by prasetia on 1/10/2017.
 */

public class SpotIklanContract {

    public static final String CONTENT_AUTHORITY = "com.rubahapi.iklaniku";

    public static final String PATH_SPOT_IKLAN = "spotiklan";

    public static final class VehicleEntry implements BaseColumns{

        public static final String PATH_VEHICLE = "vehicle";

        public static final String TABLE_NAME = "VEHICLES";
        public static final String COLUMN_ID = "ID";
        public static final String COLUMN_NAME = "NAME";
        public static final String COLUMN_PLAT_NUMBER = "PLAT_NUMBER";
        public static final String COLUMN_IMAGE_PATH = "IMAGE_PATH";
        public static final String COLUMN_VEHICLE_TYPE = "VEHICLE_TYPE";
    }
}

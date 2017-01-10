package com.rubahapi.iklaniku.data;

import android.provider.BaseColumns;

/**
 * Created by prasetia on 1/10/2017
 */

class SpotIklanContract {

    static final String CONTENT_AUTHORITY = "com.rubahapi.iklaniku";

    static final String PATH_SPOT_IKLAN = "spotiklan";

    static final class VehicleEntry implements BaseColumns{

        static final String PATH_VEHICLE = "vehicle";

        static final String TABLE_NAME = "VEHICLES";
        static final String COLUMN_ID = "ID";
        static final String COLUMN_NAME = "NAME";
        static final String COLUMN_PLAT_NUMBER = "PLAT_NUMBER";
        static final String COLUMN_IMAGE_PATH = "IMAGE_PATH";
        static final String COLUMN_VEHICLE_TYPE = "VEHICLE_TYPE";
    }
}

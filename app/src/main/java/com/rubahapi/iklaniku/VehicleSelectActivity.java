package com.rubahapi.iklaniku;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rubahapi.iklaniku.adapter.VehicleAdapter;
import com.rubahapi.iklaniku.data.SpotIklanContract;
import com.rubahapi.iklaniku.service.VehicleService;

public class VehicleSelectActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final  String[] VEHICLE_COLUMNS = {
            SpotIklanContract.VehicleEntry.TABLE_NAME + "." + SpotIklanContract.VehicleEntry.COLUMN_ID,
            SpotIklanContract.VehicleEntry.COLUMN_NAME,
            SpotIklanContract.VehicleEntry.COLUMN_PLAT_NUMBER,
            SpotIklanContract.VehicleEntry.COLUMN_VEHICLE_TYPE,
            SpotIklanContract.VehicleEntry.COLUMN_IMAGE_PATH
    };
    public static final int VEHICLE_LOADER = 100;

    VehicleAdapter vehicleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_select);

        RecyclerView vehicleRecyclerView = (RecyclerView) findViewById(R.id.vehicle_recycler_view);
//        vehicleRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        vehicleRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        vehicleAdapter = new VehicleAdapter(null, this);
        vehicleRecyclerView.setAdapter(vehicleAdapter);

        getLoaderManager().initLoader(VEHICLE_LOADER, null, this);

        startVehicleService();
    }

    private void startVehicleService(){
        Intent vehicleService = new Intent(this, VehicleService.class);
        startService(vehicleService);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Uri uri = SpotIklanContract.VEHICLE_CONTENT_URI;
        switch (i){
            case VEHICLE_LOADER:
                return new CursorLoader(VehicleSelectActivity.this,
                        uri,
                        VEHICLE_COLUMNS,
                        null,
                        null,
                        null);
            default:
                throw new UnsupportedOperationException("Unknown loader ID: " + i);
        }
        //return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        vehicleAdapter.updateResult(cursor);
        vehicleAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        vehicleAdapter.updateResult(null);
    }
}

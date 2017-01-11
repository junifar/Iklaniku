package com.rubahapi.iklaniku;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rubahapi.iklaniku.service.VehicleService;

public class VehicleSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_select);

        startVehicleService();
    }

    private void startVehicleService(){
        Intent vehicleService = new Intent(this, VehicleService.class);
        startService(vehicleService);
    }
}

package com.rubahapi.iklaniku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener{

    SharedPreferences sharedPreferences;
    ImageView vehicleImage;
    LinearLayout layoutQuest;
    LinearLayout profileLayout;
    Button buttonStart;
    Button buttonStop;

    TextView textViewUserName;
    TextView textViewCurrentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        textViewUserName = (TextView) findViewById(R.id.textViewUserName);
        textViewCurrentLocation = (TextView) findViewById(R.id.textViewCurrentLocation);

        vehicleImage = (ImageView) findViewById(R.id.image_vehicle);
        layoutQuest = (LinearLayout) findViewById(R.id.layout_quest);
        profileLayout = (LinearLayout) findViewById(R.id.profile_layout);
        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStop = (Button) findViewById(R.id.buttonStop);

        textViewUserName.setText(sharedPreferences.getString(getString(R.string.username),""));
//        textViewCurrentLocation.setText(sharedPreferences.getString(getString(R.string.profile_key),""));

        vehicleImage.setOnClickListener(this);
        layoutQuest.setOnClickListener(this);
        profileLayout.setOnClickListener(this);
        buttonStart.setOnClickListener(this);
        buttonStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.image_vehicle:
                imageView_OnClick();
                break;
            case R.id.layout_quest:
                layoutQuest_OnClick();
                break;
            case R.id.profile_layout:
                profileLayout_OnClick();
                break;
            case R.id.buttonStart:
                buttonStart_OnClick();
                break;
            case R.id.buttonStop:
                buttonStop_OnClick();
                break;
        }
    }

    private void buttonStop_OnClick() {
        Toast.makeText(this,"It's Works 5",Toast.LENGTH_SHORT).show();
    }

    private void buttonStart_OnClick() {
        Toast.makeText(this,"It's Works 4",Toast.LENGTH_SHORT).show();
    }

    private void profileLayout_OnClick() {
        Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
        this.startActivity(intent);
    }

    private void layoutQuest_OnClick() {
        Intent intent = new Intent(DashboardActivity.this, QuestSelectActivity.class);
        this.startActivity(intent);
    }

    private void imageView_OnClick() {
        Intent intent = new Intent(DashboardActivity.this, VehicleSelectActivity.class);
        this.startActivity(intent);
    }
}

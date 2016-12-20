package com.rubahapi.iklaniku;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        TextView textView1 = (TextView) findViewById(R.id.textView1);
        textView1.setText(sharedPreferences.getString(getString(R.string.username),""));
    }
}

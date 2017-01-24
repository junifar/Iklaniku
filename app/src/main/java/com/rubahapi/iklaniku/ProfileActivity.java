package com.rubahapi.iklaniku;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.rubahapi.iklaniku.listener.OnDatePickerClickListener;
import com.rubahapi.iklaniku.pojo.Driver;
import com.rubahapi.iklaniku.service.DriverApiService;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.rubahapi.iklaniku.R.id.birthday_edit;
import static com.rubahapi.iklaniku.config.ServerURL.BASE_API_URL;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, OnDatePickerClickListener {

    private TextView birthdayTextView;
    private TextView nameEditTextView;
    private TextView identityNumberTextView;
    private TextView driverLicenseIdTextView;

    private ProgressDialog mProgressDialog;
    SharedPreferences sharedPreferences;

    @Override
    protected void onStart() {
        showProgressDialog();
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        nameEditTextView = (TextView) findViewById(R.id.name_edit);
        identityNumberTextView = (TextView) findViewById(R.id.identity_card_id_edit);
        driverLicenseIdTextView = (TextView) findViewById(R.id.driver_license_edit);

        birthdayTextView = (TextView) findViewById(R.id.birthday_edit);
        birthdayTextView.setOnClickListener(this);
        birthdayTextView.setInputType(InputType.TYPE_NULL);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

//        showProgress(true);
        getProfileToServer(sharedPreferences.getString(getString(R.string.profile_key),""));
    }

    private void getProfileToServer(String gid){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DriverApiService driverApiService = retrofit.create(DriverApiService.class);

        Call<List<Driver>> result = driverApiService.getDriverProfileEnc(gid);

        result.enqueue(new Callback<List<Driver>>() {
            @Override
            public void onResponse(Call<List<Driver>> call, Response<List<Driver>> response) {
                for (Driver driver : response.body()){
                    nameEditTextView.setText(driver.getName());
                    identityNumberTextView.setText(driver.getIdentityNumber());
                    driverLicenseIdTextView.setText(driver.getLicenseId());
                    hideProgressDialog();
                    showAlertDialog();
                }
            }

            @Override
            public void onFailure(Call<List<Driver>> call, Throwable t) {
                t.printStackTrace();
                hideProgressDialog();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case birthday_edit:
                birthday_edit_OnClick();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_name) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    private void birthday_edit_OnClick() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
//
//        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
//                birthdayTextView.setText(dayOfMonth + "-" + (monthOfYear-1) + "-" + year);
//            }
//        },mYear, mMonth, mDay);
//
//        datePickerDialog.show();
        String birthday = birthdayTextView.getText().toString();

        DialogFragment newFragment = new DatePickerFragment();
        Bundle bundle = new Bundle();
        if(!birthday.equals("")){
            bundle.putInt("year", Integer.parseInt(splitString(birthday,2)));
            bundle.putInt("month",Integer.parseInt(splitString(birthday,1)));
            bundle.putInt("day",Integer.parseInt(splitString(birthday,0)));
        }else{
            bundle.putInt("year", mYear);
            bundle.putInt("month",mMonth);
            bundle.putInt("day",mDay);
        }

        newFragment.setArguments(bundle);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private String splitString(String value, int retNum){
        String[] result = value.split("-");
        return result[retNum];
    }

    @Override
    public void onDatePickerClickListener(int year, int month, int date) {
        birthdayTextView.setText(date + "-" + month + "-" + year);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading ...");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    private void showAlertDialog(){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("This is an alert with no consequence");
        dlgAlert.setTitle("App Title");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(false);
        dlgAlert.create().show();
    }
}

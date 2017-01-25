package com.rubahapi.iklaniku;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.rubahapi.iklaniku.listener.OnDatePickerClickListener;
import com.rubahapi.iklaniku.pojo.Driver;
import com.rubahapi.iklaniku.service.DriverApiService;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnFocusChange;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.rubahapi.iklaniku.R.id.action_done;
import static com.rubahapi.iklaniku.config.ServerURL.BASE_API_URL;

public class ProfileActivity extends AppCompatActivity implements OnDatePickerClickListener {

    @BindView(R.id.birthday_edit) TextView birthdayTextView;
    @BindView(R.id.name_edit) TextView nameEditTextView;
    @BindView(R.id.identity_card_id_edit) TextView identityNumberTextView;
    @BindView(R.id.driver_license_edit) TextView driverLicenseIdTextView;
    @BindView(R.id.address_edit) TextView addressTextView;

    private Retrofit retrofit;
    private DriverApiService driverApiService;
    private ProgressDialog mProgressDialog;
    SharedPreferences sharedPreferences;

    @Override
    protected void onStart() {
        showProgressDialog();

        getProfileToServer(sharedPreferences.getString(getString(R.string.profile_key),""));
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        birthdayTextView.setInputType(InputType.TYPE_NULL);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        driverApiService = retrofit.create(DriverApiService.class);
    }

    private void getProfileToServer(String gid){
        Call<List<Driver>> result = driverApiService.getDriverProfileEnc(gid);

        result.enqueue(new Callback<List<Driver>>() {
            @Override
            public void onResponse(Call<List<Driver>> call, Response<List<Driver>> response) {
                for (Driver driver : response.body()){
                    nameEditTextView.setText(driver.getName());
                    identityNumberTextView.setText(driver.getIdentityNumber());
                    driverLicenseIdTextView.setText(driver.getLicenseId());
                    birthdayTextView.setText(driver.getBirthday());
                    addressTextView.setText(driver.getAddress());
                    hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<List<Driver>> call, Throwable t) {
                t.printStackTrace();
                hideProgressDialog();
            }
        });
    }

    @OnFocusChange(R.id.birthday_edit)
    public void OnClickBirthdayEdit(View view){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == action_done) {
            action_done_click();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void action_done_click() {
        showProgressDialog();
        Log.d("Data Sent", sharedPreferences.getString(getString(R.string.profile_key),""));
        Call<Driver> result = driverApiService.updateDriverRecord(sharedPreferences.getString(getString(R.string.profile_key),""),
                nameEditTextView.getText().toString(), addressTextView.getText().toString(),
                identityNumberTextView.getText().toString(), driverLicenseIdTextView.getText().toString());

        result.enqueue(new Callback<Driver>() {
            @Override
            public void onResponse(Call<Driver> call, Response<Driver> response) {
                hideProgressDialog();
                showAlertDialog("Update Success");
            }

            @Override
            public void onFailure(Call<Driver> call, Throwable t) {
                t.printStackTrace();
                hideProgressDialog();
                showAlertDialog("No data Found");
            }
        });

//        Toast.makeText(ProfileActivity.this,"Its work's", Toast.LENGTH_SHORT).show();
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

    private void showAlertDialog(String message){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage(message);
        dlgAlert.setTitle("Information");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(false);
        dlgAlert.create().show();
    }
}

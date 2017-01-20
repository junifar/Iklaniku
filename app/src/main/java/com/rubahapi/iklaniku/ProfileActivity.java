package com.rubahapi.iklaniku;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
    private ProgressDialog mProgressDialog;
    SharedPreferences sharedPreferences;
    private View profileLayout;
    private View progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        birthdayTextView = (TextView) findViewById(R.id.birthday_edit);
        birthdayTextView.setOnClickListener(this);
        birthdayTextView.setInputType(InputType.TYPE_NULL);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        profileLayout = findViewById(R.id.profile_layout);
        progressView = findViewById(R.id.profile_progress);

//        showProgress(true);
        getProfileToServer(sharedPreferences.getString(getString(R.string.google_id),""),sharedPreferences.getString(getString(R.string.username),""));
    }

    private void getProfileToServer(String gid, String name){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DriverApiService driverApiService = retrofit.create(DriverApiService.class);

        Call<List<Driver>> result = driverApiService.getDriverProfile(gid, name);

        result.enqueue(new Callback<List<Driver>>() {
            @Override
            public void onResponse(Call<List<Driver>> call, Response<List<Driver>> response) {
                Toast.makeText(ProfileActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Driver>> call, Throwable t) {
                t.printStackTrace();
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
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2){

            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            profileLayout.setVisibility(show ? View.GONE : View.VISIBLE);
            profileLayout.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(
                    new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {}

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            profileLayout.setVisibility(show ? View.GONE : View.VISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {}

                        @Override
                        public void onAnimationRepeat(Animator animator) {}
                    });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(
                    new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                        }
                    });
        }else {
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            profileLayout.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}

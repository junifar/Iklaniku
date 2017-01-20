package com.rubahapi.iklaniku;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.rubahapi.iklaniku.pojo.Driver;
import com.rubahapi.iklaniku.service.DriverApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.rubahapi.iklaniku.config.ServerURL.BASE_API_URL;

public class QuestSelectActivity extends AppCompatActivity {

//    public static final String BASE_API_URL = "http://transpot.id:9999";

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_select);


        showProgressDialog();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DriverApiService driverApiService = retrofit.create(DriverApiService.class);

//        Call<List<Driver>> result = driverApiService.getDriversInfo();
//        result.enqueue(new Callback<List<Driver>>() {
//            @Override
//            public void onResponse(Call<List<Driver>> call, Response<List<Driver>> response) {
//                for (Driver driver :
//                        response.body()) {
//                    Toast.makeText(QuestSelectActivity.this, driver.getName(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Driver>> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });

        Call<List<Driver>> result = driverApiService.insertDriverRecord("gid",
                "name",
                "address",
                "identity_number",
                "license_id");

        result.enqueue(new Callback<List<Driver>>() {
            @Override
            public void onResponse(Call<List<Driver>> call, Response<List<Driver>> response) {
                int res = response.code();
                if(res == 500){
                    Toast.makeText(QuestSelectActivity.this, "Duplicate Entry", Toast.LENGTH_SHORT).show();
                }else{
                Toast.makeText(QuestSelectActivity.this, "Success", Toast.LENGTH_SHORT).show();}
            }

            @Override
            public void onFailure(Call<List<Driver>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading ...");
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }
}

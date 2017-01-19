package com.rubahapi.iklaniku;

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

public class QuestSelectActivity extends AppCompatActivity {

    public static final String BASE_API_URL = "http://transpot.id:9999";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_select);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DriverApiService driverApiService = retrofit.create(DriverApiService.class);

//        Call<ResponseBody> result = driverApiService.getDriversInfoJSON();
//        result.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Toast.makeText(QuestSelectActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });


        Call<List<Driver>> result = driverApiService.getDriversInfo();
        result.enqueue(new Callback<List<Driver>>() {
            @Override
            public void onResponse(Call<List<Driver>> call, Response<List<Driver>> response) {
                for (Driver driver :
                        response.body()) {
                    Toast.makeText(QuestSelectActivity.this, driver.getName(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Driver>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}

package com.rubahapi.iklaniku.service;

import com.rubahapi.iklaniku.pojo.Driver;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by prasetia on 1/19/2017.
 */

public interface DriverApiService {

    @GET("api/drivers")
    Call<List<Driver>> getDriversInfo();

    @GET("api/drivers")
    Call<ResponseBody> getDriversInfoJSON();
}

package com.rubahapi.iklaniku.service;

import com.rubahapi.iklaniku.pojo.Driver;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by prasetia on 1/19/2017.
 */

public interface DriverApiService {

    @GET("api/drivers")
    Call<List<Driver>> getDriversInfo();

    @FormUrlEncoded
    @POST("api/driver/insert")
    Call<List<Driver>> insertDriverRecord(@Field("gid") String gid,
                                          @Field("name") String name,
                                          @Field("address") String address,
                                          @Field("identity_number") String identity_number,
                                          @Field("license_id") String license_id);

    @FormUrlEncoded
    @POST("api/driver/profile/{gid}")
    Call<List<Driver>> getDriverProfile(@Path("gid") String gid,
                                  @Field("name") String name);
}

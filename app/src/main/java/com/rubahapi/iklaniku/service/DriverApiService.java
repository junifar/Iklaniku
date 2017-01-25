package com.rubahapi.iklaniku.service;

import com.rubahapi.iklaniku.pojo.Driver;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    @POST("api/driver/update/{gid}")
    Call<Driver> updateDriverRecord(@Path("gid") String id,
                                          @Field("name") String name,
                                          @Field("address") String address,
                                          @Field("identity_number") String identity_number,
                                          @Field("license_id") String license_id);

    @FormUrlEncoded
    @POST("api/driver/profile/{gid}")
    Call<List<Driver>> getDriverProfile(@Path("gid") String gid,
                                  @Field("name") String name);

    @POST("api/driver/profile/key/{gid}")
    Call<ResponseBody> getDriverProfileKey(@Path("gid") String gid);

    @POST("api/driver/profile/get/{gid}")
    Call<List<Driver>> getDriverProfileEnc(@Path("gid") String gid);

    @Multipart
    @POST("api/driver/profile/image/upload/{gid}")
    Call<List<Driver>> upladImageProfile(@Path("gid") String gid, @Part("image") RequestBody file);
}

package com.ibmwebapi;

import com.ibmwebapi.data.PostTemperatureResponse;
import com.ibmwebapi.data.UserData;

import java.util.List;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GetDataService {

    @GET("declaration")
    Call<List<UserData>> getUserData(@Query("declarationID") String declarationID,
                                     @Query("IDNo") String IDNo,
                                     @Query("individualName") String individualName,
                                     @Query("dateFrom") String dateFrom,
                                     @Query("dateTo") String dateTo,
                                     @Query("institutionCode") String institutionCode,
                                     @Query("pageNo") String pageNo,
                                     @Query("pageSize") String pageSize,
                                     @Query("loginName") String loginName,
                                     @Query("includeArchive") String includeArchive);

    @POST("declarationtemperature")
    Call<PostTemperatureResponse> postTemperature(@Body TreeMap<String, String> parameters);
}

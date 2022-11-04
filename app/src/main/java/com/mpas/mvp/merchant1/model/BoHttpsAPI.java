package com.mpas.mvp.merchant1.model;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface BoHttpsAPI {


    @GET("/mpas/v1/site/info") // Merchant Download
    Single<Merchant> getMerchant(
            @Header("authorization") String authorization,
            @Query("businessnumber") String businessnumber,
            @Query("siteid") String siteid
    );

    @GET("/mpas/v1/trans/sum") // Merchant Sales Query
    Single<SalesModel> getSales(
            @Header("authorization") String authorization,
            @Query("businessnumber") String businessnumber,
            @Query("siteid") String siteid,
            @Query("sdate") String sdate,
            @Query("edate") String edate
    );

    @GET("/mpas/v1/trans/sum/detail") // Merchant Sales Purchase Query
    Single<SalesDetailModel> getISalesPurchase(
            @Header("authorization") String authorization,
            @Query("businessnumber") String businessnumber,
            @Query("siteid") String siteid,
            @Query("transdate") String transdate
    );

    @GET("/mpas/v1/trans/list") // Merchant sales Purchase Detail Query
    Call<Object> getSalesPurchaseDetail(
            @Query("businessnumber") String businessnumber,
            @Query("siteid") String siteid,
            @Query("transdate") String transdate,
            @Query("page") int page,
            @Query("rowcount") int rowcount,
            @Query("token") String token
    );

    @GET("/bank/info") // Merchant IssuerNumber Query
    Call<Object> getBankInfo(
            @Query("businessnumber") String businessnumber,
            @Query("siteid") String siteid,
            @Query("ficode") String ficode,
            @Query("token") String token
    );
}

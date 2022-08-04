package com.mpas.mvp.merchant1.model;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BoHttpsAPI {

    public static final String API_URL = "https://api.jtnet.co.kr/";
    public static final String KEY =  "3O421T9V6hxb5dnGSuDVd27BFRsnQYYS";

    @GET("payda/v1/site/info") // Merchant Download
    Single<MerchantInfoModel> getMerchant(
            @Query("businessnumber") String businessnumber,
            @Query("siteid") String siteid,
            @Query("token") String token
    );

    @GET("payda/v1/bank/info") // Merchant IssuerNumber Query
    Call<Object> getBankInfo(
            @Query("businessnumber") String businessnumber,
            @Query("siteid") String siteid,
            @Query("ficode") String ficode,
            @Query("token") String token
    );

    @GET("payda/v1/trans/sum") // Merchant Sales Query
    Single<SalesModel> getSales(
            @Query("businessnumber") String businessnumber,
            @Query("siteid") String siteid,
            @Query("sdate") String sdate,
            @Query("edate") String edate,
            @Query("token") String token
    );

    @GET("payda/v1/trans/sum/detail") // Merchant Sales Purchase Query
    Single<SalesDetailModel> getISalesPurchase(
            @Query("businessnumber") String businessnumber,
            @Query("siteid") String siteid,
            @Query("transdate") String transdate,
            @Query("token") String token
    );

    @GET("payda/v1/trans/list") // Merchant sales Purchase Detail Query
    Call<Object> getSalesPurchaseDetail(
            @Query("businessnumber") String businessnumber,
            @Query("siteid") String siteid,
            @Query("transdate") String transdate,
            @Query("page") int page,
            @Query("rowcount") int rowcount,
            @Query("token") String token
    );
}

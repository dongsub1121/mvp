package com.mpas.mvp.merchant.api;

import com.google.gson.JsonObject;
import com.mpas.mvp.merchant.model.MerchantInfoModel;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BoHttpsAPI {

    public static final String API_URL = "https://api.jtnet.co.kr/";
    public static final String KEY =  "3O421T9V6hxb5dnGSuDVd27BFRsnQYYS";
    public static final String SERVICE_TYPE = "P";


    @GET("payda/v1/site/info") // Merchant Download
    Single<MerchantInfoModel> getMerchantInfo(
            @Query("servicetype") String servicetype,
            @Query("businessnumber") String businessnumber,
            @Query("siteid") String siteid,
            @Query("token") String token
    );

/*

    @GET("payda/v1/site/info") // Merchant Download
    Call<MerchantInfoModel> getMerchantInfo(
            @Query("servicetype") String servicetype,
            @Query("businessnumber") String businessnumber,
            @Query("siteid") String siteid,
            @Query("token") String token
    );
*/

    @GET("payda/v1/bank/info") // Merchant IssuerNumber Query
    Call<Object> getBankInfo(
            @Query("servicetype") String servicetype,
            @Query("businessnumber") String businessnumber,
            @Query("siteid") String siteid,
            @Query("ficode") String ficode,
            @Query("token") String token
    );

    @GET("payda/v1/trans/sum") // Merchant Sales Query
    Call<Object> getSales(
            @Query("servicetype") String servicetype,
            @Query("businessnumber") String businessnumber,
            @Query("sdate") String sdate,
            @Query("edate") String edate,
            @Query("token") String token
    );

    @GET("payda/v1/trans/sum/detail") // Merchant Sales Purchase Query
    Call<Object> getISalesPurchase(
            @Query("servicetype") String servicetype,
            @Query("businessnumber") String businessnumber,
            @Query("transdate") String transdate,
            @Query("token") String token
    );

    @GET("payda/v1/trans/list") // Merchant sales Purchase Detail Query
    Call<Object> getSalesPurchaseDetail(
            @Query("servicetype") String servicetype,
            @Query("businessnumber") String businessnumber,
            @Query("transdate") String transdate,
            @Query("page") int page,
            @Query("rowcount") int rowcount,
            @Query("token") String token
    );


}

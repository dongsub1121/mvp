package com.mpas.mvp.merchant1.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.mpas.mvp.merchant1.Config;
import com.mpas.mvp.merchant1.model.BoHttpsAPI;
import com.mpas.mvp.merchant1.model.MerchantInfoModel;
import com.mpas.mvp.merchant1.model.SalesModel;
import com.mpas.mvp.merchant1.model.SalseDetailModel;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.Month;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRepository {
    private  static final  String BASE_URL = "https://api.jtnet.co.kr/";
    private static final  String SERVICE_TYPE =  "P";
    private static final String TOKEN = "3O421T9V6hxb5dnGSuDVd27BFRsnQYYS";
    private static String biz;
    private static String mid;

    private static ApiRepository instance;

    public ApiRepository(Context context) {
        PreferenceRepository repo = new PreferenceRepository(context);
        SharedPreferences sharedPreferences = repo.getSharedPreferences();
        biz = sharedPreferences.getString(Config.BUSINESS_ID,"");
        mid = sharedPreferences.getString(Config.MERCHANT_ID,"");
    }

    public static ApiRepository getInstance(Context context) {
        if ( instance == null){
            instance = new ApiRepository(context);
        }
        return instance;
    }

    public BoHttpsAPI api = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(BoHttpsAPI.class);

    public static void setMerchantData(String biz, String mid){
        ApiRepository.biz = biz;
        ApiRepository.mid = mid;
    }

    public Single<MerchantInfoModel> getMerchantInfo() {
        String value = SERVICE_TYPE+ biz + mid;
        String sha = sha256(sha256(value)+ TOKEN);
        Log.e("token",sha);

        return api.getMerchantInfo(SERVICE_TYPE, biz, mid, sha);
    }

    public Single<MerchantInfoModel> getMerchantInfo(String biz, String mid) {
        String value = SERVICE_TYPE+ biz + mid;
        String sha = sha256(sha256(value)+ TOKEN);
        Log.e("token",sha);

        return api.getMerchantInfo(SERVICE_TYPE, biz, mid, sha);
    }

    public SalesModel getSales() {

        String toDay = "20220701";//getStartDate();
        String sDay = "20220702";//getStartDate();
        String value = SERVICE_TYPE+ biz +toDay+sDay ;
        Log.e("getSales_value",value);
        String sha = sha256(sha256(value)+ TOKEN);
        SalesModel salesModel = new SalesModel();
        Log.e("token",sha);
/*
        api.getSalse(SERVICE_TYPE,biz,toDay,toDay,sha).enqueue(new Callback<SalesModel>() {
            @Override
            public void onResponse(Call<SalesModel> call, Response<SalesModel> response) {

                Log.e("getDetail",response.body().getDetail());
                Log.e("getStatus",response.body().getStatus());
                Log.e("getMessage",response.body().getMessage());
                Log.e("getSaleDBList",response.body().getSaleDBList().get(0).toString());


            }

            @Override
            public void onFailure(Call<SalesModel> call, Throwable t) {
                Log.e("onFailure", t.toString());
                t.printStackTrace();
            }
        });*/
        //SalesModel) api.getSalse(SERVICE_TYPE, biz,toDay,toDay,sha);

        return salesModel;
    }
    public String sha256(String value) {

        String SHA = "";
        try{
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(value.getBytes(StandardCharsets.UTF_8));
            byte[] byteData = sh.digest();
            StringBuilder sb = new StringBuilder();
            for (byte byteDatum : byteData)
                sb.append(Integer.toString((byteDatum & 0xff) + 0x100, 16).substring(1));
            SHA = sb.toString();
        }catch(NoSuchAlgorithmException e) { e.printStackTrace(); SHA = null; }


        return SHA;
    }


    public Single<SalseDetailModel> getSale_purchase() {

        String toDay = getStartDate();
        String value = SERVICE_TYPE+ biz +toDay ;
        Log.e("value",value);
        String sha = sha256(sha256(value)+ TOKEN);
        Log.e("getSale_purchase",sha);

        return api.getISalesPurchase(SERVICE_TYPE,biz,toDay,sha);
    }

    @SuppressLint("DefaultLocale")
    public String getToDay(){
        int year = 0, month = 0, toDay = 0, startDay = 0;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate currDate = LocalDate.now();
            year = currDate.getYear();
            Month mon = currDate.getMonth();
            month = mon.getValue();
            toDay = currDate.getDayOfMonth();
            startDay = toDay-1;

            Log.e("getToday",String.format("%04d%02d%02d",year, month,toDay));
            if ( toDay > 2){
                startDay = toDay-1;
            }else if( toDay <= 1){
                startDay = toDay;
            }

        }

    return String.format("%04d%02d%02d",year, month,toDay);
    }

    @SuppressLint("DefaultLocale")
    public String getStartDate() {
        int year = 0, month = 0, toDay = 0, startDay = 0;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate currDate = LocalDate.now();
            year = currDate.getYear();
            Month mon = currDate.getMonth();
            month = mon.getValue();
            toDay = currDate.getDayOfMonth();
            startDay = toDay - 1;

            if (toDay > 2) {
                startDay = toDay - 1;
            } else if (toDay <= 1) {
                startDay = toDay;
            }
        }
        Log.e("getStartDate",String.format("%04d%02d%02d",year, month,startDay));
        return String.format("%04d%02d%02d", year, month, startDay);
    }

}

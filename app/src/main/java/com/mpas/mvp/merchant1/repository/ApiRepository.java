package com.mpas.mvp.merchant1.repository;

import android.annotation.SuppressLint;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.mpas.mvp.merchant.api.BoHttpsAPI;
import com.mpas.mvp.merchant1.model.MerchantInfoModel;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.Month;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRepository {
    private  static final  String BASE_URL = "https://api.jtnet.co.kr/";
    private static final  String SERVICE_TYPE =  "P";
    private static final String TOKEN = "3O421T9V6hxb5dnGSuDVd27BFRsnQYYS";
    private static String biz = null;
    private static String mid = null;

    private static ApiRepository instance;

    public BoHttpsAPI api = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(BoHttpsAPI.class);

    public ApiRepository() {

    }

    public static ApiRepository getInstance() {
        if ( instance == null){
            instance = new ApiRepository();
        }
        return instance;
    }

    public static void setMerchantData(String biz, String mid){
        ApiRepository.biz = biz;
        ApiRepository.mid = mid;
    }

    public Single<MerchantInfoModel> getMerchantInfo() {
        String value = SERVICE_TYPE+ biz + mid;
        String sha = sha256(sha256(value)+ TOKEN);

        return api.getMerchantInfo(SERVICE_TYPE, biz, mid, sha);
    }

    public Single<MerchantInfoModel> getMerchantInfo(String biz, String mid) {
        String value = SERVICE_TYPE+ biz + mid;
        String sha = sha256(sha256(value)+ TOKEN);

        return api.getMerchantInfo(SERVICE_TYPE, biz, mid, sha);
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

    @SuppressLint("DefaultLocale")
    public String getDate(){
        int year = 0, month = 0, toDay = 0, startDay = 0;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate currDate = LocalDate.now();
            year = currDate.getYear();
            Month mon = currDate.getMonth();
            month = mon.getValue();
            toDay = currDate.getDayOfMonth();

            if ( toDay > 2){
                startDay = toDay-1;
            }else if( toDay <= 1){
                startDay = toDay;
            }
            //ManagementViewModel.setEndDate(String.format("%04d%02d%02d",year, month,toDay));
        }
    return String.format("%04d%02d%02d",year, month,toDay);
    }
}

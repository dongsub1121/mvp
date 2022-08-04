package com.mpas.mvp.merchant1.repository;

import static com.mpas.mvp.merchant1.util.TextConvert.*;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.mpas.mvp.merchant1.util.Config;
import com.mpas.mvp.merchant1.model.BoHttpsAPI;
import com.mpas.mvp.merchant1.model.MerchantInfoModel;
import com.mpas.mvp.merchant1.model.SalesModel;
import com.mpas.mvp.merchant1.model.SalesDetailModel;
import com.mpas.mvp.merchant1.util.TextConvert;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRepository {
    private  static final  String BASE_URL = "https://api.jtnet.co.kr/";
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
        String value = biz + mid;
        String sha = sha256(sha256(value)+ TOKEN);
        Log.e("token",sha);

        return api.getMerchant(biz, mid, sha);
    }

    public Single<MerchantInfoModel> getMerchantInfo(String biz, String mid) {
        String value = biz + mid;
        String sha = sha256(sha256(value)+ TOKEN);
        Log.e("token",sha);

        return api.getMerchant(biz, mid, sha);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Single<SalesModel> getSalesSummary(String startDate, String endDate) {

        String start , end;

        if(startDate == null || startDate.equals("")) {
            start = TextConvert.toString(toDay().minusDays(8));
        }else{
            start = startDate;
        }

        if(endDate ==null || endDate.equals("")) {
            end = TextConvert.toString(toDay().minusDays(1));
        }else {
            end = endDate;
        }

        //109515144818141122882022072420220731
        //String value = "1515125204" + "1481900115" + start + end; //422:'token'(query) 오류. 입력값: '98a43212309a464a43956ddbccaf2f392a540c8539119a809dd91160f87ef10a':요청 변수에 오류가 있습니다.
        String value = biz+ mid+ start + end; // "1078155843" + "1802100001"  에러없이 가능
        Log.e("getSales_value",value);
        String sha = sha256(sha256(value)+ TOKEN);
        Log.e("token",sha);

        return api.getSales(biz,mid,start,end,sha);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Single<SalesDetailModel> getSale_purchase() {

        String toDay = TextConvert.toString(toDay().minusDays(1));
        //String value = "1095151448" + "1814112288" + toDay ; // 임시로 본죽 오목교점 사용
        //String mid = "";
        String value  = biz + mid + toDay;
        String sha = sha256(sha256(value)+ TOKEN);
        Log.e("value: sha",value+":"+sha);

        return api.getISalesPurchase(biz,mid,toDay,sha);
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


}

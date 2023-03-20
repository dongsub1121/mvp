package com.mpas.mvp.ui.main.payments;


import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.mpas.mvp.PaydaPostMPMCommit;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroRepository {

    private  static final  String BASE_URL = "https://d-pay.mpas.co.kr/"; //http://61.33.183.227:3000/";
    private static final String _uid = "MzQ4NTUwOTk5EUGB";
    private static RetroRepository instance;

    public static RetroRepository getInstance() {
        if ( instance == null){
            instance = new RetroRepository();
        }
        return instance;
    }

    public RetrofitAPI api = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(RetrofitAPI.class);

    public Single<List<MpasApiModel>> getMpasItem(String uid) {
        Log.e("Repo","getMpasItem");
        return api.mpasGetInfo(_uid,uid);
    }

    public Single<Object> retroCommit(String uid, String mpas) {
        return api.mpasPaymentCommit(_uid,uid,mpas);
    }

}

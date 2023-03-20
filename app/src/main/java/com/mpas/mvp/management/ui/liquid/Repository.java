package com.mpas.mvp.management.ui.liquid;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.mpas.mvp.merchant1.model.BoHttpsAPI;
import com.mpas.mvp.merchant1.repository.ApiRepository;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {
    private  static final  String BASE_URL = "https://sandbox.api.liquidpay.com";
    private static final String API_KEY = "HFpKWT6DpguFgwig4fuIUIL3sUtF9rY6";

    private static Repository instance;

    public Repository(Context context) {
        super();
    }

    public static Repository getInstance(Context context) {
        if ( instance == null){
            instance = new Repository(context);
        }
        return instance;
    }

    public LiquidAPI api = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(LiquidAPI.class);

    public Single<String> getPayLoad() {
        return api.getPayLoad(API_KEY);
    }
}

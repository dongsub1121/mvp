package com.mpas.mvp.model;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.mpas.mvp.BoHttpsAPI;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MerchantInfoService {
    private  static final  String BASE_URL = "https://api.jtnet.co.kr/";
    private static final  String SERVICE_TYPE = BoHttpsAPI.SERVICE_TYPE;
    private static final String TOKEN = BoHttpsAPI.KEY;

    private static MerchantInfoService instance;

    public BoHttpsAPI api = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(BoHttpsAPI.class);

    public static MerchantInfoService getInstance() {
        if ( instance == null){
            instance = new MerchantInfoService();
        }
        return instance;
    }

    public Single<MerchantInfo> getMerchantInfo( String businessnumber, String siteid) {
        Single<MerchantInfo> merchantInfo = Single.create(emitter ->)
                api.getMerchantInfo(SERVICE_TYPE, businessnumber, siteid, TOKEN);

        return merchantInfo.
    }
}

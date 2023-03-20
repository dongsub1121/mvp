package com.mpas.mvp.management.ui.liquid;

import com.mpas.mvp.merchant1.model.Merchant;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface LiquidAPI {

    @GET("v1/liquidnet/acquirer/qr/payloadtypes") //
    Single<String> getPayLoad(
            @Header("Liquid-Api-Key") String apiKey
    );

    @GET("v1/liquidnet/acquirer/qr/payloadtypes") //
    Call<Object> getPayLoads(
            @Header("Liquid-Api-Key") String apiKey
    );
}

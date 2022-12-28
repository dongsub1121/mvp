package com.mpas.mvp.ui.main.payments;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitAPI {

    @FormUrlEncoded
    @POST("jtnet/pos/trade/info")
    Call<Object> posGetInfoBody(
            @Field("_uid") String uid,
            @Field("uid") String mid
    );

    @FormUrlEncoded
    @POST("/jtnet/pos/trade/commit")
    Call<Object> mpmCommit(
            @Field("_uid") String uid,
            @Field("uid") String mid,
            @Field("pack") String pack
    );

    @FormUrlEncoded
    @POST("jtnet/pos/trade/info")
    Single<List<MpasApiModel>> mpasGetInfo(
            @Field("_uid") String _uid,
            @Field("uid") String uid
    );

    @FormUrlEncoded
    @POST("/jtnet/pos/trade/commit")
    Single<Object> mpasPaymentCommit(
            @Field("_uid") String _uid,
            @Field("uid") String uid,
            @Field("pack") String pack
    );
}

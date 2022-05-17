package com.mpas.mvp;

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
}

package com.mpas.mvp;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mpas.mvp.model.GetItem;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRequest {

    private GetItem item;
    private GetItem.Prev prev;

    public void HttpsPostGetInfo(String _uid, String uid) {

        Bundle bundle = new Bundle();
        Message message = Message.obtain();
        message.what = 1;

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://211.32.72.243:3000/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);


        retrofitAPI.posGetInfoBody(_uid, uid).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NotNull Call<Object> call, @NotNull Response<Object> response) {
                String s = new Gson().toJson(response.body());
                String body = s.substring(1, s.length() - 1);
                item = gson.fromJson(body, GetItem.class);
                String prevString = item.getPrev().substring(1, item.getPrev().length() - 1);
                GetItem.Prev prev = gson.fromJson(prevString, GetItem.Prev.class);
                Log.e("prev",prev.getMenu()+":::"+prev.getCnt()+":::"+prev.getPrice());
            }

            @Override
            public void onFailure(@NotNull Call<Object> call, @NotNull Throwable t) {
                Log.e("GET INFO FAIL", t.getMessage());
            }
        });
    }

    public void HttpsMPMCommit(String uid, String mid, String pack, boolean isValidate ) {

        if(isValidate){
            Bundle bundle = new Bundle();
            Message message = Message.obtain();
            message.what = 3;
            bundle.putString("uid", uid);
            bundle.putString("mid", mid);
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://211.32.72.243:3000/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);


            retrofitAPI.mpmCommit(uid, mid, pack).enqueue(new Callback<Object>() {
                @Override
                public void onResponse(@NotNull Call<Object> call, @NotNull Response<Object> response) {
                    String s = new Gson().toJson(response.body());
                    String body = s.substring(1, s.length() - 1);
                    Log.e("onResponse", body);

                }

                @Override
                public void onFailure(@NotNull Call<Object> call, @NotNull Throwable t) {

                }
            });
        }

    }
}

package com.mpas.mvp.ui.main;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mpas.mvp.BoHttpsAPI;
import com.mpas.mvp.model.MerchantInfo;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ManagementViewModel extends ViewModel {

    public static String businessID = "";
    public static String merchantID = "";
    public static String fiCode = "";
    public static String startDate = "";
    public static String endDate = "";

    public static String getFiCode() {
        return fiCode;
    }

    public static String getStartDate() {
        return startDate;
    }

    public static void setStartDate(String startDate) {
        ManagementViewModel.startDate = startDate;
    }

    public static String getEndDate() {
        return endDate;
    }

    public static void setEndDate(String endDate) {
        ManagementViewModel.endDate = endDate;
    }

    public static void setFiCode(String fiCode) {
        ManagementViewModel.fiCode = fiCode;
    }

    public static String getBusinessID() {
        return businessID;
    }

    public static void setBusinessID(String businessID) {
        ManagementViewModel.businessID = businessID;
    }

    public static String getMerchantID() {
        return merchantID;
    }

    public static void setMerchantID(String merchantID) {
        ManagementViewModel.merchantID = merchantID;
    }

    private final MutableLiveData<String> merchantInfoMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> merchantIssuerMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> merchantSalesMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> merchantPurchaseSalesMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<String> getMerchantInfoMutableLiveData() {
        return merchantInfoMutableLiveData;
    }

    public void getMerchantInfo() {

        String value = BoHttpsAPI.SERVICE_TYPE+ businessID + merchantID;
        String token = BoHttpsAPI.KEY;
        String sha = sha256(sha256(value)+token);

        Gson gson = new GsonBuilder().setPrettyPrinting()
                //.setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BoHttpsAPI.API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        BoHttpsAPI httpsAPI = retrofit.create(BoHttpsAPI.class);

        httpsAPI.getMerchantInfo(BoHttpsAPI.SERVICE_TYPE, businessID, merchantID, sha).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                String body = gson.toJson(response.body());
                Log.e("response.body()",body);
                merchantInfoMutableLiveData.postValue(body);
                MerchantInfo merchantInfo = gson.fromJson(body,MerchantInfo.class);
                JsonObject jsonObject = merchantInfo.getResult();
                Log.e("getResult()",jsonObject.toString());

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("GET INFO FAIL", t.getMessage());
            }
        });
    }

    public void getBankInfo() {

        String value = BoHttpsAPI.SERVICE_TYPE+ businessID + merchantID + fiCode;
        String token = BoHttpsAPI.KEY;
        String sha = sha256(sha256(value)+token);

        Gson gson = new GsonBuilder().setPrettyPrinting()
                //.setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BoHttpsAPI.API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        BoHttpsAPI httpsAPI = retrofit.create(BoHttpsAPI.class);

        httpsAPI.getBankInfo(BoHttpsAPI.SERVICE_TYPE, businessID, merchantID, fiCode, sha).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                String body = gson.toJson(response.body());
                Log.e("response.body()",body);
                merchantInfoMutableLiveData.postValue(body);
                MerchantInfo merchantInfo = gson.fromJson(body,MerchantInfo.class);
                JsonObject jsonObject = merchantInfo.getResult();
                Log.e("getResult()",jsonObject.toString());

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("GET INFO FAIL", t.getMessage());
            }
        });
    }

    public void getQueryISales() {

        String value = BoHttpsAPI.SERVICE_TYPE+ businessID + startDate + endDate;
        String token = BoHttpsAPI.KEY;
        String sha = sha256(sha256(value)+token);
        Log.e("value",value);

        Gson gson = new GsonBuilder().setPrettyPrinting()
                //.setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BoHttpsAPI.API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        BoHttpsAPI httpsAPI = retrofit.create(BoHttpsAPI.class);

        httpsAPI.getSales(BoHttpsAPI.SERVICE_TYPE, businessID, startDate, endDate, sha).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                String body = gson.toJson(response.body());
                Log.e("response.body()",body);
                merchantInfoMutableLiveData.postValue(body);

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("GET INFO FAIL", t.getMessage());
            }
        });
    }

    public void getQueryISalesPurchase() {

        String value = BoHttpsAPI.SERVICE_TYPE+ businessID + endDate;
        String token = BoHttpsAPI.KEY;
        String sha = sha256(sha256(value)+token);
        Log.e("value",value);

        Gson gson = new GsonBuilder().setPrettyPrinting()
                //.setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BoHttpsAPI.API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        BoHttpsAPI httpsAPI = retrofit.create(BoHttpsAPI.class);

        httpsAPI.getISalesPurchase(BoHttpsAPI.SERVICE_TYPE, businessID, endDate, sha).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                String body = gson.toJson(response.body());
                Log.e("response.body()",body);
                merchantInfoMutableLiveData.postValue(body);

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("GET INFO FAIL", t.getMessage());
            }
        });
    }

    public void getQueryISalesPurchaseDetail() {

        String value = BoHttpsAPI.SERVICE_TYPE+ businessID + endDate + fiCode + 1 + 10 ;
        String token = BoHttpsAPI.KEY;
        String sha = sha256(sha256(value)+token);
        Log.e("value",value);

        Gson gson = new GsonBuilder().setPrettyPrinting()
                //.setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BoHttpsAPI.API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        BoHttpsAPI httpsAPI = retrofit.create(BoHttpsAPI.class);

        httpsAPI.getSalesPurchaseDetail(BoHttpsAPI.SERVICE_TYPE, businessID, endDate,1,10, sha).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                String body = gson.toJson(response.body());
                Log.e("response.body()",body);
                merchantInfoMutableLiveData.postValue(body);

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("GET INFO FAIL", t.getMessage());
            }
        });

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
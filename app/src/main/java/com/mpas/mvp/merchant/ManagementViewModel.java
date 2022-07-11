package com.mpas.mvp.merchant;

import static com.mpas.mvp.merchant1.model.MerchantInfoModel.*;
import static com.mpas.mvp.merchant1.repository.ApiRepository.*;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mpas.mvp.merchant1.model.BanksModel;
import com.mpas.mvp.merchant1.model.MerchantInfoModel;
import com.mpas.mvp.merchant1.repository.ApiRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ManagementViewModel extends AndroidViewModel {

    private final ApiRepository apiRepository = getInstance(getApplication());
    private final MutableLiveData<Result> merchantInfoMutableLiveData = new MutableLiveData<>();
    public final MutableLiveData<List<BanksModel>> banksMutableLiveData = new MutableLiveData<List<BanksModel>>();

    private final MutableLiveData<String> merchantIssuerMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> merchantSalesMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> merchantPurchaseSalesMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Result> getMerchantInfoMutableLiveData() {
        return merchantInfoMutableLiveData;
    }

    public MutableLiveData<List<BanksModel>> getBanksMutableLiveData() {
        return banksMutableLiveData;
    }

    public MutableLiveData<Boolean> loadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private final CompositeDisposable disposable = new CompositeDisposable();

    public ManagementViewModel(@NonNull Application application) {
        super(application);
    }

    public void refresh() {
        // 서버로부터 데이터를 받아오는 동안 로딩 상태를 보여주기 위해 true 설정
        Log.e("viewModel","refresh");
        loading.setValue(true);
        // CompositeDisposable에 Observable(여기선 Single) 추가
        disposable.add(apiRepository.getMerchantInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<MerchantInfoModel>() {


                    @Override
                    public void onSuccess(MerchantInfoModel merchantInfoModel) {
                        // 데이터가 있다면 MutableLiveData<List>에 데이터를 넣고
                        Log.e("refresh",merchantInfoModel.getResult().getBusinessnumber());
                        Log.e("onSuccess",merchantInfoModel.toString());
                        merchantInfoMutableLiveData.setValue(merchantInfoModel.getResult());
                        banksMutableLiveData.setValue(merchantInfoModel.getResult().getBanks());

                        // 로딩, 에러 관련 뷰들을 가리기 위해 false 값을 넣는다
                        loadError.setValue(false);
                        loading.setValue(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("refresh","onError");
                        // 실패한 경우는 성공했을 때와 반대로 한다
                        loadError.setValue(true);
                        loading.setValue(false);
                        Log.e("refresh", String.valueOf(e));

                    }
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        // 앱이 통신 중에 프로세스가 종료될 경우(앱이 destory됨)
        // 메모리 손실을 최소화 하기 위해 백그라운드 스레드에서 통신 작업을 중단한다
        disposable.clear();
    }

    public void setMerchant(String biz, String mid) {
        setMerchantData(biz,mid);
    }

    public void getMerchantInfo(){

    }
}
/*
    @Override
    protected void onCleared() {
        super.onCleared();

        // 앱이 통신 중에 프로세스가 종료될 경우(앱이 destory됨)
        // 메모리 손실을 최소화 하기 위해 백그라운드 스레드에서 통신 작업을 중단한다
        disposable.clear();
    }

}

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
                MerchantInfoModel merchantInfoModel = gson.fromJson(body, MerchantInfoModel.class);
                JsonObject jsonObject = merchantInfoModel.getResult();
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
                MerchantInfoModel merchantInfoModel = gson.fromJson(body, MerchantInfoModel.class);
                JsonObject jsonObject = merchantInfoModel.getResult();
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
}*/
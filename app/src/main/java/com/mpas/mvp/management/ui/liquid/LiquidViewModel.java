package com.mpas.mvp.management.ui.liquid;

import static com.mpas.mvp.merchant1.repository.ApiRepository.getInstance;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mpas.mvp.merchant1.repository.ApiRepository;
import com.mpas.mvp.ui.main.payments.RetrofitAPI;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LiquidViewModel extends AndroidViewModel {

    private final Repository repo = Repository.getInstance(getApplication());
    private final CompositeDisposable disposable = new CompositeDisposable();

    public LiquidViewModel(@NonNull Application application) {
        super(application);
    }

    public void getPayLoad() {
        disposable.add(repo.getPayLoad().
                subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<String>(){

            @Override
            public void onSuccess(String s) {
                Log.e("onSuccess",s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("onSuccess",e.toString());
            }
        }));
    }

    public void getPayLoads(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://sandbox.api.liquidpay.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        LiquidAPI retrofitAPI = retrofit.create(LiquidAPI.class);

        retrofitAPI.getPayLoads("HFpKWT6DpguFgwig4fuIUIL3sUtF9rY6").enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                //String s = new Gson().toJson(response.body());
                //String body = s.substring(1, s.length() - 1);

                Log.e("onResponse", response.message());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });

    }
}
package com.mpas.mvp.merchant1.view;

import static com.mpas.mvp.merchant1.repository.ApiRepository.getInstance;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mpas.mvp.merchant1.model.SalesModel;
import com.mpas.mvp.merchant1.model.SalesDetailModel;
import com.mpas.mvp.merchant1.repository.ApiRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


public class SalesViewModel extends AndroidViewModel {

    public SalesViewModel(@NonNull Application application) {
        super(application);
    }

    private final ApiRepository apiRepository = getInstance(getApplication());
    private final CompositeDisposable disposable = new CompositeDisposable();
    public MutableLiveData<List<SalesDetailModel.SalesDetailDB>> saleDetailDbMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<SalesModel.SaleDB>> salesDbMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> salesSumPriceMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<SalesModel.SaleDB>> getSalesDb() {
        return salesDbMutableLiveData;
    }

    public MutableLiveData<Integer> getSalesSumPriceMutableLiveData() {
        return salesSumPriceMutableLiveData;
    }

    public MutableLiveData<List<SalesDetailModel.SalesDetailDB>> getSaleDetailDbMutableLiveData() {
        return saleDetailDbMutableLiveData;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getSale_purchase() {
        Log.e("viewmodel","getSale_purchase");

        disposable.add(apiRepository.getSale_purchase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<SalesDetailModel>(){

                    @Override
                    public void onSuccess(SalesDetailModel salseDetailModel) {
                        Log.e("viewmodel","onSuccess");
                        if ( salseDetailModel.getStatus().equals("200")) {
                            saleDetailDbMutableLiveData.setValue(salseDetailModel.getSalesDetailData());
                            salesSumPriceMutableLiveData.setValue(salseDetailModel.getTotalPrice());
                        } else {
                            Log.e("ErrorMessage" , salseDetailModel.getStatus() + ": "+ salseDetailModel.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                }));

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getSales(String startDate, String endDate) {
       disposable.add(apiRepository.getSalesSummary(startDate,endDate)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribeWith(new DisposableSingleObserver<SalesModel>() {

           @Override
           public void onSuccess(SalesModel salesModel) {

               if ( salesModel.getStatus().equals("200")) {
                   for(SalesModel.SaleDB db :salesModel.getSaleDBList()) {
                       Log.e("getSalesonSuccess",db.toString());
                   }
                   salesDbMutableLiveData.setValue(salesModel.getSaleDBList());
               } else {
                   Log.e("Error",salesModel.getStatus()+":"+salesModel.getDetail()+":"+salesModel.getMessage());
               }
           }

           @Override
           public void onError(Throwable e) {

           }
       }));



    }

    @Override
    protected void onCleared() {
        super.onCleared();

        // Memory Initialize
        disposable.clear();
    }
}
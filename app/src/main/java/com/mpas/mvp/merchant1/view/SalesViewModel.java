package com.mpas.mvp.merchant1.view;

import static com.mpas.mvp.merchant1.repository.ApiRepository.getInstance;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mpas.mvp.merchant1.model.SalseDetailModel;
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
    public MutableLiveData<List<SalseDetailModel.SalesDetailDB>> saleDbMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> salesSumPriceMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Integer> getSalesSumPriceMutableLiveData() {
        return salesSumPriceMutableLiveData;
    }

    public MutableLiveData<List<SalseDetailModel.SalesDetailDB>> getSaleDbMutableLiveData() {
        return saleDbMutableLiveData;
    }

    public void getSale_purchase() {
        disposable.add(apiRepository.getSale_purchase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<SalseDetailModel>(){

                    @Override
                    public void onSuccess(SalseDetailModel salseDetailModel) {
                        saleDbMutableLiveData.setValue(salseDetailModel.getSalesDetailData());
                        salesSumPriceMutableLiveData.setValue(salseDetailModel.getTotalPrice());
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
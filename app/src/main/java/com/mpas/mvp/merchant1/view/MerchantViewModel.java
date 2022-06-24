package com.mpas.mvp.merchant1.view;

import static com.mpas.mvp.merchant.repository.ApiRepository.getInstance;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mpas.mvp.merchant.model.BanksModel;
import com.mpas.mvp.merchant.model.MerchantInfoModel;
import com.mpas.mvp.merchant.repository.ApiRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MerchantViewModel extends AndroidViewModel {
    public MerchantViewModel(@NonNull Application application) {
        super(application);
    }

    private final ApiRepository apiRepository = getInstance();
    private final CompositeDisposable disposable = new CompositeDisposable();

    public MutableLiveData<List<BanksModel>> banksMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<BanksModel>> getBanksMutableLiveData() {
        return banksMutableLiveData;
    }

    public void getMerchant(String biz, String mid) {

        disposable.add(apiRepository.getMerchantInfo(biz, mid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<MerchantInfoModel>() {

                    @Override
                    public void onSuccess(MerchantInfoModel merchantInfoModel) {

                        Log.e("refresh",merchantInfoModel.getResult().getBusinessnumber());
                        Log.e("onSuccess",merchantInfoModel.toString());

                        banksMutableLiveData.setValue(merchantInfoModel.getResult().getBanks());

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                       e.printStackTrace();
                    }
                }));
    }


}

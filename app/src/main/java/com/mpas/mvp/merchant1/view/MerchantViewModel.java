package com.mpas.mvp.merchant1.view;

import static com.mpas.mvp.merchant1.model.SalesModel.*;
import static com.mpas.mvp.merchant1.repository.ApiRepository.getInstance;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mpas.mvp.merchant1.model.BanksModel;
import com.mpas.mvp.merchant1.model.MerchantInfoModel;
import com.mpas.mvp.merchant1.repository.ApiRepository;
import com.mpas.mvp.merchant1.repository.MerchantFactory;
import com.mpas.mvp.merchant1.repository.PreferenceRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MerchantViewModel extends AndroidViewModel {
    private final ApiRepository apiRepository = getInstance(getApplication());
    private final PreferenceRepository preferenceRepository = new PreferenceRepository(getApplication());
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MerchantFactory merchantFactory = new MerchantFactory();

    public MerchantViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<BanksModel>> banksMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<SaleDB>> saleDbMutableLiveDate = new MutableLiveData<>();
    public MutableLiveData<Set<String>> factoryLivedata = new MutableLiveData<>();

    public MutableLiveData<SharedPreferences> sharedPreferencesMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<SharedPreferences> getSharedPreferencesMutableLiveData() {
        return sharedPreferencesMutableLiveData;
    }

    public MutableLiveData<List<BanksModel>> getBanksMutableLiveData() {
        return banksMutableLiveData;
    }

    public MutableLiveData<Set<String>> getFactoryLivedata() {
        return factoryLivedata;
    }

    public void getMerchant(String biz, String mid) {

        disposable.add(apiRepository.getMerchantInfo(biz, mid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<MerchantInfoModel>() {

                    @Override
                    public void onSuccess(MerchantInfoModel merchantInfoModel) {

                        Log.e("onSuccess",merchantInfoModel.toString());

                        merchantFactory.add(merchantInfoModel.getResult());
                        Log.e("factory",merchantFactory.getKeySet().toString());
                        factoryLivedata.setValue(merchantFactory.getKeySet());
                        sharedPreferencesMutableLiveData.setValue(preferenceRepository.updateSharedPreference(merchantInfoModel));
                        banksMutableLiveData.setValue(merchantInfoModel.getResult().getBanks());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                       e.printStackTrace();
                    }
                }));
    }
}

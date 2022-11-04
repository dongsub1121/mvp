package com.mpas.mvp.merchant1.view;

import static com.mpas.mvp.merchant1.repository.ApiRepository.getInstance;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mpas.mvp.merchant1.model.Merchant;
import com.mpas.mvp.merchant1.repository.ApiRepository;
import com.mpas.mvp.merchant1.repository.DatabaseRepository;
import com.mpas.mvp.merchant1.repository.MerchantEntity;
import com.mpas.mvp.merchant1.repository.MerchantFactory;
import com.mpas.mvp.merchant1.repository.RoomDB;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MerchantViewModel extends AndroidViewModel {
    private final ApiRepository apiRepository = getInstance(getApplication());
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MerchantFactory merchantFactory = new MerchantFactory();
    private final RoomDB roomDB = RoomDB.getInstance(getApplication());

    public MutableLiveData<MerchantFactory> merchant_factory = new MutableLiveData<>();
    public MutableLiveData<MerchantEntity> merchant =  new MutableLiveData<>();
    public MutableLiveData<ArrayList<String>> merchants= new MutableLiveData<>();

    public MerchantViewModel(@NonNull Application application) {
        super(application);
        setMerchant();
        getMerchantList();
    }

    public MutableLiveData<MerchantFactory> getMerchantDownLoad() {
        return merchant_factory;
    }

    public MutableLiveData<MerchantEntity> getMerchant() {
        return merchant;
    }

    public MutableLiveData<ArrayList<String>> getMerchant_list() {
        return merchants;
    }


    public void getMerchantDownLoad(String biz, String mid) {

        disposable.add(apiRepository.getMerchantInfo(biz, mid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Merchant>() {

                    @Override
                    public void onSuccess(Merchant merchantInfoModel) {
                        Log.e("getMerchantDownLoad","#############");
                        Log.e("onSuccess",merchantInfoModel.toString());

                        merchantFactory.add(merchantInfoModel.getResult());
                        merchant_factory.setValue(merchantFactory);
                        AddMerchant(merchantInfoModel.getResult()); // db저장
                        SetMerchant(merchantInfoModel.getResult().getSitename());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                       e.printStackTrace();
                    }
                }));
    }

    public void InitMerchantFactory () {
        merchantFactory.Initialize();
    }


    @SuppressLint("CheckResult")
    public void AddMerchant(Merchant.Result merchant){
        MerchantEntity mer = new MerchantEntity();
        mer.setBusinessNo(merchant.getBusinessnumber());
        mer.setMerchantNo(merchant.getSiteid());
        mer.setSitename(merchant.getSitename());
        mer.setSiteaddress(merchant.getSiteaddress());

        roomDB.merchantDao().insert(mer).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->{
                    Toast.makeText(getApplication(), "저장", Toast.LENGTH_SHORT).show();
                });
    }

    @SuppressLint("CheckResult")
    public void SetMerchant(String siteName){
        roomDB.merchantDao().loadById(siteName).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(merchant_item->{
                    if(merchant_item.size() > 0){
                        merchant.setValue(merchant_item.get(0));
                        Log.e("SetMerchant",merchant_item.get(0).getSitename());
                    } else {
                        Toast.makeText(getApplication(), "가맹점 없음", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void DeleteAll(){
        roomDB.merchantDao().deleteAll().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->{
                    Toast.makeText(getApplication(), "DB 전체 삭제", Toast.LENGTH_SHORT).show();
                });
    }

    @SuppressLint("CheckResult")
    public void getMerchantList() {
        ArrayList<String> arrayList = new ArrayList<>();
        roomDB.merchantDao().getAll().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(merchant_list->{
                    Log.e("가맹점 개수", String.valueOf(merchant_list.size()));
                    if( merchant_list.size() > 0) {
                        for( MerchantEntity merchantEntity : merchant_list) {
                            Log.e("getMerchantList",merchantEntity.getSitename());
                            arrayList.add(merchantEntity.getSitename());
                        }
                        merchants.setValue(arrayList);
                    } else {
                        Toast.makeText(getApplication(), "가맹점 없음", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public boolean queryMerchant(){
        boolean pass = false;
        ArrayList<String> arrayList = getMerchant_list().getValue();

        if(arrayList.size() < 0 ) {
            pass = true;
        } else {
            for ( String s : arrayList) {

            }
        }

        return pass;
    }
    @SuppressLint("CheckResult")
    private void setMerchant(){
        ArrayList<String> arrayList = new ArrayList<>();
        MerchantEntity entity;
        roomDB.merchantDao().getAll().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(merchant_list->{
                    for( MerchantEntity merchantEntity : merchant_list) {
                        Log.e("getMerchantList",merchantEntity.getSitename());
                        arrayList.add(merchantEntity.getSitename());
                        merchant.setValue(merchantEntity);
                    }
                    merchants.setValue(arrayList);
                });
    }

}

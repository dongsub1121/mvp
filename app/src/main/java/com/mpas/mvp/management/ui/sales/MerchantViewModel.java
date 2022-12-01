package com.mpas.mvp.management.ui.sales;

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
    public MutableLiveData<MerchantEntity> mainMerchant =  new MutableLiveData<>();
    public MutableLiveData<List<MerchantEntity>> merchantEntities = new MutableLiveData<>();
    public MutableLiveData<ArrayList<String>> merchants= new MutableLiveData<>();
    public MutableLiveData<String> message = new MutableLiveData<>();

    public MerchantViewModel(@NonNull Application application) {
        super(application);
        setMerchant();
        getMerchantList();
        message.setValue("");
    }


    public MutableLiveData<String> getMessage() {
        return message;
    }

    public MutableLiveData<MerchantFactory> MerchantDownLoad() {
        return merchant_factory;
    }

    public MutableLiveData<MerchantEntity> getMainMerchant() {
        return mainMerchant;
    }

    public MutableLiveData<ArrayList<String>> getMerchant_list() {
        return merchants;
    }

    public MutableLiveData<List<MerchantEntity>> getMerchantAll() { return merchantEntities;}

    public void MerchantDownLoad(String biz, String mid) {

        disposable.add(apiRepository.getMerchantInfo(biz, mid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Merchant>() {

                    @Override
                    public void onSuccess(Merchant merchantInfoModel) {
                        Log.e("getMerchantDownLoad","#############");
                        Log.e("onSuccess",merchantInfoModel.toString());

                        if (merchantInfoModel.getResult().getSitename() == null) {
                            message.setValue("가맹점 정보 오류");
                        } else {
                            merchantFactory.add(merchantInfoModel.getResult());
                            merchant_factory.setValue(merchantFactory);
                            AddMerchant(merchantInfoModel.getResult()); // db저장
                            if (mainMerchant.getValue() == null) {
                                SetMerchant(merchantInfoModel.getResult().getSitename());
                            }
                        }
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


        roomDB.merchantDao().loadById(merchant.getSitename()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(merchant_item->{
                    if(merchant_item.size() == 0){
                        Log.e("checkDao","추가 가능");
                        roomDB.merchantDao().insert(mer).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(()->{
                                    message.setValue("가맹점이 추가 되었습니다.");
                                });
                    } else {
                        message.setValue("이미 추가된 가맹점 입니다");
                        Log.e("checkDao",merchant_item.size() +"이미 존재하는 가맹점 입니다.");
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void SetMerchant(String siteName){
        roomDB.merchantDao().loadById(siteName).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(merchant_item->{
                    if(merchant_item.size() > 0){
                        mainMerchant.setValue(merchant_item.get(0));
                        Log.e("SetMerchant",merchant_item.get(0).getSitename());
                    } else {
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
    public void deleteSingle(String siteName) {

        roomDB.merchantDao().delete(siteName).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->{
                    getMerchantEntities();

                    if (mainMerchant.getValue() != null) {
                        if(siteName.equals(mainMerchant.getValue().getSitename())) {
                            mainMerchant.setValue(null);
                        }
                    } else {
                        Toast.makeText(getApplication(), "가맹점 삭제", Toast.LENGTH_SHORT).show();
                    }
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
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void getMerchantEntities(){
        roomDB.merchantDao().getAll().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(merchant_list->{
                    Log.e("getMerchantEntities count", String.valueOf(merchant_list.size()));
                    if( merchant_list.size() == 0) {
                        mainMerchant.setValue(null);
                    }
                    merchantEntities.setValue(merchant_list);
                });
    }

    public boolean queryMerchant(){
        boolean pass = false;
        ArrayList<String> arrayList = getMerchant_list().getValue();

        if(arrayList.size() > 0 ) {
            pass = true;
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
                        mainMerchant.setValue(merchantEntity);
                    }
                    merchants.setValue(arrayList);
                });
    }

}
